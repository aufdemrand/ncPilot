package com.nc.pilot.lib;

import java.awt.*;

/**
 * Created by travis on 2/4/2019.
 */
public class UIWidgets {
    private static Graphics2D g;
    public UIWidgets(Graphics2D graphics)
    {
        g = graphics;
    }
    public static void DrawButton(String text, int width, int height, int posx, int posy) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString(text, posx, posy);
    }
}
