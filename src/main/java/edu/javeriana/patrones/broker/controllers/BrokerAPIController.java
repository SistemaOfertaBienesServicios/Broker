/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker.controllers;

import edu.javeriana.patrones.broker.logic.BrokerException;
import edu.javeriana.patrones.broker.model.Product;
import edu.javeriana.patrones.broker.model.Provider;
import edu.javeriana.patrones.broker.services.BrokerServices;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cristianmendi
 */

@RestController
@RequestMapping(value = "/broker")
public class BrokerAPIController {
    
    @Autowired
    BrokerServices bs;
    
    @RequestMapping(path = "/register/{token}", method = RequestMethod.POST)
    public ResponseEntity<?> registerProvider(@RequestBody Provider newProvider, @PathVariable("token") String token) {
        try {
            //registrar dato
            System.out.println(newProvider);
            System.out.println(token);
            boolean auth =bs.validateToken(token);
            if(auth){
              bs.registerProvider(newProvider);
              return new ResponseEntity<>(HttpStatus.CREATED);
            }
            else{
              return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (BrokerException ex) {
            Logger.getLogger(BrokerException.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    
    @RequestMapping(path = "/quote", method = RequestMethod.POST)
    public ResponseEntity<?> makeQuotes(@RequestBody List<Product> products) {
        bs.makeQuotes(products);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    
    
}
