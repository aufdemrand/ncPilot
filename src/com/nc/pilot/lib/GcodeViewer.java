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
    public float getAngle(float[] start_point, float[] end_point) {
        float angle = (float) Math.toDegrees(Math.atan2(start_point[1] - end_point[1], start_point[0] - end_point[0]));

        angle += 180;
        if(angle > 360){
            angle -= 360;
        }
        if(angle < 0){
            angle += 360;
        }

        return angle;
    }
    public float[] rotatePoint(float[] pivot, float[] rotated_point, float angle)
    {
        float s = (float)Math.sin(angle*Math.PI/180);
        float c = (float)Math.cos(angle*Math.PI/180);

        // translate point back to origin:
        rotated_point[0] -= pivot[0];
        rotated_point[1] -= pivot[1];

        // rotate point
        float xnew = (rotated_point[0] * c - rotated_point[1] * s);
        float ynew = (rotated_point[0] * s + rotated_point[1] * c);

        // translate point back:
        rotated_point[0] = xnew + pivot[0];
        rotated_point[1] = ynew + pivot[1];
        return new float[] {rotated_point[0], rotated_point[1]};
    }
    public float[] getPolarLineEndpoint(float[] start_point, float length, float angle)
    {
        float[] end_point = new float[] {start_point[0] + length, start_point[1]};
        return rotatePoint(start_point, end_point, angle);
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
    public void RenderArc(float[] start, float[] end, float[] center, float radius, String direction)
    {
        float start_angle = getAngle(center, start);
        float end_angle = getAngle(center, end);
        if (start_angle > end_angle && start_angle == 360)
        {
            start_angle = 0;
        }
        if (direction == "CCW")
        {
            float [] last_point = start;
            for (float x = start_angle; x < end_angle; x+= 600f / GlobalData.ViewerZoom)
            {
                float [] new_point = getPolarLineEndpoint(center, radius, x);
                RenderLine(last_point, new_point);
                last_point = new_point;
            }
            RenderLine(last_point, getPolarLineEndpoint(center, radius, end_angle));
        }
        else //CW
        {
            //System.out.println("CW Arc-> start_angle: " + start_angle + " end_angle: " + end_angle);
            float [] last_point = start;
            for (float x = start_angle; x > end_angle; x-= 600f / GlobalData.ViewerZoom)
            {
                float [] new_point = getPolarLineEndpoint(center, radius, x);
                RenderLine(last_point, new_point);
                last_point = new_point;
            }
            RenderLine(last_point, getPolarLineEndpoint(center, radius, end_angle));
        }
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
                RenderArc(entity.start, entity.end, entity.center, entity.radius, "CW");
            }
            if (entity.type == 3) //We are a counter-clockwise arc
            {
                g2d.setColor(Color.white);
                //RenderLine(entity.center, entity.start);
                //RenderLine(entity.center, entity.end);
                RenderArc(entity.start, entity.end, entity.center, entity.radius, "CCW");
                //g2d.setColor(Color.white);
            }
        }

        /*g2d.setColor(Color.green); // 0 Degree polar
        float[] test = getPolarLineEndpoint(new float[] {10, 10}, 10, 0);
        RenderLine(new float[] {10, 10}, test);

        g2d.setColor(Color.red); // 90 Degree polar
        test = getPolarLineEndpoint(new float[] {10, 10}, 10, 90);
        RenderLine(new float[] {10, 10}, test);

        g2d.setColor(Color.blue); // 180 Degree polar
        test = getPolarLineEndpoint(new float[] {10, 10}, 10, 180);
        RenderLine(new float[] {10, 10}, test);

        g2d.setColor(Color.orange); // 270 Degree polar
        test = getPolarLineEndpoint(new float[] {10, 10}, 10, 270);
        RenderLine(new float[] {10, 10}, test);*/
    }
}
