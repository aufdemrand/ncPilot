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
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SerialIO implements SerialPortEventListener {
	SerialPort serialPort;
        /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"COM1",
                        "COM2",
			"COM3",
			"COM4",
                        "COM17"
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
        private class StatusReport{
            public String posy;
            public String posx;
            public String posz;
            public String vel;
        }
        private class Report{
            public String qr;
        }
        private class JSON_Data{
            public StatusReport sr;
            public Report r;
        }
	public synchronized void serialEvent(SerialPortEvent oEvent) {
                Gson g = new Gson();
                Gson qr = new Gson();
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=input.readLine();
				System.out.println(inputLine);
                                
                                Report report = qr.fromJson(inputLine, Report.class);
                                if (report != null)
                                {
                                    if (report.qr != null)
                                    {
                                        System.out.println("(qr)Buffer Available: " + report.qr);
                                        //GlobalData.bufferAvailable = Integer.parseInt(report.qr);
                                        if (Integer.parseInt(report.qr) > 30)
                                        {
                                            GlobalData.bufferWait = false;
                                            GlobalData.bufferAvailable = 10;
                                        }
                                    }
                                }
                                
                                JSON_Data json = g.fromJson(inputLine, JSON_Data.class);
                                //System.out.println(json.posy);
                                if (json != null)
                                {
                                    if (json.sr != null)
                                    {
                                        if (json.sr.posx != null)
                                        {
                                            GlobalData.X = json.sr.posx;
                                        }
                                        if (json.sr.posy != null)
                                        {
                                            GlobalData.Y = json.sr.posy;
                                        }
                                        if (json.sr.posz != null)
                                        {
                                            GlobalData.Z = json.sr.posz;
                                        }
                                        if (json.sr.vel != null)
                                        {
                                            GlobalData.F = json.sr.vel;
                                        } 
                                    }
                                    if (json.r != null)
                                    {
                                        if (json.r.qr != null)
                                        {
                                            System.out.println("(r.qr)Buffer Available: " + json.r.qr);
                                            //GlobalData.bufferAvailable = Integer.parseInt(report.qr);
                                            if (Integer.parseInt(json.r.qr) > 30)
                                            {
                                                GlobalData.bufferWait = false;
                                                GlobalData.bufferAvailable = Integer.parseInt(json.r.qr) - 10;
                                            }
                                        }
                                    }
                                }
                                
                                
                                
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}
}