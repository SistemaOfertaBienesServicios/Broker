/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author cristianmendi
 */
public class pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //generateGetRequest("https://jsonplaceholder.typicode.com/posts/1");
        generatePOSTRequest();
    }
    
    private static void generateGetRequest(String endpoint) throws IOException {
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
            System.out.println("JSON String Result " + response.toString());
            //GetAndPost.POSTRequest(response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }
    }
    
    
    public static void generatePOSTRequest() throws IOException {
    final String POST_PARAMS = "{\n" + "\"userId\": 101,\r\n" +
        "    \"id\": 101,\r\n" +
        "    \"title\": \"Test Title\",\r\n" +
        "    \"body\": \"Test Body\"" + "\n}";
    System.out.println(POST_PARAMS);
    URL obj = new URL("https://jsonplaceholder.typicode.com/posts");
    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
    postConnection.setRequestMethod("POST");
    postConnection.setRequestProperty("userId", "a1bcdefgh");
    postConnection.setRequestProperty("Content-Type", "application/json");
    postConnection.setDoOutput(true);
    OutputStream os = postConnection.getOutputStream();
    os.write(POST_PARAMS.getBytes());
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
        while ((inputLine = in .readLine()) != null) {
            response.append(inputLine);
        } in .close();
        // print result
        System.out.println(response.toString());
    } else {
        System.out.println("POST NOT WORKED");
    }
}
}
