package com.kuka.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;


@Configuration
@MapperScan(basePackages = {"com.kuka.dao"}, annotationClass = Repository.class)
@PropertySource(value = {"classpath:application-${spring.profiles.active}.yml"},factory = YamlPropertySourceFactory.class)
public class MyBatisConfig {
    /**
     * 数据库驱动类
     */
    @Value("${jdbc.driverClassName}")
    private  String driverClassName;

    /**
     * 数据库路径测试环境
     */
    @Value("${jdbc.url}")
    private String url;
    /**
     * 数据库名称
     */
    @Value("${jdbc.username}")
    private String username;

    /**
     * 数据库密码
     */
    @Value("${jdbc.password}")
    private String password ;

    /**
     * 默认提交
     */
    private static final boolean defaultAutoCommit=true;

    /**
     * 连接池的最大数据库连接数。设为0表示无限制。
     */
    private static final int maxActive=150;

    /**
     * 最大空闲数，数据库连接的最大空闲时间。超过空闲时间，数据库连接将被标记为不可用，然后被释放。设为0表示无限制。
     */
    private static final int maxIdle=100;

    /**
     * 最小空闲数，
     */
    private static final int minIdle=5;

    /**
     * 最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制。
     */
    private static final int maxWait=60000;

    @Bean
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDefaultAutoCommit(defaultAutoCommit);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(false);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(120);
        dataSource.setTimeBetweenEvictionRunsMillis(30000);
        dataSource.setMinEvictableIdleTimeMillis(1800000);
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("com.kuka.domain");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:config/mybatis-config.xml"));
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:com/kuka/dao/*Mapper.xml"));
        return sqlSessionFactory;
    }


    @Bean
    public PlatformTransactionManager transactionManager( DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }



}
