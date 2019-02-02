package com.nc.pilot.lib;

/**
 * Created by admin on 2/1/19.
 */
public class ViewerEntity {
    public int type; //G0, G1, G2, G3
    public float[] start;
    public float[] end;
    public float[] center;
    public float radius;


    // constructor
    public ViewerEntity() {

    }

    // setter
    public void setArc(float[] start, float[] end, float radius, String direction) {
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
    public void setLine(float[] start, float[] end) {
        this.type = 1;
        this.start = start;
        this.end = end;
    }
    public void setRapid(float[] start, float[] end) {
        this.type = 0;
        this.start = start;
        this.end = end;
    }
}
