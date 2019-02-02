package com.nc.pilot.lib;

import java.awt.geom.Point2D;

/**
 * Created by admin on 2/1/19.
 */
public class ViewerEntity {
    public int type; //G0, G1, G2, G3
    public int[] start;
    public int[] end;
    public int[] center;
    public float radius;


    // constructor
    public ViewerEntity() {

    }

    // setter
    public void setArc(int[] start, int[] end, float radius, String direction) {
        if (direction == "CW")
        {
            this.type = 2;
            this.start = start;
            this.end = end;
            this.radius = radius;
        }
        if (direction == "CCW")
        {
            this.type = 3;
            this.start = start;
            this.end = end;
            this.radius = radius;
        }
    }
    public void setLine(int[] start, int[] end) {
        this.type = 1;
        this.start = start;
        this.end = end;
    }
    public void setRapid(int[] start, int[] end) {
        this.type = 0;
        this.start = start;
        this.end = end;
    }
}
