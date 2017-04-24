/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nc.pilot.lib;

/**
 *
 * @author travis
 */
public class ncCommands {
    public static String GoToOrigin = "G0 X0 Y0\r\n";
    public static String FeedHold = "!\r\n";
    public static String CycleStart = "~\r\n";
    public static String QueFlush = "%\r\n";
    
    //Y Jogging
    public static String StartJogYPlus = "G91 G0 Y5 G90\r\n";
    public static String StopJogYPlus = "!%\r\n";
    
    public static String StartJogYMinus = "G91 G0 Y-5 G90\r\n";
    public static String StopJogYMinus = "!%\r\n";
    
    //X Jogging
    public static String StartJogXPlus = "G91 G0 X5 G90\r\n";
    public static String StopJogXPlus = "!%\r\n";
    
    public static String StartJogXMinus = "G91 G0 X-5 G90\r\n";
    public static String StopJogXMinus = "!%\r\n";
    
    //Z Jogging
    public static String StartJogZPlus = "G91 G0 Z5 G90\r\n";
    public static String StopJogZPlus = "!%\r\n";
    
    public static String StartJogZMinus = "G91 G0 Z-5 G90\r\n";
    public static String StopJogZMinus = "!%\r\n";
    
    //Set origins
    public static String SetXOrigin = "G28.3 X0\r\n";
    public static String SetYOrigin = "G28.3 Y0\r\n";
    public static String SetZOrigin = "G28.3 Z0\r\n";
    
    //M Codes
    public static String TorchOn = "M3\r\n";
    public static String TorchOff = "M5\r\n";
}
