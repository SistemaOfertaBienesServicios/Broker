/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker.logic.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.javeriana.patrones.broker.logic.BrokerException;
import edu.javeriana.patrones.broker.logic.BrokerLogic;
import edu.javeriana.patrones.broker.logic.SendQuotationThread;
import edu.javeriana.patrones.broker.model.Product;
import edu.javeriana.patrones.broker.model.Provider;
import edu.javeriana.patrones.broker.model.User;
import edu.javeriana.patrones.broker.services.SobsProperties;
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
    
    public static ObjectMapper omp = new ObjectMapper();
    private SobsProperties connectionPropierties = new SobsProperties();

    private String urlSobsSaveQuote = connectionPropierties.getPropValues("registerQuotationEndpoint");

    @Override
    public void makeQuotes(List<Product> products,List<Provider> providers,User user) {

        List<Provider> acceptedProviders = getProvidersToQuote(products,providers);
        acceptedProviders.forEach((provider) -> {
            String body = generateRequestData(products,provider.getEndpoint().getEndpointParameters());
            String endpoint = provider.getEndpoint().getEndpoint();
            System.out.println(endpoint);
            sendRequest(body,products,user,provider);
        });  
    }
    
    private List<Provider> getProvidersToQuote(List<Product> products,List<Provider> providers){
        System.out.println("getProvidersToQuote");
        List<Provider> acceptedProviders = new ArrayList();
        providers.forEach((provider) -> {
            int availableProducts = 0;
            availableProducts = products.stream().filter((product) -> (provider.inCatalog(product.getName()))).map((_item) -> 1).reduce(availableProducts, Integer::sum);
            if (availableProducts==products.size()) {
                acceptedProviders.add(provider);
            }
        });
        return acceptedProviders;
    }
    
    private void sendRequest(String body, List<Product> products, User user, Provider provider){
        System.out.println("sendRequest");
        System.out.println(user);
        SendQuotationThread thread = new SendQuotationThread();
        thread.setBody(body);
        thread.setEndpoint(provider.getEndpoint().getEndpoint());
        thread.setProducts(products);
        thread.setUser(user);
        thread.setProvider(provider);
        thread.setConnEndpoint(urlSobsSaveQuote);
        thread.start();
    }

    private String generateRequestData(List<Product> products, String epParameters) {
        WrapperExternalBody web= new WrapperExternalBody(products);

        String body="";
        String[] params= epParameters.split(" ");
        try {
            body = omp.writeValueAsString(web);
                if (params.length>1){
                body=body.replaceAll("name", params[0]);
                body=body.replaceAll("quantity", params[1]);
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(BrokerLogicImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return body;
    }


    private String generateGetRequest(String endpoint) {
        System.out.println("generateGetRequest");
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





}
