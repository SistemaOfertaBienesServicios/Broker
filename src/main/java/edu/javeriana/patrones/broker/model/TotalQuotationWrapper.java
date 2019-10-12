/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker.model;

/**
 *
 * @author cristianmendi
 */
public class TotalQuotationWrapper {
    private long total;

    public TotalQuotationWrapper(long total) {
        this.total = total;
    }

    public TotalQuotationWrapper() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "TotalQuotationWrapper{" + "total=" + total + '}';
    }
    
}
