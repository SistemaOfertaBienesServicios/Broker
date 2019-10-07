package edu.javeriana.patrones.broker.model;

import java.util.List;

/**
 *
 * @author Carlos Ramirez
 */
public class Quotation {

    private long id;
    private long total;
    private List<Product> products;
    private String username;
    private long providerId;

    public Quotation() {
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

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Quotation{" + "id=" + id + ", total=" + total + ", products=" + products + ", username=" + username + ", providerId=" + providerId + '}';
    }
}
