/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker.logic;

import edu.javeriana.patrones.broker.model.Product;
import edu.javeriana.patrones.broker.model.Provider;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristianmendi
 */
@Service
public interface BrokerLogic {
    public void sendQuotations(List<Provider> providers) throws BrokerException;
    public void makeQuotes(List<Product> products);
}
