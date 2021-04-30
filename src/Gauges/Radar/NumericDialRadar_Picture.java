package Gauges.Radar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static javax.print.attribute.standard.Chromaticity.COLOR;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class NumericDialRadar_Picture {
     //angle of the space before zero

    //for Incontinous dials
    boolean incontinousDial;

    double maxValue;
    double minValue;
    double bigLineStep;

    int digitNumber = 1;
    int cutBeginningDigits = 0;
    boolean leadingZeros = true;
    boolean rotatingDisplay = false;
    int subScaleLines = 4;
    int largeLinesSize = 2;
    int dialDiameter;
    int cx;
    int cy;

    //Font dispFont;
    //Rectangle2D fontRectangle;
    //FontMetrics fontMetrics;
    //Color numberColor;
    //Color dialColor;
    //Color chromeYellow = new Color (255, 167, 0);
    //Color gauge = new Color(221, 215, 191);

    Color gaugeBackground = new Color(219,186,133);
    Color myShapeColor = new Color(255,0,0);
    //Color AirplaneColor = new Color(255, 255, 0);
    Color AirplaneTextColor = new Color(255,255,255);

    //Color AirportColor = new Color(0, 197, 0);
    Color AirportTextColor = new Color(0, 197, 0);



    final BufferedImage PlanePointer;
    final BufferedImage Object;
    final BufferedImage Airport;


    final BufferedImage fi_needle;
    final BufferedImage fi_circle;
    DistanceDirectionRhumb calculator;

    ArrayList<Object[]> Planes;
    ArrayList<Object[]> Airports;


    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param
     * @param
     */
    NumericDialRadar_Picture(int _viewDist, int _viewSize) throws IOException {
        System.out.println("NumericDialRadar_Picture");
        viewDistance = _viewDist;
        viewSize = _viewSize;
        PlanePointer = ImageIO.read(getClass().getResourceAsStream("/Pictures/Plane_Pointer.png"));
        Object = ImageIO.read(getClass().getResourceAsStream("/Pictures/Red_Diamond_Blip.png"));
        Airport = ImageIO.read(getClass().getResourceAsStream("/Pictures/Green_Diamond_Blip.png"));

        fi_needle = ImageIO.read(getClass().getResourceAsStream("/Pictures/fi_needle.png"));
        fi_circle = ImageIO.read(getClass().getResourceAsStream("/Pictures/fi_circle.png"));
        calculator = new DistanceDirectionRhumb();

        maxValue = 160;
        minValue = 0;
        //bigLineStep = 10;        // Sets Increment between big ticks in dial

        //incontinousDial = false;
        System.out.println("NumericDialRadar_Picture()");
    }


    void setPlanes(ArrayList<Object[]> _Planes)
    {

        Planes = _Planes;
    }

    void setAirports(ArrayList<Object[]> _Airports)
    {
        Airports =_Airports;

    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _viewSize
     */
    public void setViewSize(int _viewSize) {
        viewSize = _viewSize;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _viewDistance
     */
    public void setViewDist(int _viewDistance) {
        viewDistance = _viewDistance;

    }

    int viewSize; // start angle of the zero

    int viewDistance;

    int xpos;
    int ypos;

    int myShapeOffsetX = 8;
    int myShapeOffsetY = 10;

    int textXPos;
    int textLine1YPos;
    int textLine2YPos;

    int planeShapeSize = 8;
    int planeShapeOffset = planeShapeSize/2;

    int airportShapeSize = 12;
    int airportShapeOffset = airportShapeSize/2;

    Font infoFont = new Font("TimesRoman", Font.PLAIN, 11);






    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC) ;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY) ;
        //System.out.println("FINAL: " + viewSize + "\t" + viewDistance);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(cx, 0, cx, cy);
        g2d.drawLine(cx-1, 0, cx-1, cy);


        viewSize = cy;
        double ratio = viewSize / viewDistance;

        int d1 = (int)Math.round(5 * ratio) * 2;
        int d2 = (int)Math.round(10 * ratio) * 2;
        int d3 = (int)Math.round(15 * ratio) * 2;

        int r1 = d1/2;
        int r2 = d2/2;
        int r3 = d3/2;

        /**
         * DISTANCE RINGS
         */
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawOval(cx - r1,cy - r1, d1,d1 );
        g2d.drawOval(cx - r2, cy - r2, d2, d2);
        g2d.drawOval(cx - r3, cy - r3, d3, d3);


        g2d.setColor(myShapeColor);
        //g2d.fillOval(cx-myShapeOffset, cy-myShapeOffset, myShapeSize, myShapeSize);



        g2d.setFont(infoFont);

        /**
         * AIRPLANES
         */
        for(int i = 0 ; i < Planes.size() ; i++)
        {
            xpos = (int)(Planes.get(i)[2])-planeShapeOffset+cx;
            ypos = (int)(Planes.get(i)[3])-planeShapeOffset+cy;
            textXPos = xpos + 18;
            textLine1YPos = ypos + 20;
            textLine2YPos = textLine1YPos + 12;
            //g2d.setColor(AirplaneColor);
            //g2d.drawOval(xpos, ypos, planeShapeSize, planeShapeSize);
            g2d.drawImage(Object, xpos, ypos, null);
            g2d.setColor(AirplaneTextColor);
            g2d.drawString((String)Planes.get(i)[0],textXPos, textLine1YPos);
            g2d.drawString(Planes.get(i)[1].toString() + " ft.",textXPos, textLine2YPos);
            //System.out.println("ATC ID: " + Planes.get(i)[0] +"\tAltitude: " + Planes.get(i)[1]) + "\t\tX: " + (String)Planes.get(i)[2] + "\tY: " + Planes.get(i)[3]);
        }

        /**
         * AIRPORTS
         */

        for(int j = 0 ; j < Airports.size() ; j++)
        {
            //System.out.print(j + ":"  );
            int xpos1 = (int)(Airports.get(j)[1]) - airportShapeOffset+cx;
            int ypos1 = (int)(Airports.get(j)[2]) - airportShapeOffset+cy;
            textXPos = xpos1 + 18;
            int textLine1YPos1 = ypos1 + 20;
            //textLine2YPos = textLine1YPos + 12;
            //g2d.setColor(AirportColor);
            //g2d.fillOval(xpos1, ypos1, airportShapeSize, airportShapeSize);
            g2d.drawImage(Airport, xpos1, ypos1, null);
            g2d.setColor(AirportTextColor);
            g2d.drawString((Airports.get(j)[0]).toString(),textXPos, textLine1YPos1);
            //System.out.println(Airports.get(j)[0]);
            //g2d.drawString(Planes.get(i)[1].toString() + " ft.",textXPos, textLine2YPos);

        }



        g2d.drawImage(PlanePointer, cx-myShapeOffsetX,cy-myShapeOffsetY,null);

        g2d.dispose();



        //g2d.drawImage(speed_mechanics, 0,0,null);
        //g2d.rotate(theta,200,200);
        //g2d.drawImage(fi_needle, 0,0,null);
        //g2d.rotate(-theta,200,200);
        //g2d.drawImage(fi_circle,0,0,null);




    }   // End of void draw(Graphics g)

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     * @param testtext
     */
    void calcTextRectangle(Graphics g, String testtext) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //fontMetrics = g2d.getFontMetrics();
        //fontRectangle = fontMetrics.getStringBounds(testtext, g2d);
    }

    //draws a centered string

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param text
     * @param _x
     * @param _y
     * @param g
     */
    void drawCenteredString(String text, int _x, int _y, Graphics g) {
        //int x = (cx - (int) fontRectangle.getWidth() / 2) + _x;
        //int y = (cy - (int) fontRectangle.getHeight() / 2) + _y + fontMetrics.getAscent();
        //g.drawString(text, x, y);
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param c
     */


    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param c
     */


    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _cx
     * @param _cy
     */
    public void reposition(int _cx, int _cy) {
        cx = _cx;
        cy = _cy;
    }





    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param min
     * @param max
     * @param step
     */
    public void setMinMaxValue(double min, double max, double step) {
        maxValue = max;
        minValue = min;
        bigLineStep = step;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param lines
     */
    public void setLines(int lines) {
        subScaleLines = lines;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _digitNumber
     * @param _cutBeginningDigits
     * @param _showZeros
     */
    public void setDigitNumbers(int _digitNumber, int _cutBeginningDigits, boolean _showZeros) {
        digitNumber = _digitNumber;
        cutBeginningDigits = _cutBeginningDigits;
        leadingZeros = _showZeros;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _value
     */
    public void setRotatingDisplay(boolean _value) {
        rotatingDisplay = _value;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param value
     */
    public void setLargeLineSize(int value) {
        largeLinesSize = value;
    }
    /**
     * Used as a helper to truncate values to 2 decimal places
     *
     * @param val
     * @return
     */
    static double truncate(double val) {
        double v = val * 100;
        v = (int) v;
        return (v / 100);
    }

}

