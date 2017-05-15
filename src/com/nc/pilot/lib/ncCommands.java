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
    public static String GoToOrigin = "G90 G0 X0 Y0\r\n";
    public static String FeedHold = "!\r\n";
    public static String CycleStart = "$xvm=5\r\n$yvm=5\r\n$ej=1\r\n~\r\n";
    public static String QueFlush = "%\r\n$xvm=50\r\n$yvm=50\r\n$ej=1\r\n";
    
    //Y Jogging
    public static String StartJogYPlus = "G91 G0 Y100\r\n";
    public static String StopJogYPlus = "!%\r\nG90\r\n";
    
    public static String StartJogYMinus = "G91 G0 Y-100\r\n";
    public static String StopJogYMinus = "!%\r\nG90\r\n";
    
    //X Jogging
    public static String StartJogXPlus = "G91 G0 X100\r\n";
    public static String StopJogXPlus = "!%\r\nG90\r\n";
    
    public static String StartJogXMinus = "G91 G0 X-100\r\n";
    public static String StopJogXMinus = "!%\r\nG90\r\n";
    
    //Z Jogging
    public static String StartJogZPlus = "G91 G0 Z100\r\n";
    public static String StopJogZPlus = "!%\r\nG90\r\n";
    
    public static String StartJogZMinus = "G91 G0 Z-100\r\n";
    public static String StopJogZMinus = "!%\r\nG90\r\n";
    
    //Set origins
    public static String SetXOrigin = "G92 X=0\r\nG53\r\nG28.3 X=0.000\r\n";
    public static String SetYOrigin = "G92 Y=0\r\nG53\r\nG28.3 Y=0.000\r\n";
    public static String SetZOrigin = "G92 Z=0\r\nG53\r\nG28.3 Z=0.000\r\n";
    
    //M Codes
    public static String TorchOn = "M3\r\n";
    public static String TorchOff = "M5\r\n";
}
