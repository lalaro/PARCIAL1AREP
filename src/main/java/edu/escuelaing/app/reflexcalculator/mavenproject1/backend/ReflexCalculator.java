/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.app.reflexcalculator.mavenproject1.backend;

/**
 *
 * @author laura.rortegon
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReflexCalculator {
    
    public static String parseAndExecute(String command) {
        // Class command: Class(java.lang.String)
        Pattern classPattern = Pattern.compile("Class\\(([^)]+)\\)");
        Matcher classMatcher = classPattern.matcher(command);
        if (classMatcher.matches()) {
            String className = classMatcher.group(1);
            return ReflectionService.getClassInfo(className);
        }
        
        
        return "{\"error\": \"Invalid command format\"}";
    }
}
