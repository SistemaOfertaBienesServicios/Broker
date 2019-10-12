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
public class QuotationWrapper {
    private long id;
    private long total;
    private List<Product> products;
    private String username;
    private long providerId;
    private String email;
    private String providerName;

    public QuotationWrapper() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    
    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "QuotationWrapper{" + "id=" + id + ", total=" + total + ", products=" + products + ", username=" + username + ", providerId=" + providerId + ", email=" + email + ", providerName=" + providerName + '}';
    }
    
    

}
