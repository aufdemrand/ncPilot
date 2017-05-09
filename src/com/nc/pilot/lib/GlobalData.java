/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nc.pilot.lib;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author travis
 */
public class GlobalData {
    public static String X = "0.000";
    public static String Y = "0.000";
    public static String Z = "0.000";
    
    public static Boolean KeycodeExecute = false;
    public static Boolean AltPressed = false;
    public static Boolean GCodeWindowFocused = false;
    
    public static Boolean TorchOn = false;
    
    public static String NCFile;
    public static String NC_Code;
    
    public static Boolean Auto = false;
    
    public static int bufferSize = 254; //bytes
    public static int bufferAvailable = 32; //bytes
    public static int bufferPosition = 0; //bytes
    
    public static String readFile(String path) throws IOException 
    {
       byte[] encoded = Files.readAllBytes(Paths.get(path));
       return new String(encoded);
    }
}
