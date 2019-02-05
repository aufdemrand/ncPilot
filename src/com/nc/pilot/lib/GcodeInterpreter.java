package com.nc.pilot.lib;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by travi on 2/5/2019.
 */
class GcodeMove{
    private int Gword;
    private int Xword;
    private int Yword;
    private int Zword;
    private int Fword;
}
public class GcodeInterpreter {
    private ArrayList<WidgetEntity> Moves = new ArrayList();

    //Active words
    private int Gword;
    private int Xword;
    private int Yword;
    private int Zword;
    private int Fword;

    public GcodeInterpreter(String file)
    {
        try {
            String buffer = GlobalData.readFile(file);
            String[] lines = buffer.split("\n");
            for (int x = 0; x < lines.length; x++)
            {
                parseLine(lines[x]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void parseLine(String line)
    {
        System.out.println("Line: " + line);
        if (line.contains("G"))
        {

        }
    }
}
