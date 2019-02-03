package com.nc.pilot.ui;

import com.nc.pilot.lib.GlobalData;
import com.nc.pilot.lib.SerialIO;
import com.nc.pilot.lib.ViewerEntity;
import com.nc.pilot.lib.ncCommands;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

/**
 * This program demonstrates how to draw lines using Graphics2D object.
 * @author www.codejava.net
 *
 */
public class XmotionGen3 extends JFrame {

    ArrayList<ViewerEntity> ViewerEntityStack = new ArrayList();
    private SerialIO serial;
    Timer repaint_timer = new Timer();
    public int move_lines = 0;
    public XmotionGen3() {

        super("ncPilot Xmotion Gen3");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        serial = new SerialIO();
        serial.initialize();
        GcodeViewerPanel panel = new GcodeViewerPanel();
        add(panel);
        repaint_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                serial.write(ncCommands.SatusReport);
                repaint();
            }
        }, 0, 100);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println(e.getX() + "," + e.getY());
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {// provides empty implementation of all
            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.println(e.getX() + "," + e.getY());
                GlobalData.MousePositionX = e.getX();
                GlobalData.MousePositionY = e.getY();
            }
        });
        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                if (e.getWheelRotation() < 0) {
                    GlobalData.ViewerZoom *= 1.1;
                    //System.out.println("ViewerZoom: " + GlobalData.ViewerZoom);
                    if (GlobalData.ViewerZoom > GlobalData.MaxViewerZoom)
                    {
                        GlobalData.ViewerZoom = GlobalData.MaxViewerZoom;
                    }
                } else {
                    GlobalData.ViewerZoom *= 0.9;
                    //System.out.println("ViewerZoom: " + GlobalData.ViewerZoom);
                    if (GlobalData.ViewerZoom < GlobalData.MinViewerZoom)
                    {
                        GlobalData.ViewerZoom = GlobalData.MinViewerZoom;
                    }
                }
                repaint();
            }
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent ke) {
                        switch (ke.getID()) {
                            case KeyEvent.KEY_PRESSED:
                                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                                    //move_lines++;
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                                    //move_lines--;
                                }
                                //System.out.println("Move_Lines: " + move_lines);
                                //repaint();
                                break;

                            case KeyEvent.KEY_RELEASED:
                                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                                    serial.write("?\n");
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                                    serial.write("G1X10F10\n");
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                                    //Load up a bunch of lines onto viewer entity stack and repaint
                                    ViewerEntity entity = new ViewerEntity();
                                    entity.setLine(new float[]{50, 50}, new float[]{500, 50});
                                    ViewerEntityStack.add(entity);

                                    entity = new ViewerEntity();
                                    entity.setLine(new float[]{500, 50}, new float[]{500.5f, 500.5f});
                                    ViewerEntityStack.add(entity);

                                    entity = new ViewerEntity();
                                    entity.setLine(new float[]{500, 500}, new float[]{50, 500});
                                    ViewerEntityStack.add(entity);

                                    entity = new ViewerEntity();
                                    entity.setLine(new float[]{50, 500}, new float[]{50, 50});
                                    ViewerEntityStack.add(entity);

                                    repaint();
                                    System.out.println("Added Entities!");

                                }

                                break;
                        }
                        return false;
                    }
                });
    }
    // create a panel that you can draw on.
    class GcodeViewerPanel extends JPanel {
        public void paint(Graphics g) {
            Rectangle Frame_Bounds = this.getParent().getBounds();
            Graphics2D g2d = (Graphics2D) g;
            /* Begin Wallpaper */
            g.setColor(Color.black);
            g.fillRect(0,0,Frame_Bounds.width,Frame_Bounds.height);
            /* End Wallpaper */

            g.setColor(Color.white);

            //g.fillRect(10,10,100,100);
            for(int i=0;i< ViewerEntityStack.size();i++)
            {
                ViewerEntity entity = ViewerEntityStack.get(i);
                if (entity.type == 1) //We are a line move
                {
                    //g.drawLine(entity.start[0], entity.start[1], entity.end[0], entity.end[1]);
                    g2d.draw(new Line2D.Float(entity.start[0] * GlobalData.ViewerZoom, entity.start[1] * GlobalData.ViewerZoom, entity.end[0] * GlobalData.ViewerZoom, entity.end[1] * GlobalData.ViewerZoom));
                    //System.out.println("(" + i + ") Drawing Line Move: start: (" + entity.start[0] + ", " + entity.start[1] + ") end (" + entity.end[0] + ", " + entity.end[1] + ")");
                }
            }

            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            if (GlobalData.IsHomed == false)
            {
                g.setColor(Color.red);
            }
            else
            {
                g.setColor(Color.green);
            }

            g.drawString("X:", Frame_Bounds.width - 250, 50);
            g.drawString("Y:", Frame_Bounds.width - 250, 90);
            g.drawString("Z:", Frame_Bounds.width - 250, 130);

            g.drawString(String.format("%.4f", GlobalData.dro[0]), Frame_Bounds.width - 220, 50);
            g.drawString(String.format("%.4f", GlobalData.dro[1]), Frame_Bounds.width - 220, 90);
            g.drawString(String.format("%.4f", GlobalData.dro[2]), Frame_Bounds.width - 220, 130);

        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new XmotionGen3().setVisible(true);
            }
        });
    }
}