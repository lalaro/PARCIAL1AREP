/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.app.reflexcalculator.mavenproject1.backend;

/**
 *
 * @author laura
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
 * HTTP Server implementation for the backend reflective service
 */
public class HttpBackendServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(45000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 45000.");
            System.exit(1);
        }
        
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Backend server ready to receive...");
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
            
            String response;
            if (uriPath.startsWith("/compreflex")) {
                try {
                    int queryIndex = uriPath.indexOf("?");
                    if (queryIndex != -1) {
                        String query = uriPath.substring(queryIndex + 1);
                        String[] params = query.split("=");
                        if (params.length >= 2 && params[0].equals("comando")) {
                            String command = URLDecoder.decode(params[1], StandardCharsets.UTF_8);
                            String result = "Hello"; //CommandParser.parseAndExecute(command);
                            response = "HTTP/1.1 200 OK\r\n" +
                                      "Content-Type: application/json\r\n" +
                                      "Access-Control-Allow-Origin: *\r\n" +
                                      "\r\n" +
                                      result;
                        } else {
                            response = "HTTP/1.1 400 Bad Request\r\n" +
                                      "Content-Type: application/json\r\n" +
                                      "\r\n" +
                                      "{\"error\": \"Missing command parameter\"}";
                        }
                    } else {
                        response = "HTTP/1.1 400 Bad Request\r\n" +
                                  "Content-Type: application/json\r\n" +
                                  "\r\n" +
                                  "{\"error\": \"Missing query\"}";
                    }
                } catch (Exception e) {
                    response = "HTTP/1.1 500 Internal Server Error\r\n" +
                              "Content-Type: application/json\r\n" +
                              "\r\n" +
                              "{\"error\": \"" + e.getMessage() + "\"}";
                }
            } else {
                response = "HTTP/1.1 404 Not Found\r\n" +
                          "Content-Type: application/json\r\n" +
                          "\r\n" +
                          "{\"error\": \"Resource not found\"}";
            }
            
            out.write(response.getBytes());
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
}