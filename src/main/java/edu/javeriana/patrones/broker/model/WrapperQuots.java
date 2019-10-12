/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker.model;

import java.util.List;

/**
 *
 * @author cristianmendi
 */
public class WrapperQuots {
    List<Product> products;
    List<Provider> providers;
    String username;
    String email;
    
    
    public WrapperQuots() {
    }

    public WrapperQuots(List<Product> products, List<Provider> providers, String username, String email) {
        this.products = products;
        this.providers = providers;
        this.username = username;
        this.email = email;
    }

   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "WrapperQuots{" + "products=" + products + ", providers=" + providers + ", username=" + username + ", email=" + email + '}';
    }

    

    

}
