/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker.services;

import edu.javeriana.patrones.broker.logic.BrokerException;
import edu.javeriana.patrones.broker.logic.BrokerLogic;
import edu.javeriana.patrones.broker.model.Product;
import edu.javeriana.patrones.broker.model.Provider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristianmendi
 */
@Service
public class BrokerServices {
    
    @Autowired
    BrokerLogic brokerlogic=null;
    
    public void sendQuotations(List<Provider> providers) throws BrokerException{
        brokerlogic.sendQuotations(providers);
    }
    
    public boolean validateToken(String token) throws BrokerException{
        return brokerlogic.validateToken(token);
    }
    
    public Provider registerProvider(Provider newProvider) throws BrokerException{
        return brokerlogic.registerProvider(newProvider);
    }

    public void makeQuotes(List<Product> products) {
        brokerlogic.makeQuotes(products);
    }
}
