/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nc.pilot.lib;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author travis
 */
public class GlobalData {
    /* Depricated but still here so HMI.java compiles */
    public static String X = "0.000";
    public static String Y = "0.000";
    public static String Z = "0.000";
    public static String F = "0.000";

    public static float[] dro = {0, 0, 0};
    public static float[] work_offset = {20, 20, 0};
    public static String status;
    public static boolean IsHomed = false;

    public static float X_Extents = 48.250f;
    public static float Y_Extents = 45.500f;
    public static float Z_Extents = 4.000f;


    public static Boolean KeycodeExecute = false;
    public static Boolean AltPressed = false;
    public static Boolean GCodeWindowFocused = false;

    public static Boolean TorchOn = false;

    public static String NCFile;
    public static String NC_Code;
    public static Integer NC_Lines;
    
    public static Boolean Auto = false; //ncPilot exits "Auto mode" after file is fully sent but HMC usually is still in cycle because moves are left in the planner.
    public static Boolean HMC_Auto = false; //HMC_Auto returns to false after machine state from changes to "3" (stop)
    
    public static int LinePosition = 0;
    public static int FreeBuffers = 0;
    public static boolean PlannerReady = false;
    public static boolean SendOnce = true;

    public static float ViewerZoom = 19f;
    public static float MinViewerZoom = 0.005f;
    public static float MaxViewerZoom = 300;
    public static float[] ViewerPan = {438, 900};
    public static int MousePositionX;
    public static int MousePositionY;
    public static float MousePositionX_MCS;
    public static float MousePositionY_MCS;

    
    public static String readFile(String path) throws IOException 
    {
       byte[] encoded = Files.readAllBytes(Paths.get(path));
       return new String(encoded);
    }
}