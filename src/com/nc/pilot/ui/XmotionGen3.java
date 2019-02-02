package com.nc.pilot.ui;

import com.nc.pilot.lib.ViewerEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * This program demonstrates how to draw lines using Graphics2D object.
 * @author www.codejava.net
 *
 */
public class XmotionGen3 extends JFrame {

    ArrayList<ViewerEntity> ViewerEntityStack = new ArrayList();
    public int move_lines = 0;
    public XmotionGen3() {
        super("ncPilot Xmotion Gen3");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GcodeViewerPanel panel = new GcodeViewerPanel();
        add(panel);

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent ke) {
                        switch (ke.getID()) {
                            case KeyEvent.KEY_PRESSED:
                                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                                    move_lines++;
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                                    move_lines--;
                                }
                                System.out.println("Move_Lines: " + move_lines);
                                repaint();
                                break;

                            case KeyEvent.KEY_RELEASED:
                                if (ke.getKeyCode() == KeyEvent.VK_UP) {

                                }
                                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {

                                }
                                if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                                    //Load up a bunch of lines onto viewer entity stack and repaint
                                    ViewerEntity entity = new ViewerEntity();
                                    entity.setLine(new int[]{50, 50}, new int[]{500, 50});
                                    ViewerEntityStack.add(entity);

                                    entity = new ViewerEntity();
                                    entity.setLine(new int[]{500, 50}, new int[]{500, 500});
                                    ViewerEntityStack.add(entity);

                                    entity = new ViewerEntity();
                                    entity.setLine(new int[]{500, 500}, new int[]{50, 500});
                                    ViewerEntityStack.add(entity);

                                    entity = new ViewerEntity();
                                    entity.setLine(new int[]{50, 500}, new int[]{50, 50});
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
            g.setColor(Color.black);
            //g.fillRect(10,10,100,100);
            for(int i=0;i< ViewerEntityStack.size();i++)
            {
                ViewerEntity entity = ViewerEntityStack.get(i);
                if (entity.type == 1) //We are a line move
                {
                    g.drawLine(entity.start[0], entity.start[1], entity.end[0], entity.end[1]);
                    System.out.println("(" + i + ") Drawing Line Move: start: (" + entity.start[0] + ", " + entity.start[1] + ") end (" + entity.end[0] + ", " + entity.end[1] + ")");
                }
            }
            //
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