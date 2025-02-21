/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.app.reflexcalculator.mavenproject1.frontend;

/**
 *
 * @author laura.rortegon
 */
import java.net.*;
import java.io.*;

public class HttpServer {
  public static void main(String[] args) throws IOException {
   ServerSocket serverSocket = null;
   try { 
      serverSocket = new ServerSocket(36000);
      System.out.println("Listo para recibir...");
   } catch (IOException e) {
      System.err.println("Could not listen on port: 35000.");
      System.exit(1);
   }

   Socket clientSocket = null;
   try {
       System.out.println("Listo para recibir ...");
       clientSocket = serverSocket.accept();
   } catch (IOException e) {
       System.err.println("Accept failed.");
       System.exit(1);
   }
   PrintWriter out = new PrintWriter(
                         clientSocket.getOutputStream(), true);
   BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream()));
   String inputLine, outputLine;
   while ((inputLine = in.readLine()) != null) {
      System.out.println("Recibí: " + inputLine);
      if (!in.ready()) {break; }
   }
   outputLine = 
          "<!DOCTYPE html>" + 
          "<html>" + 
          "<head>" + 
          "<meta charset=\"UTF-8\">" + 
          "<title>Calculadora</title>\n" + 
          "</head>" + 
          "<body>" + 
          "<h1>Mi propio mensaje</h1>" + 
          "</body>" + 
          "</html>"; 
    out.println(outputLine);
    out.close(); 
    in.close(); 
    clientSocket.close(); 
    serverSocket.close(); 
  }
}