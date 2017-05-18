/*
 * Copyright (c) 2010, Oracle.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */


package com.nc.pilot.ui;

import com.nc.pilot.lib.GlobalData;
import com.nc.pilot.lib.SerialIO;
import com.nc.pilot.lib.ncCommands;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class HMI extends JFrame{

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels=javax.swing.UIManager.getInstalledLookAndFeels();
            for (int idx=0; idx<installedLookAndFeels.length; idx++)
                if ("Nimbus".equals(installedLookAndFeels[idx].getName())) {
                    javax.swing.UIManager.setLookAndFeel(installedLookAndFeels[idx].getClassName());
                    break;
                }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HMI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HMI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HMI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HMI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HMI().setVisible(true);
            }
        });
    }

    private int wordIdx = 0;
    private Timer timer = null;
    private SerialIO serial;
    public HMI() {
        setFocusable(true);
        serial = new SerialIO();
        serial.initialize();
     
        
        initComponents();
        pack();
        timer = new Timer(10, new MyActionListener());
        timer.setInitialDelay(0);
        timer.start();
        
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
              switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_UP && GlobalData.GCodeWindowFocused == false) {
                            if (GlobalData.KeycodeExecute == false)
                            {
                                GlobalData.KeycodeExecute = true;
                                serial.write(ncCommands.StartJogYPlus);
                            }
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_DOWN && GlobalData.GCodeWindowFocused == false) {
                            if (GlobalData.KeycodeExecute == false)
                            {
                                GlobalData.KeycodeExecute = true;
                                serial.write(ncCommands.StartJogYMinus);
                            }
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_LEFT && GlobalData.GCodeWindowFocused == false) {
                            if (GlobalData.KeycodeExecute == false)
                            {
                                GlobalData.KeycodeExecute = true;
                                serial.write(ncCommands.StartJogXMinus);
                            }
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_RIGHT && GlobalData.GCodeWindowFocused == false) {
                            if (GlobalData.KeycodeExecute == false)
                            {
                                GlobalData.KeycodeExecute = true;
                                serial.write(ncCommands.StartJogXPlus);
                            }
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_PAGE_UP && GlobalData.GCodeWindowFocused == false) {
                            if (GlobalData.KeycodeExecute == false)
                            {
                                GlobalData.KeycodeExecute = true;
                                serial.write(ncCommands.StartJogZPlus);
                            }
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_PAGE_DOWN && GlobalData.GCodeWindowFocused == false) {
                            if (GlobalData.KeycodeExecute == false)
                            {
                                GlobalData.KeycodeExecute = true;
                                serial.write(ncCommands.StartJogZMinus);
                            }
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                            if (GlobalData.KeycodeExecute == false)
                            {
                                GlobalData.KeycodeExecute = true;
                            }
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_R && GlobalData.AltPressed == true) {
                            if (GlobalData.KeycodeExecute == false)
                            {
                                GlobalData.KeycodeExecute = true;
                            }
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_ALT) {
                                GlobalData.AltPressed = true;
                        }
                        break;

                    case KeyEvent.KEY_RELEASED:
                        if (ke.getKeyCode() == KeyEvent.VK_ALT) {
                            GlobalData.AltPressed = false;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_UP && GlobalData.GCodeWindowFocused == false) {
                            GlobalData.KeycodeExecute = false;
                            serial.write(ncCommands.StopJogYPlus);
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_DOWN && GlobalData.GCodeWindowFocused == false) {
                            GlobalData.KeycodeExecute = false;
                            serial.write(ncCommands.StopJogYMinus);
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_RIGHT && GlobalData.GCodeWindowFocused == false) {
                            GlobalData.KeycodeExecute = false;
                            serial.write(ncCommands.StopJogXPlus);
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_LEFT && GlobalData.GCodeWindowFocused == false) {
                            GlobalData.KeycodeExecute = false;
                            serial.write(ncCommands.StopJogXMinus);
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_PAGE_UP && GlobalData.GCodeWindowFocused == false) {
                            GlobalData.KeycodeExecute = false;
                            serial.write(ncCommands.StopJogZPlus);
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_PAGE_DOWN && GlobalData.GCodeWindowFocused == false) {
                            GlobalData.KeycodeExecute = false;
                            serial.write(ncCommands.StopJogZMinus);
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                            GlobalData.KeycodeExecute = false;
                            serial.write(ncCommands.FeedHold);
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_R && GlobalData.AltPressed == true) {
                            GlobalData.KeycodeExecute = false;
                            //serial.write(ncCommands.CycleStart);
                            jButton12MouseClicked(null);
                        }
                        break;
                    }
                return false;
            }
        });
        
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton14 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        mainMenu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();

        setTitle("ncPilot");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel1.setMinimumSize(new java.awt.Dimension(900, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 600));
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPanel1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jPanel1KeyTyped(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Z");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel3.setText("0.000");
        jPanel1.add(jLabel3, new AbsoluteConstraints(110, 160, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("Y");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("X");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel6.setText("0.000");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel7.setText("0.000");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, -1, -1));

        jButton1.setText("Home");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 340, 80, 60));

        jButton3.setText("Y+");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton3MouseReleased(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton3KeyPressed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 280, 80, 60));

        jButton2.setText("Y-");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton2MouseReleased(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, 80, 60));

        jButton4.setText("X-");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton4MouseReleased(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 340, 80, 60));

        jButton5.setText("X+");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton5MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton5MouseReleased(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 340, 80, 60));

        jButton6.setText("Z+");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton6MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton6MouseReleased(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 260, 80, 60));

        jButton7.setText("Flush");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 260, 80, 60));

        jButton8.setText("Z=0");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 410, 80, 60));

        jButton9.setText("Y=0");
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton9MouseClicked(evt);
            }
        });
        jPanel1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 410, 80, 60));

        jButton10.setText("Z-");
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton10MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton10MouseReleased(evt);
            }
        });
        jPanel1.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 410, 80, 60));

        jButton11.setText("Hold");
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
        });
        jPanel1.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 410, 80, 60));

        jButton12.setText("Start");
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton12MouseClicked(evt);
            }
        });
        jPanel1.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 340, 80, 60));

        jButton13.setText("X=0");
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton13MouseClicked(evt);
            }
        });
        jPanel1.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 80, 60));

        jToggleButton1.setText("Torch");
        jToggleButton1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jToggleButton1StateChanged(evt);
            }
        });
        jToggleButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton1MouseClicked(evt);
            }
        });
        jPanel1.add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 470, -1, -1));

        jButton14.setText("Open NC");
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton14MouseClicked(evt);
            }
        });
        jPanel1.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, 120, 80));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea1FocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 540, 200));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel8.setText("0.000");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("F");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        jLabel1.setText("0");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, -1, -1));

        jLabel10.setText("Lines Sent:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, -1, -1));

        jLabel11.setText("Lines Total:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, -1, -1));

        jLabel12.setText("0");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, -1, -1));

        jLabel13.setText("Free Buffer:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 250, -1, -1));

        jLabel14.setText("0");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 250, -1, -1));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        fileMenu.setMnemonic('F');
        fileMenu.setText("File");

        aboutMenuItem.setMnemonic('A');
        aboutMenuItem.setText("About");
        aboutMenuItem.setToolTipText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(aboutMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, java.awt.event.InputEvent.ALT_MASK));
        exitMenuItem.setMnemonic('E');
        exitMenuItem.setText("Exit");
        exitMenuItem.setToolTipText("Quit Team, Quit!");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        mainMenu.add(fileMenu);

        setJMenuBar(mainMenu);
    }// </editor-fold>//GEN-END:initComponents

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        new About(this).setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        serial.close();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        serial.close();
        System.exit(0);
    }//GEN-LAST:event_exitForm

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        serial.write(ncCommands.Home);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
      
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseClicked
        System.out.println("Run!");
        if (GlobalData.Auto == false)
        {
            //Start feeding code
            GlobalData.Auto = true;
            GlobalData.HMC_Auto = true;
            GlobalData.LinePosition = 0;
            GlobalData.NC_Code = jTextArea1.getText();
            ncCommands.AutoMode(serial);
        }
        else
        {
            serial.write(ncCommands.CycleStart);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12MouseClicked

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked
        serial.write(ncCommands.FeedHold);  // TODO add your handling code here:
    }//GEN-LAST:event_jButton11MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        serial.write(ncCommands.QueFlush);        // TODO add your handling code here:
        GlobalData.Auto = false;
        GlobalData.LinePosition = 0;

        ncCommands.ManualMode(serial);
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MousePressed
        serial.write(ncCommands.StartJogYPlus);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MousePressed

    private void jButton3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseReleased
        serial.write(ncCommands.StopJogYPlus); // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseReleased

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        serial.write(ncCommands.StartJogYMinus);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseReleased
        serial.write(ncCommands.StopJogYMinus); // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseReleased

    private void jButton5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MousePressed
        serial.write(ncCommands.StartJogXPlus);// TODO add your handling code here:
    }//GEN-LAST:event_jButton5MousePressed

    private void jButton5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseReleased
        serial.write(ncCommands.StopJogXPlus);// TODO add your handling code here:
    }//GEN-LAST:event_jButton5MouseReleased

    private void jButton4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MousePressed
        serial.write(ncCommands.StartJogXMinus);// TODO add your handling code here:
    }//GEN-LAST:event_jButton4MousePressed

    private void jButton4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseReleased
        serial.write(ncCommands.StopJogXMinus);// TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseReleased

    private void jButton6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MousePressed
        serial.write(ncCommands.StartJogZPlus);// TODO add your handling code here:
    }//GEN-LAST:event_jButton6MousePressed

    private void jButton6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseReleased
        serial.write(ncCommands.StopJogZPlus);// TODO add your handling code here:
    }//GEN-LAST:event_jButton6MouseReleased

    private void jButton10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MousePressed
        serial.write(ncCommands.StartJogZMinus);// TODO add your handling code here:
    }//GEN-LAST:event_jButton10MousePressed

    private void jButton10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseRelease
        serial.write(ncCommands.StopJogZMinus);// TODO add your handling code here:
    }//GEN-LAST:event_jButton10MouseReleased

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked
        serial.write(ncCommands.SetXOrigin);    // TODO add your handling code here:
    }//GEN-LAST:event_jButton13MouseClicked

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        serial.write(ncCommands.SetYOrigin);// TODO add your handling code here:
    }//GEN-LAST:event_jButton9MouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        serial.write(ncCommands.SetZOrigin);// TODO add your handling code here:
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyPressed
        
    }//GEN-LAST:event_jButton3KeyPressed

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
         
    }//GEN-LAST:event_jPanel1KeyPressed

    private void jPanel1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyReleased
              // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1KeyReleased

    private void jPanel1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyTyped
        
    }//GEN-LAST:event_jPanel1KeyTyped

    private void jToggleButton1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jToggleButton1StateChanged
        
    // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1StateChanged

    private void jToggleButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseClicked
        if (GlobalData.TorchOn == false)
        {
            GlobalData.TorchOn = true;
            serial.write(ncCommands.TorchOn);
        }
        else
        {
            GlobalData.TorchOn = false;
            serial.write(ncCommands.TorchOff);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1MouseClicked

    private void jButton14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            GlobalData.NCFile = selectedFile.getAbsolutePath();
            try {
                jTextArea1.setText(GlobalData.readFile(GlobalData.NCFile));
                GlobalData.NC_Code = GlobalData.readFile(GlobalData.NCFile);
            } catch (IOException ex) {
                Logger.getLogger(HMI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14MouseClicked

    private void jTextArea1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea1FocusGained
        // TODO add your handling code here:
        GlobalData.GCodeWindowFocused = true;
        //System.out.println("Focused!");
    }//GEN-LAST:event_jTextArea1FocusGained

    private void jTextArea1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea1FocusLost
        // TODO add your handling code here:
        GlobalData.GCodeWindowFocused = false;
        //System.out.println("UnFocused!");
    }//GEN-LAST:event_jTextArea1FocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JMenuBar mainMenu;
    // End of variables declaration//GEN-END:variables

    class MyActionListener implements ActionListener {

        private Integer skipTicks = 5;
        private Integer tickCount = 0;

        public void actionPerformed(ActionEvent e) {

            jLabel6.setText(GlobalData.X);
            jLabel7.setText(GlobalData.Y);
            jLabel3.setText(GlobalData.Z);
            jLabel8.setText(GlobalData.F);
            jLabel1.setText(String.valueOf(GlobalData.LinePosition));
            jLabel12.setText(String.valueOf(GlobalData.NC_Lines));
            jLabel14.setText(String.valueOf(GlobalData.FreeBuffers));

            tickCount++;
            if (tickCount < skipTicks) {
                return;
            }
            tickCount = 0;
            if (GlobalData.Auto == true) {
                String[] lines = GlobalData.NC_Code.split("\n");
                GlobalData.NC_Lines = lines.length;
                if (GlobalData.PlannerReady) {
                    GlobalData.SendOnce = true;
                    //System.out.println("Buffer Availible = " + GlobalData.bufferAvailable);
                    if (lines.length > GlobalData.LinePosition) {
                        System.out.println("Sending!");
                        String b = new StringBuilder().append(lines[GlobalData.LinePosition]).toString();
                        System.out.println(b);
                        serial.write(b + "\r\n");
                        GlobalData.LinePosition++;

                        serial.write("{\"qr\":\"\"}\r\n");
                    } else {
                        if (lines.length == GlobalData.LinePosition) {
                            System.out.println("End of file!");
                            GlobalData.Auto = false;
                            ncCommands.ManualMode(serial);
                        }
                    }
                } else {
                    System.out.println("Waiting for Buffer to free up!");
                    if (GlobalData.SendOnce) {
                        GlobalData.SendOnce = false;
                        serial.write("{\"qr\":null}\r\n");
                    }


                }
            }
        }
    }
}
