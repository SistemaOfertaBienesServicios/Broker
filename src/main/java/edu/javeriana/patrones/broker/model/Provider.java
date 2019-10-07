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
public class Provider {

    private long id;
    private String name;
    private boolean system;
    private EndpointInfo endpoint;
    private List<Product> products;
    private List<Quotation> quotations;

    public Provider() {
    }

    public Provider(long id, String name, boolean system, EndpointInfo endpoint) {
        this.id = id;
        this.name = name;
        this.system = system;
        this.endpoint = endpoint;
    }
    
    public Provider(long id, String name, boolean system) {
        this.id = id;
        this.name = name;
        this.system = system;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Quotation> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<Quotation> quotations) {
        this.quotations = quotations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the system
     */
    public boolean isSystem() {
        return system;
    }

    /**
     * @param system the system to set
     */
    public void setSystem(boolean system) {
        this.system = system;
    }

    /**
     * @return the endpoint
     */
    public EndpointInfo getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(EndpointInfo endpoint) {
        this.endpoint = endpoint;
    }

    public boolean inCatalog(String productName) {
        System.out.println("inCatalog");
        return this.products.stream().anyMatch((product) -> (product.getName().equals(productName)));
    }

    @Override
    public String toString() {
        return "Provider{" + "id=" + id + ", name=" + name + ", system=" + system + ", endpoint=" + endpoint
                + ", products=" + products + '}';
    }

}
