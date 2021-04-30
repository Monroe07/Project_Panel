package Gauges.COM_1;

import java.awt.*;
import java.io.IOException;
import java.lang.*;
import java.awt.Font;


public class NumericDisplayRadio
{
    double Active;
    double Standby;
    Font fontNum = new Font("Arial", Font.PLAIN, 44);
    Font fontLab = new Font("Arial", Font.PLAIN, 14);
    Color numColor = Color.RED;
    Color boxColor = Color.WHITE;
    Color labelColor = Color.WHITE;




    String ACTIVE = "Loading...";

    String STANDBY = "Loading...";
    int refX = 95;
    int refY = 40;

    NumericDisplayRadio() throws IOException
    {

    }
    void setActive(double A) throws ArrayIndexOutOfBoundsException
    {
        Active = (int)(A /1000);



            if (Active > 0) {

                String s = Double.toString(Active);

                StringBuilder str = new StringBuilder(s);
                str.insert(3, '.');
                str.delete(7, 9);

                ACTIVE = str.toString();
                //System.out.println("Length: " + ACTIVE.length());

                //ACTIVE = Integer.toString(Active);
                //ACTIVE = new StringBuilder(ACTIVE).insert(3, ".").toString();
            }


    }
    void setStandby(double S)
    {
        Standby = (int)(S / 1000);

        if (Standby > 0) {

            String s = Double.toString(Standby);

            StringBuilder str = new StringBuilder(s);
            str.insert(3, '.');
            str.delete(7, 9);

            STANDBY = str.toString();
            //System.out.println("Length: " + ACTIVE.length());

            //ACTIVE = Integer.toString(Active);
            //ACTIVE = new StringBuilder(ACTIVE).insert(3, ".").toString();
        }


    }

    void draw(Graphics g)
    {


        System.out.print(ACTIVE);


        System.out.println();

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC) ;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY) ;
        //g2d.setColor(Color.white);
        //g2d.drawRect(0,0,400,200);




        // Draw Labels
        g2d.setFont(fontLab);
        g2d.setColor(labelColor);
        g2d.drawString("ACTIVE", refX, refY + 0);

        g2d.drawString("STANDBY", refX, refY + 75);

        // Draw Boxes
        g2d.setColor(boxColor);
        // Active Box
        g2d.drawRoundRect(refX - 10,refY+3,205,44,10,10);
        // Standby Box
        g2d.drawRoundRect(refX - 10,refY+78,205,44,10,10);

        // Draw Active Freq.
        g2d.setFont(fontNum);
        g2d.setColor(numColor);
        g2d.drawString(ACTIVE,refX,refY +41);

        // Draw Standby Freq.
        g2d.setFont(fontNum);
        g2d.setColor(numColor);
        g2d.drawString(STANDBY,refX,refY + 116);


    }
}
