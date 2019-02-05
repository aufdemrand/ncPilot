package com.nc.pilot.lib;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by travis on 2/4/2019.
 */
class WidgetEntity {
    public String type;
    public String anchor; //Right or Left
    public String text;

    public int width;
    public int height;
    public int posx;
    public int posy;

    //Meta data
    public int real_posx;
    public int real_posy;
    public boolean engaged;
}
public class UIWidgets {
    private ArrayList<WidgetEntity> WidgetStack = new ArrayList();
    private Graphics2D g;
    public UIWidgets()
    {

    }
    public void DrawButton(String text, boolean engaged, int width, int height, int posx, int posy) {
        System.out.println("Drawing " + text);
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
    public void AddMomentaryButton(String text, String anchor, int width, int height, int posx, int posy){
        System.out.println("Adding: " + text);
        WidgetEntity w = new WidgetEntity();
        w.type = "momentary_button";
        w.anchor = anchor;
        w.text = text;
        w.engaged = false;
        w.width = width;
        w.height = height;
        w.posx = posx;
        w.posy = posy;
        WidgetStack.add(w);
    }
    public void RenderStack(Graphics2D graphics, Rectangle Frame_Bounds){
        g = graphics;
        //System.out.println("WidgetStack has " + WidgetStack.size() + " Entities!");
        for (int x = 0; x < WidgetStack.size(); x++)
        {
            System.out.println("Rendering " + x + " Entity which is a " + WidgetStack.get(x).type + " with a text of " + WidgetStack.get(x).text);
            if (WidgetStack.get(x).type.equals("momentary_button")){
                if (WidgetStack.get(x).anchor.equals("top-right")){
                    WidgetStack.get(x).real_posx = Frame_Bounds.width - WidgetStack.get(x).posx - WidgetStack.get(x).width;
                    WidgetStack.get(x).real_posy = WidgetStack.get(x).posy;
                }
                else if (WidgetStack.get(x).anchor.equals("bottom-right")){
                    WidgetStack.get(x).real_posx = Frame_Bounds.width - WidgetStack.get(x).posx - WidgetStack.get(x).width;
                    WidgetStack.get(x).real_posy = Frame_Bounds.height - WidgetStack.get(x).posy - WidgetStack.get(x).height;
                }
                else {
                    WidgetStack.get(x).real_posx = WidgetStack.get(x).posx;
                    WidgetStack.get(x).real_posy = WidgetStack.get(x).posy;
                }
                DrawButton(WidgetStack.get(x).text, WidgetStack.get(x).engaged, WidgetStack.get(x).width, WidgetStack.get(x).height, WidgetStack.get(x).real_posx, WidgetStack.get(x).real_posy);
            }
        }

    }
    public void ClickStack(int mousex, int mousey){

    }
}
