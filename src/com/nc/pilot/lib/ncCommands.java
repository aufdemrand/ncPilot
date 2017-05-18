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

    public static String Home = "G53\r\nG28.2 Y0\r\nG28.2 X0\r\n";

    public static String FeedHold = "!\r\n";
    public static String CycleStart = "~\r\n";
    public static String QueFlush = "$clear\r\n%\r\n";
    
    //Y Jogging
    public static String StartJogYPlus = "G53 G0 Y" + ncConfig.YMaxTravel +"\r\n";
    public static String StopJogYPlus = "!%\r\nG54\r\n";
    
    public static String StartJogYMinus = "G53 G0 Y" + ncConfig.YMinTravel +"\r\n";
    public static String StopJogYMinus = "!%\r\nG54\r\n";
    
    //X Jogging
    public static String StartJogXPlus = "G53 G0 X" + ncConfig.XMaxTravel +"\r\n";
    public static String StopJogXPlus = "!%\r\nG54\r\n";
    
    public static String StartJogXMinus = "G53 G0 X" + ncConfig.XMinTravel +"\r\n";
    public static String StopJogXMinus = "!%\r\nG54\r\n";
    
    //Z Jogging
    public static String StartJogZPlus = "G53 G0 Z" + ncConfig.ZMaxTravel +"\r\n";
    public static String StopJogZPlus = "!%\r\nG54\r\n";
    
    public static String StartJogZMinus = "G53 G0 Z" + ncConfig.XMinTravel +"\r\n";
    public static String StopJogZMinus = "!%\r\nG54\r\n";
    
    //Set origins
    public static String SetXOrigin = "G54 G92 X0.000\r\n";
    public static String SetYOrigin = "G54 G92 Y0.000\r\n";
    public static String SetZOrigin = "G92 Z=0\r\nG54\r\nG28.3 Z=0.000\r\n";
    
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
        s.write("$sl=0\r\n"); //Disable Soft Limits
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
