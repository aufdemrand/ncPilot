package com.nc.pilot.lib;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by travis on 2/4/2019.
 */
class WidgetEntity {
    public static String type;
    public static String anchor; //Right or Left
    public static String text;
    public static boolean engaged;
    public static int width;
    public static int height;
    public static int posx;
    public static int posy;
}
public class UIWidgets {
    private static ArrayList<WidgetEntity> WidgetStack = new ArrayList();
    private static Graphics2D g;
    public UIWidgets()
    {

    }
    public static void DrawButton(String text, boolean engaged, int width, int height, int posx, int posy) {
        int button_font_size = 20;
        if (engaged == true)
        {
            g.setColor(Color.green);
        }
        else
        {
            g.setColor(Color.red);
        }
        g.setFont(new Font("Arial", Font.PLAIN, button_font_size));
        int text_length = (text.length()-2) * button_font_size;
        int text_posx = posx + (width / 2) - (text_length/2);
        int text_posy = posy + (height / 2) + (button_font_size/2);
        g.drawString(text, text_posx, text_posy);
        g.drawRect(posx, posy, width, height);
    }
    public static void AddButton(String text, String anchor, int width, int height, int posx, int posy){
        WidgetEntity w = new WidgetEntity();
        w.type = "button";
        w.anchor = anchor;
        w.text = text;
        w.engaged = false;
        w.width = width;
        w.height = height;
        w.posx = posx;
        w.posy = posy;
        WidgetStack.add(w);
    }
    public static void RenderStack(Graphics2D graphics, Rectangle Frame_Bounds){
        g = graphics;
        for (int x = 0; x < WidgetStack.size(); x++)
        {
            if (WidgetStack.get(x).type.equals("button")){
                if (WidgetStack.get(x).anchor.equals("top-right")){
                    DrawButton(WidgetStack.get(x).text, WidgetStack.get(x).engaged, WidgetStack.get(x).width, WidgetStack.get(x).height, Frame_Bounds.width - WidgetStack.get(x).posx - WidgetStack.get(x).width, WidgetStack.get(x).posy);
                }
                else if (WidgetStack.get(x).anchor.equals("bottom-right")){
                    DrawButton(WidgetStack.get(x).text, WidgetStack.get(x).engaged, WidgetStack.get(x).width, WidgetStack.get(x).height, Frame_Bounds.width - WidgetStack.get(x).posx - WidgetStack.get(x).width, Frame_Bounds.height - WidgetStack.get(x).posy - WidgetStack.get(x).height);
                }
                else {
                    DrawButton(WidgetStack.get(x).text, WidgetStack.get(x).engaged, WidgetStack.get(x).width, WidgetStack.get(x).height, WidgetStack.get(x).posx, WidgetStack.get(x).posy);
                }
            }
        }

    }
}
