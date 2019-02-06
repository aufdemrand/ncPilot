package com.nc.pilot.lib;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by admin on 2/1/19.
 */
public class GcodeViewer {
    public class ViewerEntity{
        public int type; //G0, G1, G2, G3
        public float[] start;
        public float[] end;
        public float[] center;
        public float radius;
    }

    private Graphics2D g2d;
    private ArrayList<ViewerEntity> gcodeViewerStack = new ArrayList();


    // constructor
    public GcodeViewer() {

    }

    // setter
    public void addArc(float[] start, float[] end, float[] center, float radius, String direction) {
        ViewerEntity e = new ViewerEntity();
        if (direction == "CW")
        {
            e.type = 2;
            e.start = start;
            e.end = end;
            e.radius = radius;
        }
        if (direction == "CCW")
        {
            e.type = 3;
            e.start = start;
            e.end = end;
            e.radius = radius;
        }
        e.center = center;
        gcodeViewerStack.add(e);
    }
    public void addLine(float[] start, float[] end) {
        ViewerEntity e = new ViewerEntity();
        e.type = 1;
        e.start = start;
        e.end = end;
        gcodeViewerStack.add(e);
    }
    public void addRapid(float[] start, float[] end) {
        ViewerEntity e = new ViewerEntity();
        e.type = 0;
        e.start = start;
        e.end = end;
        gcodeViewerStack.add(e);
    }
    public void RenderLine(float[] start, float end[])
    {
        g2d.draw(new Line2D.Float(((start[0] + GlobalData.work_offset[0]) * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], (((start[1] + GlobalData.work_offset[1]) * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1], ((end[0] + GlobalData.work_offset[0]) * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], (((end[1] + GlobalData.work_offset[1]) * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1]));
    }
    public void RenderStack(Graphics2D graphics)
    {
        g2d = graphics;
         /* Begin machine boundry outline */
        g2d.setColor(Color.red);
        //0,0,X_Extent,0
        //X_Extent,0,X_Extent,Y_Extent
        //X_Extent,Y_Extent,0,Y_Extent
        //0,Y_Extent,0,0
        g2d.draw(new Line2D.Float((0 * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((0 * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1], (GlobalData.X_Extents * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((0 * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1]));
        g2d.draw(new Line2D.Float((GlobalData.X_Extents * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((0 * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1], (GlobalData.X_Extents * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((GlobalData.Y_Extents  * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1]));
        g2d.draw(new Line2D.Float((GlobalData.X_Extents * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((GlobalData.Y_Extents * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1], (0 * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((GlobalData.Y_Extents  * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1]));
        g2d.draw(new Line2D.Float((0 * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((GlobalData.Y_Extents * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1], (0 * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((0  * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1]));
        g2d.setColor(Color.white);
            /* End machine boundry outline */

        for(int i = 0; i< gcodeViewerStack.size(); i++)
        {
            ViewerEntity entity = gcodeViewerStack.get(i);
            if (entity.type == 1) //We are a line move
            {
                g2d.setColor(Color.white);
                RenderLine(entity.start, entity.end);
            }
            if (entity.type == 2) //We are a clockwise arc
            {
                g2d.setColor(Color.red);
                RenderLine(entity.center, entity.start);
                RenderLine(entity.center, entity.end);
            }
            if (entity.type == 3) //We are a clockwise arc
            {
                g2d.setColor(Color.blue);
                RenderLine(entity.center, entity.start);
                RenderLine(entity.center, entity.end);
            }
        }
    }
}
