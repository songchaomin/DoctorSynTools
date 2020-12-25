package com.kuka.scheduler.job;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.kuka.domain.*;
import com.kuka.exeception.KukaRollbackException;
import com.kuka.services.*;
import com.kuka.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SynInventoryJob implements Job {
    /**
     * 每次查询的记录数
     */
    private static final int ROWS=1000;
    private static final int DBHANDLERROWS=500;
    @Autowired
    private Md5Utils md5Utils;
    @Autowired
    private SupplierStockService supplierStockService;
    @Autowired
    private SupplierStockItemService supplierStockItemService;
    @Autowired
    private InventoryConfigService inventoryConfigService;
    @Autowired
    private SpkfkService spkfkService;
    @Autowired
    private HuoWeiService huoWeiService;
    @Autowired
    private SpkfjcService spkfjcService;
    @Autowired
    private HwspService hwspService;
    @Autowired
    private SphwphService sphwphService;
    @Autowired
    private SupplierJxingService supplierJxingService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        synInventory();
    }

    public void synInventory(){
        log.info("------------------同步安徽华源系统库存开始（全量同步）---------------");
        handlerInventory();
        log.info("------------------同步安徽华源系统库存结束---------------");
        log.info("------------------处理商品资料开始---------------");
        handlerStockItem();
        log.info("------------------处理商品资料结束---------------");
        log.info("------------------获取货位---------------");
        String hwi = getHwi();
        log.info("------------------处理结存和货位商品表开始---------------");
        handlerInventoryStorage(hwi);
        log.info("------------------处理结存和货位商品表结束---------------");
        log.info("------------------处理商品货位开始---------------");
        handlerItemStorage(hwi);
        log.info("------------------处理商品货位结束---------------");
    }

    private void handlerItemStorage(String hwi) {
        int itemCount = supplierStockService.queryBatchSupplierStockItemCount();
        if (itemCount<=0){
            throw new KukaRollbackException("此次查询没有可以同步的库存数据！");
        }else{
            sphwphService.deleteSphwphByHwi(hwi);
        }
        int itemPage=itemCount%DBHANDLERROWS==0?itemCount/DBHANDLERROWS:(itemCount/DBHANDLERROWS)+1;
        for (int i=1;i<=itemPage;i++) {
            List<SupplierStock> stockJCS = supplierStockService.queryBathSupplierStockItemByPage(DBHANDLERROWS,i);
            List<Sphwph> insertSphwph= new ArrayList<>();
            for (int j=0;j<stockJCS.size();j++){
                SupplierStock supplierStock = stockJCS.get(j);
                Sphwph sphwph=new Sphwph();
                sphwph.setSpid(supplierStock.getStoreId());
                sphwph.setHw(hwi);
                sphwph.setPihao(supplierStock.getBatchNum());
                sphwph.setBaozhiqi(supplierStock.getDueDate2());
                sphwph.setDangqzht("合格");
                sphwph.setSxrq(supplierStock.getDueDate2());
                sphwph.setShl(new BigDecimal(supplierStock.getStoreNum()));
                sphwph.setGebjj(supplierStock.getPrice());
                insertSphwph.add(sphwph);
            }
            sphwphService.batchInsertSphwph(insertSphwph);
        }
    }

    private void handlerInventoryStorage(String hwi) {
        //查询合计库存数量
        int itemCount = supplierStockService.queryMergeSupplierStockItemCount();
        if (itemCount<=0){
            throw new KukaRollbackException("此次查询没有可以同步的库存数据！");
        }
        int itemPage=itemCount%DBHANDLERROWS==0?itemCount/DBHANDLERROWS:(itemCount/DBHANDLERROWS)+1;
        for (int i=1;i<=itemPage;i++){
            List<SupplierStock> stockJCS = supplierStockService.queryMergeSupplierStockItemByPage(DBHANDLERROWS,i);
            List<String> spids=stockJCS.stream().map(t->t.getStoreId()).collect(Collectors.toList());
            CompletableFuture.runAsync(()->{
                handlerSpkfjc(stockJCS, spids);
            });
            String finalHwi = hwi;
            CompletableFuture.runAsync(()->{
                handlerHwsp(stockJCS,spids, finalHwi);
            });
        }


    }

    private String getHwi() {
        List<InventoryConfig> inventoryConfigs = inventoryConfigService.queryInventoryConfig();
        String hwi = inventoryConfigs.stream().filter(t -> Objects.equals("hwi", t.getType())).findFirst().get().getContent();
        if (Objects.equals(hwi,"0")){
            String huwei = huoWeiService.queryMaxHuoWeiNo();
            if (StringUtils.isEmpty(huwei)){
                hwi=String.format("%s%08d", "HWI",1);
            }else{
                String maxno = huwei.substring(3);
                hwi=String.format("%s%08d", "HWI",Integer.valueOf(maxno)+1);
            }
            Huoweizl huoweizl=new Huoweizl();
            huoweizl.setHw(hwi);
            huoweizl.setHwbh("1");
            huoweizl.setHuowname("安徽华源药品库");
            huoweizl.setBeactive("是");
            huoWeiService.insertAHHHuoWei(huoweizl);
            inventoryConfigService.updateInventoryConfigByType(hwi,"hwi");
        }
        return hwi;
    }

    private void handlerHwsp(List<SupplierStock> stockJCS, List<String> spids,  String hwi) {
        //查询货位商品表
        List<Hwsp> hwspList = hwspService.queryHwsp(spids);
        Map<String, Hwsp> mapSpkfjc = hwspList.stream().collect(Collectors.toMap(Hwsp::getSpid, t -> t, (k1, k2) -> k1));
        List<Hwsp> insertHwsp=new ArrayList<>();
        List<Hwsp> updateHwsp=new ArrayList<>();
        if (CollectionUtils.isEmpty(hwspList)){
            for (int j=0;j<stockJCS.size();j++){
                SupplierStock supplierStock = stockJCS.get(j);
                Hwsp hwsp=new Hwsp();
                hwsp.setHw(hwi);
                hwsp.setIsHege("是");
                hwsp.setSpid(supplierStock.getStoreId());
                hwsp.setHwshl(new BigDecimal(supplierStock.getStoreNum()));
                hwsp.setChbdj(supplierStock.getPrice());
                hwsp.setHwje(new BigDecimal(supplierStock.getStoreNum()).multiply(supplierStock.getPrice()));
                insertHwsp.add(hwsp);
            }
        }else{
            for (int j=0;j<stockJCS.size();j++){
                SupplierStock supplierStock = stockJCS.get(j);
                Hwsp hwsp=new Hwsp();
                if (mapSpkfjc.containsKey(supplierStock.getStoreId())){
                    hwsp.setIsHege("是");
                    hwsp.setHw(hwi);
                    hwsp.setSpid(supplierStock.getStoreId());
                    hwsp.setHwshl(new BigDecimal(supplierStock.getStoreNum()));
                    hwsp.setChbdj(supplierStock.getPrice());
                    hwsp.setHwje(new BigDecimal(supplierStock.getStoreNum()).multiply(supplierStock.getPrice()));
                    updateHwsp.add(hwsp);
                }else{
                    hwsp.setIsHege("是");
                    hwsp.setHw(hwi);
                    hwsp.setSpid(supplierStock.getStoreId());
                    hwsp.setHwshl(new BigDecimal(supplierStock.getStoreNum()));
                    hwsp.setChbdj(supplierStock.getPrice());
                    hwsp.setHwje(new BigDecimal(supplierStock.getStoreNum()).multiply(supplierStock.getPrice()));
                    insertHwsp.add(hwsp);
                }
            }
        }
        hwspService.batchInsertHwsp(insertHwsp);
        hwspService.batchUpateHwsp(updateHwsp);
    }

    private void handlerSpkfjc(List<SupplierStock> stockJCS, List<String> spids) {
        //查询结存表
        List<Spkfjc> spkfjcs = spkfjcService.querySpkfjc(spids);
        Map<String, Spkfjc> mapSpkfjc = spkfjcs.stream().collect(Collectors.toMap(Spkfjc::getSpid, t -> t, (k1, k2) -> k1));
        List<Spkfjc> insertSpkfjc=new ArrayList<>();
        List<Spkfjc> updateSpkfjc=new ArrayList<>();
        if (CollectionUtils.isEmpty(spkfjcs)){
            for (int j=0;j<stockJCS.size();j++){
                SupplierStock supplierStock = stockJCS.get(j);
                Spkfjc spkfjc=new Spkfjc();
                spkfjc.setSpid(supplierStock.getStoreId());
                spkfjc.setKcshl(new BigDecimal(supplierStock.getStoreNum()));
                spkfjc.setChbdj(supplierStock.getPrice());
                spkfjc.setKcje(new BigDecimal(supplierStock.getStoreNum()).multiply(supplierStock.getPrice()));
                spkfjc.setHescbj(supplierStock.getPrice());
                insertSpkfjc.add(spkfjc);
            }
        }else{
            for (int j=0;j<stockJCS.size();j++){
                SupplierStock supplierStock = stockJCS.get(j);
                Spkfjc spkfjc=new Spkfjc();
                if (mapSpkfjc.containsKey(supplierStock.getStoreId())){
                    spkfjc.setSpid(supplierStock.getStoreId());
                    spkfjc.setKcshl(new BigDecimal(supplierStock.getStoreNum()));
                    spkfjc.setChbdj(supplierStock.getPrice());
                    spkfjc.setHescbj(supplierStock.getPrice());
                    spkfjc.setKcje(new BigDecimal(supplierStock.getStoreNum()).multiply(supplierStock.getPrice()));
                    updateSpkfjc.add(spkfjc);
                }else{
                    spkfjc.setSpid(supplierStock.getStoreId());
                    spkfjc.setKcshl(new BigDecimal(supplierStock.getStoreNum()));
                    spkfjc.setChbdj(supplierStock.getPrice());
                    spkfjc.setHescbj(supplierStock.getPrice());
                    spkfjc.setKcje(new BigDecimal(supplierStock.getStoreNum()).multiply(supplierStock.getPrice()));
                    insertSpkfjc.add(spkfjc);
                }
            }
        }
        spkfjcService.batchInsertSpkfjc(insertSpkfjc);
        spkfjcService.batchUpateSpkfjc(updateSpkfjc);
    }

    public void handlerStockItem() {
        //查询所有的剂型
        List<SupplierJixing> supplierJixings = supplierJxingService.queryAll();
        //查询库存同步中间表商品数据
        int itemCount = supplierStockService.querySupplierStockItemCount();
        if (itemCount<=0){
            throw new KukaRollbackException("此次查询没有可以同步的商品资料！");
        }
        int itemPage=itemCount%DBHANDLERROWS==0?itemCount/DBHANDLERROWS:(itemCount/DBHANDLERROWS)+1;
        for (int i=1;i<=itemPage;i++){
            List<InventoryConfig> inventoryConfigs = inventoryConfigService.queryInventoryConfig();
            int sequence=Integer.parseInt(inventoryConfigs.stream().filter(t -> Objects.equals("spi", t.getType())).findFirst().get().getContent());
            List<SupplierStock> supplierStockList = supplierStockService.querySupplierStockItemByPage(DBHANDLERROWS,i);
            Map<String, SupplierStock> supplierStockMap = supplierStockList.stream().collect(Collectors.toMap(SupplierStock::getDrugNum, t -> t, (k1, k2) -> k1));
            List<SupplierStockItem> insertSupplierStockItem=new ArrayList<>();
            List<Spkfk> insertSpkfk=new ArrayList<>();
            List<String> drugNumList=supplierStockList.stream().map(t->t.getDrugNum()).collect(Collectors.toList());
            List<SupplierStockItem> supplierStockItems = supplierStockItemService.queryItemByDrugNum(drugNumList);
            Map<String, SupplierStockItem> supplierStockItemMap = supplierStockItems.stream().collect(Collectors.toMap(SupplierStockItem::getSpbh, t -> t, (k1, k2) -> k1));
            if (CollectionUtils.isEmpty(supplierStockItems)){
               for (int j=0;j<supplierStockList.size();j++){
                   String spid = String.format("%s%08d", "AHH", ++sequence);
                    SupplierStockItem supplierStockItem=new SupplierStockItem();
                    supplierStockItem.setSpbh(supplierStockList.get(j).getDrugNum());
                    supplierStockItem.setSpid(spid);
                    insertSupplierStockItem.add(supplierStockItem);
               }
            }else{
                for (int j=0;j<supplierStockList.size();j++){
                    SupplierStockItem supplierStockItem=new SupplierStockItem();
                    String drugNum = supplierStockList.get(j).getDrugNum();
                    if (!supplierStockItemMap.containsKey(drugNum)){
                        String spid = String.format("%s%08d", "AHH", ++sequence);
                        supplierStockItem.setSpbh(drugNum);
                        supplierStockItem.setSpid(spid);
                        insertSupplierStockItem.add(supplierStockItem);
                    }
                }
            }
            inventoryConfigService.updateInventoryConfigByType(String.valueOf(sequence),"spi");
            //商品中间表
            supplierStockItemService.batchInsertSupplierStockItem(insertSupplierStockItem);
            //处理商品资料
            insertSupplierStockItem.stream().forEach(t->{
                String spbh = t.getSpbh();
                if (supplierStockMap.containsKey(spbh)){
                    SupplierStock supplierStock = supplierStockMap.get(spbh);
                    Spkfk spkfk = convertSpkfk(t, supplierStock,supplierJixings);
                    insertSpkfk.add(spkfk);
                }
            });
           spkfkService.batchInsertSpkfk(insertSpkfk);
        }
    }

    private Spkfk convertSpkfk(SupplierStockItem t, SupplierStock supplierStock,List<SupplierJixing> supplierJixings) {
        Spkfk spkfk=new Spkfk();
        spkfk.setSpid(t.getSpid());
        spkfk.setSpbh(t.getSpbh());
        spkfk.setSpmch(supplierStock.getDrugName());
        spkfk.setZjm(supplierStock.getDrugCode());
        spkfk.setBeactive("是");
        spkfk.setYishj("否");
        spkfk.setIsGdsj("是");
        spkfk.setIsGdzk("是");
        spkfk.setIsTjsp("否");
        spkfk.setIsHysp("否");
        spkfk.setIsGmp("是");
        spkfk.setIsGsp("是");
        spkfk.setIsSy("是");
        spkfk.setIsYp("否");
        spkfk.setJingd("经销");
        spkfk.setDw(supplierStock.getUnit());
        spkfk.setShpchd(supplierStock.getProductFactory());
        spkfk.setShpgg(supplierStock.getSpec());
        spkfk.setBzqfs("没有");
        spkfk.setShengccj(supplierStock.getProductFactory());
        spkfk.setPizhwh(supplierStock.getApproveNo());
        String drugType = supplierStock.getDrugType();
        if (StringUtils.isEmpty(drugType)){
            spkfk.setJixing("合剂");
        }else{
           supplierJixings.stream().forEach((s)->{
               if (s.getAhhyjx().contains(drugType)){
                   spkfk.setJixing(s.getYyjx());
               }
           });
        }
        spkfk.setLeibie("中成药");
        spkfk.setJlgg(Integer.parseInt(supplierStock.getPack()));
        spkfk.setCunchtj("常温");
        spkfk.setShenhe("是");
        spkfk.setShlv(BigDecimal.ZERO);
        spkfk.setXscbdj(BigDecimal.ZERO);
        spkfk.setZhk(BigDecimal.ZERO);
        spkfk.setZhkj(BigDecimal.ZERO);
        spkfk.setZhbj(BigDecimal.ZERO);
        spkfk.setZhengzzt(1);
        spkfk.setGspPzwhyxq("2055-12-31");
        spkfk.setShangplx("普通药品");
        return spkfk;
    }

    public void handlerInventory() {
        //第一次调用接口取总记录数
        List<InventoryConfig> inventoryConfigs = inventoryConfigService.queryInventoryConfig();
        String startTime = inventoryConfigs.stream().filter(t -> Objects.equals("time", t.getType())).findFirst().get().getContent();
        String totalJson = invokeInventoryInterface(1, 10,startTime);
        if (StringUtils.isEmpty(totalJson)){
           throw new KukaRollbackException("调用接口出错！");
        }
        JSONObject jsonObject = JSONObject.parseObject(totalJson);
        String flag = (String)jsonObject.get("flag");
        if (Objects.equals(flag,"failure")) {
            throw new KukaRollbackException("调用华源接口服务时报以下错误："+(String)jsonObject.get("message"));
        }
        int totalRows=(int) JSONObject.parseObject(totalJson).get("total");
        if (totalRows<=0){
            throw new KukaRollbackException("同步华源库存时，查询不到库存数据，本次没有同步库存！");
        }else{
            supplierStockService.truncateSupplierStock();
        }
        int totalPage=totalRows%ROWS==0?totalRows/ROWS:totalRows/ROWS+1;
        log.info("总共有{}条库存数据，需要处理{}次!",totalRows,totalPage);
        for (int i=1;i<=totalPage;i++){
            log.info("当前第{}次处理数据!",i);
            String inventoryJson = invokeInventoryInterface(i, ROWS,startTime);
            //循环调用接口处理返回数据
            DubheErpSupplierStockQueryResponse dubheErpSupplierStockQueryResponse = JSONObject.parseObject(inventoryJson, DubheErpSupplierStockQueryResponse.class);
            List<SupplierStock> supplierStockList = dubheErpSupplierStockQueryResponse.getSupplierStockList();
            if (!CollectionUtils.isEmpty(supplierStockList)){
                //批量插入中间表数据
                supplierStockService.batchInsertSupplierStock(supplierStockList);
            }
        }
    }

    private String invokeInventoryInterface(int page, int rows, String startTime ) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("tenantCode","ylkj");
        jsonObject.put("startTime",startTime);
        jsonObject.put("endTime", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("page",page);
        jsonObject.put("rows",rows);
        String requestBody=jsonObject.toJSONString();
        log.info("调用接口的查询入参：{}",requestBody);
        try {
            return md5Utils.doPost(md5Utils.getSingnUrl("DUBHE_ERP_SUPPLIER_STOCK_QUERY", requestBody), requestBody);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }
}
