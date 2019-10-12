/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.javeriana.patrones.broker.model.QuotationWrapper;
import edu.javeriana.patrones.broker.logic.impl.BrokerLogicImpl;
import static edu.javeriana.patrones.broker.logic.impl.BrokerLogicImpl.omp;
import edu.javeriana.patrones.broker.logic.impl.WrapperExternalBody;
import edu.javeriana.patrones.broker.model.Product;
import edu.javeriana.patrones.broker.model.Provider;
import edu.javeriana.patrones.broker.model.Quotation;
import edu.javeriana.patrones.broker.model.TotalQuotationWrapper;
import edu.javeriana.patrones.broker.model.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cristianmendi
 */
public class SendQuotationThread extends Thread {

    private String endpoint;
    private String body;

    private List<Product> products;
    private Provider provider;
    private User user;
    private String connEndpoint;

    @Override
    public void run() {
        System.out.println("Sending post");
        String response = generatePOSTRequest(this.endpoint, this.body);
        System.out.println(this.body);
        System.out.println(response);
        QuotationWrapper quo = decodeResponse(response);
        saveQuotation(quo);
        
    }
    
    public String saveQuotation(QuotationWrapper quo){
        String response="";
        try {
            String body = omp.writeValueAsString(quo);
            System.out.println("bodysavequoto");
            System.out.println(body);
            response = generatePOSTRequest(connEndpoint, body);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(SendQuotationThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public QuotationWrapper decodeResponse(String response) {
        QuotationWrapper q = new QuotationWrapper();
        String[] params = this.provider.getEndpoint().getEndpointParameters().split(" ");
        if (params.length > 1) {
            body = body.replaceAll(params[2], "total");
        }
        try {
            TotalQuotationWrapper tqw = BrokerLogicImpl.omp.readValue(response, TotalQuotationWrapper.class);
            q.setTotal(tqw.getTotal());
            q.setProducts(products);
            q.setProviderId(provider.getId());
            q.setUsername(user.getUsername());
            q.setProviderName(provider.getName());
            q.setEmail(user.getEmail());
        } catch (IOException ex) {
            Logger.getLogger(SendQuotationThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("q");
        System.out.println(q);
        return q;
    }

    public String getConnEndpoint() {
        return connEndpoint;
    }

    public void setConnEndpoint(String connEndpoint) {
        this.connEndpoint = connEndpoint;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static String generatePOSTRequest(String endpoint, String body) {
        try {
            URL obj = new URL(endpoint);
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);
            OutputStream os = postConnection.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            os.close();
            int responseCode = postConnection.getResponseCode();
            System.out.println("POST Response Code :  " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
                System.out.println("siithread");
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        postConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // print result
                return response.toString();
            } else {
                return null;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(BrokerLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BrokerLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
