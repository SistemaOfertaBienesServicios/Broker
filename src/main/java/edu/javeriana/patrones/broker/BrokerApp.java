/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker;

import edu.javeriana.patrones.broker.services.BrokerServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author cristianmendi
 */

@SpringBootApplication
@ComponentScan(basePackages = {"edu.javeriana.patrones.broker"})
public class BrokerApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BrokerApp.class, args);        
    }
    
}
