/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.app.reflexcalculator.mavenproject1.backend;

/**
 *
 * @author laura.rortegon
 */

import java.lang.reflect.*;
import java.util.*;

public class ReflectionService {
    
    public static String getClassInfo(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            
            // Get declared fields
            Field[] fields = clazz.getDeclaredFields();
            List<String> fieldNames = new ArrayList<>();
            for (Field field : fields) {
                fieldNames.add(field.getName() + " (" + field.getType().getSimpleName() + ")");
            }
            
            // Get declared methods
            Method[] methods = clazz.getDeclaredMethods();
            List<String> methodNames = new ArrayList<>();
            for (Method method : methods) {
                StringBuilder methodInfo = new StringBuilder();
                methodInfo.append(method.getName()).append("(");
                
                Class<?>[] paramTypes = method.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    methodInfo.append(paramTypes[i].getSimpleName());
                    if (i < paramTypes.length - 1) {
                        methodInfo.append(", ");
                    }
                }
              
                methodInfo.append(") : ").append(method.getReturnType().getSimpleName());
                
                methodNames.add(methodInfo.toString());
            }
            
            // Create JSON response
            return buildJsonResponse(className, fieldNames, methodNames);
            
        } catch (ClassNotFoundException e) {
            return "{\"error\": \"Class not found: " + className + "\"}";
        }
    }
    
  
    
    private static Class<?> getParamClass(String paramType) throws ClassNotFoundException {
        switch (paramType.toLowerCase()) {
            case "int": return int.class;
            case "double": return double.class;
            default: throw new ClassNotFoundException("Unsupported parameter type: " + paramType);
        }
    }
    
    private static Object convertParam(String paramType, String paramValue) {
        switch (paramType.toLowerCase()) {
            case "int": return Integer.parseInt(paramValue);
            case "double": return Double.parseDouble(paramValue);
            case "string": return paramValue;
            default: return paramValue;
        }
    }
    
    private static String buildJsonResponse(String className, List<String> fields, List<String> methods) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"className\": \"").append(className).append("\", ");
        
        // Add fields
        json.append("\"fields\": [");
        for (int i = 0; i < fields.size(); i++) {
            json.append("\"").append(fields.get(i)).append("\"");
            if (i < fields.size() - 1) {
                json.append(", ");
            }
        }
        json.append("], ");
        
        // Add methods
        json.append("\"methods\": [");
        for (int i = 0; i < methods.size(); i++) {
            json.append("\"").append(methods.get(i)).append("\"");
            if (i < methods.size() - 1) {
                json.append(", ");
            }
        }
        json.append("]");
        
        json.append("}");
        return json.toString();
    }
}