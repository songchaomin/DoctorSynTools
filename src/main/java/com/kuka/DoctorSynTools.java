package com.kuka;

import com.kuka.event.SendMailEvent;
import com.kuka.event.SynInventoryEvent;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.kuka.dao")
@ComponentScan(basePackages = {"com.kuka.*"})
public class DoctorSynTools
{
    public static void main( String[] args )
    {
        SpringApplication springApplication=new SpringApplication(DoctorSynTools.class);
        ConfigurableApplicationContext context = springApplication.run(args);
        context.publishEvent(new SynInventoryEvent(new Object()));
        context.publishEvent(new SendMailEvent(new Object()));
    }
}
