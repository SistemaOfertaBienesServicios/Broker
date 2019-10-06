/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker.logic.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.javeriana.patrones.broker.logic.BrokerException;
import edu.javeriana.patrones.broker.logic.BrokerLogic;
import edu.javeriana.patrones.broker.logic.SendQuotationThread;
import edu.javeriana.patrones.broker.model.Product;
import edu.javeriana.patrones.broker.model.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristianmendi
 */
@Service
public class BrokerLogicImpl implements BrokerLogic {
    
    private ObjectMapper omp = new ObjectMapper();

    @Override
    public void makeQuotes(List<Product> products) {
        List<Provider> providers = getProvidersToQuote(products);
        providers.forEach((provider) -> {
            String body = generateRequestData(provider,products);
            String endpoint = provider.getEndpoint().getEndpoint();
            sendRequest(body,endpoint);
        });  
    }
    
    private List<Provider> getProvidersToQuote(List<Product> products){
        List<Provider> providers = getProvsData();
        List<Provider> acceptedProviders = new ArrayList();
        for (Provider provider: providers ){
            int availableProducts = 0;
            for (Product product : products){
                if(provider.inCatalog(product.getName())){
                    availableProducts+=1;
                }
            }
            if(availableProducts==products.size()){
                acceptedProviders.add(provider);
            }
        }
        return acceptedProviders;
    }
    
    private void sendRequest(String body, String endpoint){
        SendQuotationThread thread = new SendQuotationThread();
        thread.setBody(body);
        thread.setEndpoint(endpoint);
        thread.start();
    }

    private String generateRequestData(Provider provider,List<Product> products) {
        String body="{\n" +
                "\"products\":[{\"name\":\"product1\",\"quantity\":1},"
                + "{\"name\":\"product2\",\"quantity\":2},"
                + "{\"name\":\"product3\",\"quantity\":3}]\n" +"}";
        return body;
    }

    private List<Provider> getProvsData() {
        String response = generateGetRequest("endpoint getProvidersData");
        List<Provider> provsData;
        try {
            provsData = omp.readValue(response, new TypeReference<List<Provider>>() {});
        } catch (IOException ex) {
            Logger.getLogger(BrokerLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return provsData;
    }

    @Override
    public boolean validateToken(String token) throws BrokerException {
        System.out.println("token: " + token);
        return token.equals("valido");
    }

    @Override
    public Provider registerProvider(Provider newProvider) throws BrokerException {
        String response = generatePOSTRequest("endopointRegistro","body");
        Provider provider;
        try {
            provider = omp.readValue(response, new TypeReference<Provider>() {});
        } catch (IOException ex) {
            Logger.getLogger(BrokerLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return provider;
    }
    
    

    private String generateGetRequest(String endpoint) {
        try {
            URL urlForGetRequest = new URL(endpoint);
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
            int responseCode = conection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                // print result
                return response.toString();
                //GetAndPost.POSTRequest(response.toString());
            } else {
                return "";
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(BrokerLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(BrokerLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BrokerLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
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
            System.out.println("POST Response Message : " + postConnection.getResponseMessage());
            if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
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

    @Override
    public void sendQuotations(List<Provider> providers) throws BrokerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




}
