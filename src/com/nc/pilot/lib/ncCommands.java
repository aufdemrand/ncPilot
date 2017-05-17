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
    public static String CycleStart = "~\r\n";
    public static String QueFlush = "$clear\r\n%\r\n";
    
    //Y Jogging
    public static String StartJogYPlus = "G53 G0 " + ncConfig.YMaxTravel +"\r\n";
    public static String StopJogYPlus = "!%\r\n";
    
    public static String StartJogYMinus = "G53 G0 " + ncConfig.YMinTravel +"\r\n";
    public static String StopJogYMinus = "!%\r\n";
    
    //X Jogging
    public static String StartJogXPlus = "G53 G0 " + ncConfig.XMaxTravel +"\r\n";
    public static String StopJogXPlus = "!%\r\n";
    
    public static String StartJogXMinus = "G53 G0 " + ncConfig.XMinTravel +"\r\n";
    public static String StopJogXMinus = "!%\r\nG90\r\n";
    
    //Z Jogging
    public static String StartJogZPlus = "G90 G0 Z100\r\n";
    public static String StopJogZPlus = "!%\r\nG90\r\n";
    
    public static String StartJogZMinus = "G90 G0 Z-100\r\n";
    public static String StopJogZMinus = "!%\r\nG90\r\n";
    
    //Set origins
    public static String SetXOrigin = "G92 X=0\r\nG53\r\nG28.3 X=0.000\r\n";
    public static String SetYOrigin = "G92 Y=0\r\nG53\r\nG28.3 Y=0.000\r\n";
    public static String SetZOrigin = "G92 Z=0\r\nG53\r\nG28.3 Z=0.000\r\n";
    
    //M Codes
    public static String TorchOn = "M3\r\n";
    public static String TorchOff = "M5\r\n";

    public static void WriteWait() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    public static void ManualMode(SerialIO s)
    {
        s.write("$sl=1\r\n"); //Enable Soft Limits
        ncCommands.WriteWait();
        s.write("$xjm=" + ncConfig.ManualModeAcceleration + "\r\n");
        ncCommands.WriteWait();
        s.write("$yjm=" + ncConfig.ManualModeAcceleration + "\r\n");
        ncCommands.WriteWait();
        s.write("$ej=1\r\n");
        ncCommands.WriteWait();
        s.write(ncCommands.TorchOff);
    }

    public static void AutoMode(SerialIO s)
    {
        s.write("$sl=1\r\n"); //Enable Soft Limits
        ncCommands.WriteWait();
        s.write("$xjm=" + ncConfig.AutoModeAcceleration + "\r\n");
        ncCommands.WriteWait();
        s.write("$yjm=" + ncConfig.AutoModeAcceleration + "\r\n");
        ncCommands.WriteWait();
        s.write("$ej=1\r\n");
        ncCommands.WriteWait();
    }
}
