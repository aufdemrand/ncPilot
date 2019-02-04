/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nc.pilot.lib;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SerialIO implements SerialPortEventListener {
	SerialPort serialPort;
        /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"COM3",
                "/dev/tty.usbmodem1411"
	};
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 115200;

	public void initialize() {

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
        public synchronized void writeByte(byte b)
        {
            //System.out.println("Writing " + data);
             if (serialPort != null){
                 try {
                     output.write(b);
                 } catch (IOException ex) {
                     Logger.getLogger(SerialIO.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
        }
        public synchronized void write(String data)
        {
            //System.out.println("Writing " + data);
             if (serialPort != null){
                     Writer w = null;
                 try {
                     w = new OutputStreamWriter(output, "Cp1252");
                         try {
                             w.write(data);
                         } catch (IOException ex) {
                             Logger.getLogger(SerialIO.class.getName()).log(Level.SEVERE, null, ex);
                         }
                         try {
                             w.close(); //close will auto-flush
                         } catch (IOException ex) {
                             Logger.getLogger(SerialIO.class.getName()).log(Level.SEVERE, null, ex);
                         }
                 } catch (UnsupportedEncodingException ex) {
                     Logger.getLogger(SerialIO.class.getName()).log(Level.SEVERE, null, ex);
                 } finally {
                     try {
                         w.close();
                     } catch (IOException ex) {
                         Logger.getLogger(SerialIO.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }
            }
        }

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
        public synchronized void serialEvent(SerialPortEvent oEvent) {
            if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                try {
                    String inputLine=input.readLine();
                    //System.out.println(inputLine);
                    if (inputLine.equals("ok"))
                    {
                        //System.out.println("OK Recieved!");
                        if (GlobalData.WriteBuffer.size() > 0)
                        {
                            //System.out.println(GlobalData.WriteBuffer.size() + " Writing: " + GlobalData.WriteBuffer.get(0));
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                            write(GlobalData.WriteBuffer.get(0));
                            ArrayList<String> TmpBuffer = new ArrayList();
                            for (int x = 1; x < GlobalData.WriteBuffer.size(); x++)
                            {
                                TmpBuffer.add(GlobalData.WriteBuffer.get(x));
                            }
                            GlobalData.WriteBuffer.clear();
                            for (int x = 0; x < TmpBuffer.size(); x++)
                            {
                                GlobalData.WriteBuffer.add(TmpBuffer.get(x));
                            }

                        }
                    }
                    else if (inputLine.contains("<") && inputLine.contains(">")) //Status Report
                    {
                        GlobalData.last_dro[0] = GlobalData.dro[0];
                        GlobalData.last_dro[1] = GlobalData.dro[1];
                        GlobalData.last_dro[2] = GlobalData.dro[2];
                        String status_output = inputLine.substring(inputLine.indexOf("<") + 1, inputLine.indexOf(">"));
                        String[] status = status_output.split("\\|");

                        GlobalData.status = status[0];
                        //System.out.println("Status: " + GlobalData.status);

                        String mpos = status[1].substring(status[1].indexOf("MPos:") + 5, status[1].length());
                        //System.out.println("pos data: " + mpos);
                        String[] dro = mpos.split(",");
                        GlobalData.dro[0] = Float.parseFloat(dro[0]);
                        GlobalData.dro[1] = Float.parseFloat(dro[1]);
                        GlobalData.dro[2] = Float.parseFloat(dro[2]);
                        //System.out.println("X: " + GlobalData.dro[0] + " Y: " + GlobalData.dro[1] + " Z: " + GlobalData.dro[2]);
                        if (GlobalData.last_dro[0] == GlobalData.dro[0] && GlobalData.last_dro[1] == GlobalData.dro[1] && GlobalData.last_dro[2] == GlobalData.dro[2])
                        {
                            GlobalData.IsInMotion = false;
                            if (GlobalData.pendingReset == true)
                            {
                                MotionController.SoftResetNow();
                            }
                        }
                        else
                        {
                            GlobalData.IsInMotion = true;
                        }
                        if (GlobalData.last_dro[0] == GlobalData.dro[0])
                        {
                            GlobalData.IsXAxisInMotion = false;
                        }
                        else
                        {
                            GlobalData.IsXAxisInMotion = true;
                        }
                        if (GlobalData.last_dro[1] == GlobalData.dro[1])
                        {
                            GlobalData.IsYAxisInMotion = false;
                        }
                        else
                        {
                            GlobalData.IsYAxisInMotion = true;
                        }
                        if (GlobalData.last_dro[2] == GlobalData.dro[2])
                        {
                            GlobalData.IsZAxisInMotion = false;
                        }
                        else
                        {
                            GlobalData.IsZAxisInMotion = true;
                        }
                        //System.out.println("IsInMotion: " + GlobalData.IsInMotion);

                    }
                    else if (inputLine.contains("[") && inputLine.contains("]")) //Work Offset Parameters
                    {
                        String parameter_output = inputLine.substring(inputLine.indexOf("[") + 1, inputLine.indexOf("]"));
                        String[] g_param = parameter_output.split(":");
                        //System.out.println("Param: " + g_param[0]);
                        if (g_param[0].equals("G92"))
                        {
                            String[] pos = g_param[1].split(",");
                            GlobalData.work_offset[0] = Float.parseFloat(pos[0]);
                            GlobalData.work_offset[1] = Float.parseFloat(pos[1]);
                            GlobalData.work_offset[2] = Float.parseFloat(pos[2]);
                        }
                    }

                } catch (Exception e) {
                    //System.err.println(e.toString());
                }
            }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }
}