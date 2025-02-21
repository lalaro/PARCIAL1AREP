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
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * HTTP Server implementation of ReflexCalculator
 */
public class HttpReflexCalculatorServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Facade server ready to receive...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            
            OutputStream out = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );
            
            String inputLine;
            boolean isFirstLine = true;
            String uriPath = "";
            
            while ((inputLine = in.readLine()) != null) {
                if (isFirstLine) {
                    uriPath = inputLine.split(" ")[1];
                    isFirstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            
            // Parametro para irse a la pagina
            if (uriPath.equals("/calculadora")) {
                serveClientPage(out);
            } 
           
            else if (uriPath.startsWith("/consulta")) {
                try {
                    int queryIndex = uriPath.indexOf("?");
                    if (queryIndex != -1) {
                        String query = uriPath.substring(queryIndex + 1);
                        String[] params = query.split("=");
                        if (params.length >= 2 && params[0].equals("comando")) {
                            String command = URLDecoder.decode(params[1], StandardCharsets.UTF_8);
                            String backendResponse = HttpConnectionExample.getCommand(command);
                            String response = "HTTP/1.1 200 OK\r\n" +
                                            "Content-Type: application/json\r\n" +
                                            "\r\n" +
                                            backendResponse;
                            out.write(response.getBytes());
                        } else {
                            String response = "HTTP/1.1 400 Bad Request\r\n" +
                                            "Content-Type: application/json\r\n" +
                                            "\r\n" +
                                            "{\"error\": \"Missing command parameter\"}";
                            out.write(response.getBytes());
                        }
                    } else {
                        String response = "HTTP/1.1 400 Bad Request\r\n" +
                                        "Content-Type: application/json\r\n" +
                                        "\r\n" +
                                        "{\"error\": \"Missing query parameters\"}";
                        out.write(response.getBytes());
                    }
                } catch (Exception e) {
                    String response = "HTTP/1.1 500 Internal Server Error\r\n" +
                                    "Content-Type: application/json\r\n" +
                                    "\r\n" +
                                    "{\"error\": \"" + e.getMessage() + "\"}";
                    out.write(response.getBytes());
                }
            } 
            else {
                String response = "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Type: application/json\r\n" +
                                "\r\n" +
                                "{\"error\": \"Resource not found\"}";
                out.write(response.getBytes());
            }
            
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
    
    private static void serveClientPage(OutputStream out) throws IOException {
        String htmlPage = "<!DOCTYPE html>" + 
                            "<html>" + 
                            "<head>" + 
                            "<meta charset=\"UTF-8\">" + 
                            "<title>Calculadora</title>\n" + 
                            "</head>" + 
                            "<body>" + 
                            "<h1>Pagina de inicio</h1>" + 
                            "</body>" + 
                            "</html>"; 
        
        out.write(htmlPage.getBytes());
    }
}