package com.nc.pilot.lib;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

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

    public Runnable action;

    //Meta data
    public int real_posx;
    public int real_posy;
    public boolean engaged;
}
public class UIWidgets {
    private ArrayList<WidgetEntity> WidgetStack = new ArrayList();
    private Graphics2D g;
    private Rectangle Frame_Bounds;
    public UIWidgets()
    {

    }
    public void DrawDRO()
    {
        g.setFont(new Font("Arial", Font.BOLD, 45));
        if (GlobalData.IsHomed == false)
        {
            g.setColor(Color.red);
        }
        else
        {
            g.setColor(Color.green);
        }
        int DRO_X_Offset = -50;
        g.drawString("X:", Frame_Bounds.width - 350 - DRO_X_Offset, 70);
        g.drawString("Y:", Frame_Bounds.width - 350 - DRO_X_Offset, 140);
        g.drawString("Z:", Frame_Bounds.width - 350 - DRO_X_Offset, 210);

        g.drawString(String.format("%.4f", GlobalData.dro[0]), Frame_Bounds.width - 220 - DRO_X_Offset, 70);
        g.drawString(String.format("%.4f", GlobalData.dro[1]), Frame_Bounds.width - 220 - DRO_X_Offset, 140);
        g.drawString(String.format("%.4f", GlobalData.dro[2]), Frame_Bounds.width - 220 - DRO_X_Offset, 210);
        g.drawRect(Frame_Bounds.width - 360, 10, 350, 240);
    }
    public void DrawButton(String text, boolean engaged, int width, int height, int posx, int posy) {
        //System.out.println("Drawing " + text);
        int button_font_size = 15;
        if (engaged == true)
        {
            g.setColor(Color.green);
        }
        else
        {
            g.setColor(Color.red);
        }
        g.setFont(new Font("Arial", Font.PLAIN, button_font_size));
        int text_length = (text.length() - 3) * button_font_size;
        int text_posx = posx + (width / 2) - (text_length/2);
        int text_posy = posy + (height / 2) + (button_font_size/2);
        g.drawString(text, text_posx, text_posy);
        g.drawRect(posx, posy, width, height);
    }
    public void AddMomentaryButton(String text, String anchor, int width, int height, int posx, int posy, Runnable action){
        //System.out.println("Adding: " + text);
        WidgetEntity w = new WidgetEntity();
        w.type = "momentary_button";
        w.anchor = anchor;
        w.text = text;
        w.engaged = false;
        w.width = width;
        w.height = height;
        w.posx = posx;
        w.posy = posy;
        w.action = action;
        WidgetStack.add(w);
    }
    public void RenderStack(Graphics2D graphics, Rectangle f){
        g = graphics;
        Frame_Bounds = f;
        //System.out.println("WidgetStack has " + WidgetStack.size() + " Entities!");
        for (int x = 0; x < WidgetStack.size(); x++)
        {
            //System.out.println("Rendering " + x + " Entity which is a " + WidgetStack.get(x).type + " with a text of " + WidgetStack.get(x).text);
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
        DrawDRO();

    }
    public void ClickPressStack(int mousex, int mousey){
        for (int x = 0; x < WidgetStack.size(); x++)
        {
            if (WidgetStack.get(x).type.equals("momentary_button")){
                if ((mousex > WidgetStack.get(x).real_posx && mousex < WidgetStack.get(x).real_posx + WidgetStack.get(x).width) && (mousey > WidgetStack.get(x).real_posy && mousey < WidgetStack.get(x).real_posy + WidgetStack.get(x).height))
                {
                    //System.out.println("Clicked on: " + WidgetStack.get(x).text);
                    WidgetStack.get(x).engaged = true;
                }
            }
        }
    }
    public void ClickReleaseStack(int mousex, int mousey){
        for (int x = 0; x < WidgetStack.size(); x++)
        {
            if (WidgetStack.get(x).type.equals("momentary_button")){
                if ((mousex > WidgetStack.get(x).real_posx && mousex < WidgetStack.get(x).real_posx + WidgetStack.get(x).width) && (mousey > WidgetStack.get(x).real_posy && mousey < WidgetStack.get(x).real_posy + WidgetStack.get(x).height))
                {
                    //System.out.println("Clicked on: " + WidgetStack.get(x).text);
                    WidgetStack.get(x).engaged = false;
                    WidgetStack.get(x).action.run();
                }
            }
        }
    }
}
