/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.app.reflexcalculator.mavenproject1.frontend;

/**
 *
 * @author laura.rortegon
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Class for HTTP connection to the backend server
 */
public class HttpConnectionExample {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String BACKEND_URL = "http://localhost:45000/compreflex?comando=";
    
    public static String getCommand(String command) throws IOException {
        String encodedCommand = URLEncoder.encode(command, StandardCharsets.UTF_8);
        URL obj = new URL(BACKEND_URL + encodedCommand);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
   
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_OK) { 
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            return response.toString();
        } else {
            return "{\"error\": \"Backend server error: " + responseCode + "\"}";
        }
    }
}