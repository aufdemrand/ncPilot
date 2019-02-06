package com.nc.pilot.lib;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by travi on 2/5/2019.
 */
public class GcodeInterpreter {
    public class GcodeMove{
        public float Gword;
        public float Xword;
        public float Yword;
        public float Zword;
        public float Fword;
        public float Iword;
        public float Jword;
    }
    private ArrayList<GcodeMove> Moves = new ArrayList();

    //Active words
    private float Gword;
    private float Xword;
    private float Yword;
    private float Zword;
    private float Fword;
    private float Iword;
    private float Jword;

    public GcodeInterpreter(String file)
    {
        try {
            String buffer = GlobalData.readFile(file);
            String[] lines = buffer.split("\n");
            for (int x = 0; x < lines.length; x++)
            {
                parseLine(lines[x].toLowerCase());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<GcodeMove> GetMoves()
    {
        return Moves;
    }
    public void checkG(String line)
    {
        boolean capture = false;
        String word_builder = "";
        for (int x = 0; x < line.length(); x++)
        {
            if (capture == true)
            {
                if (Character.isDigit(line.charAt(x)) || line.charAt(x) == '.')
                {
                    word_builder = word_builder + line.charAt(x);
                }
                if ((Character.isAlphabetic(line.charAt(x)) && line.charAt(x) != ' ') || x == line.length() - 1)
                {
                    if (word_builder != "")
                    {
                        //System.out.println("word_builder: " + word_builder);
                        float word = new Float(word_builder);
                        if (word == 0 || word == 1 || word == 2 || word == 3)
                        {
                            Gword = word;
                        }
                    }
                    capture = false;
                    word_builder = "";
                }
            }
            if (line.charAt(x) == 'g')
            {
                capture = true;
            }
        }
    }
    public void checkX(String line)
    {
        boolean capture = false;
        String word_builder = "";
        for (int x = 0; x < line.length(); x++)
        {
            if (capture == true)
            {
                if (Character.isDigit(line.charAt(x)) || line.charAt(x) == '.')
                {
                    word_builder = word_builder + line.charAt(x);
                }
                if ((Character.isAlphabetic(line.charAt(x)) && line.charAt(x) != ' ') || x == line.length() - 1)
                {
                    if (word_builder != "")
                    {
                        float word = new Float(word_builder);
                        Xword = word;
                    }
                    capture = false;
                    word_builder = "";
                }
            }
            if (line.charAt(x) == 'x')
            {
                capture = true;
            }
        }
    }
    public void checkY(String line)
    {
        boolean capture = false;
        String word_builder = "";
        for (int x = 0; x < line.length(); x++)
        {
            if (capture == true)
            {
                if (Character.isDigit(line.charAt(x)) || line.charAt(x) == '.')
                {
                    word_builder = word_builder + line.charAt(x);
                }
                if ((Character.isAlphabetic(line.charAt(x)) && line.charAt(x) != ' ') || x == line.length() - 1)
                {
                    if (word_builder != "")
                    {
                        float word = new Float(word_builder);
                        Yword = word;
                    }
                    capture = false;
                    word_builder = "";
                }
            }
            if (line.charAt(x) == 'y')
            {
                capture = true;
            }
        }
    }
    public void checkZ(String line)
    {
        boolean capture = false;
        String word_builder = "";
        for (int x = 0; x < line.length(); x++)
        {
            if (capture == true)
            {
                if (Character.isDigit(line.charAt(x)) || line.charAt(x) == '.')
                {
                    word_builder = word_builder + line.charAt(x);
                }
                if ((Character.isAlphabetic(line.charAt(x)) && line.charAt(x) != ' ') || x == line.length() - 1)
                {
                    if (word_builder != "")
                    {
                        float word = new Float(word_builder);
                        Zword = word;
                    }
                    capture = false;
                    word_builder = "";
                }
            }
            if (line.charAt(x) == 'z')
            {
                capture = true;
            }
        }
    }
    public void checkF(String line)
    {
        boolean capture = false;
        String word_builder = "";
        for (int x = 0; x < line.length(); x++)
        {
            if (capture == true)
            {
                if (Character.isDigit(line.charAt(x)) || line.charAt(x) == '.')
                {
                    word_builder = word_builder + line.charAt(x);
                }
                if ((Character.isAlphabetic(line.charAt(x)) && line.charAt(x) != ' ') || x == line.length() - 1)
                {
                    if (word_builder != "")
                    {
                        float word = new Float(word_builder);
                        Fword = word;
                    }
                    capture = false;
                    word_builder = "";
                }
            }
            if (line.charAt(x) == 'f')
            {
                capture = true;
            }
        }
    }
    public void checkI(String line)
    {
        boolean capture = false;
        String word_builder = "";
        for (int x = 0; x < line.length(); x++)
        {
            if (capture == true)
            {
                if (Character.isDigit(line.charAt(x)) || line.charAt(x) == '.')
                {
                    word_builder = word_builder + line.charAt(x);
                }
                if ((Character.isAlphabetic(line.charAt(x)) && line.charAt(x) != ' ') || x == line.length() - 1)
                {
                    if (word_builder != "")
                    {
                        float word = new Float(word_builder);
                        Iword = word;
                    }
                    capture = false;
                    word_builder = "";
                }
            }
            if (line.charAt(x) == 'i')
            {
                capture = true;
            }
        }
    }
    public void checkJ(String line)
    {
        boolean capture = false;
        String word_builder = "";
        for (int x = 0; x < line.length(); x++)
        {
            if (capture == true)
            {
                if (Character.isDigit(line.charAt(x)) || line.charAt(x) == '.')
                {
                    word_builder = word_builder + line.charAt(x);
                }
                if ((Character.isAlphabetic(line.charAt(x)) && line.charAt(x) != ' ') || x == line.length() - 1)
                {
                    if (word_builder != "")
                    {
                        float word = new Float(word_builder);
                        Jword = word;
                    }
                    capture = false;
                    word_builder = "";
                }
            }
            if (line.charAt(x) == 'j')
            {
                capture = true;
            }
        }
    }
    public void parseLine(String line)
    {
        //System.out.println("Line: " + line);
        checkG(line);
        checkX(line);
        checkY(line);
        checkZ(line);
        checkI(line);
        checkJ(line);
        checkF(line);

        GcodeMove m = new GcodeMove();
        m.Gword = Gword;
        m.Xword = Xword;
        m.Yword = Yword;
        m.Zword = Zword;
        m.Iword = Iword;
        m.Jword = Jword;
        m.Fword = Fword;

        if (Moves.size() > 0)
        {
            if (Moves.get(Moves.size() - 1).Gword != Gword || Moves.get(Moves.size() - 1).Xword != Xword || Moves.get(Moves.size() - 1).Yword != Yword)
            {
                /*if (Gword == 2 || Gword == 3)
                {
                    System.out.println("G Entity-> G" + Gword + " X" + Xword + " Y" + Yword + " Z" + Zword + " I" + Iword + " J" + Jword + " F" + Fword);
                }
                else
                {
                    System.out.println("G Entity-> G" + Gword + " X" + Xword + " Y" + Yword + " Z" + Zword + " F" + Fword);
                }*/
                Moves.add(m);
            }
        }
        else
        {
            Moves.add(m);
        }
    }
}
