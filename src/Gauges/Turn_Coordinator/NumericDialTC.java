package Gauges.Turn_Coordinator;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class NumericDialTC {
    double startingAngle = 0; // start angle of the zero
    double spacingAngle = 0; //angle of the space before zero

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
    int cx, cy;

    Font dispFont;
    Rectangle2D fontRectangle;
    FontMetrics fontMetrics;
    Color numberColor;
    Color dialColor;
    //Color chromeYellow = new Color (255, 167, 0);
    Color gauge = new Color(221, 215, 191);
    Color gaugeBackground = new Color(219,186,133);


    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param fontsize
     * @param _dialDiameter
     */
    NumericDialTC(int fontsize, int _dialDiameter) {

        dispFont = new Font("Calibri", Font.PLAIN, fontsize);
        dialDiameter = _dialDiameter;
        cx = 115;
        cy = 115;
        //System.out.println(dialDiameter);
        //numberColor = Color.white;	// color for numbers on dial
        //dialColor = Color.white;		// Dial Tick Marks

        numberColor = Color.black;
        dialColor = Color.black;

        maxValue = 70;
        minValue = 0;
        bigLineStep = 10;        // Sets Increment between big ticks in dial

        incontinousDial = false;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    void draw(Graphics g)
    {

        int yawRateDisplayOffset = 15;
        double [] angles = {0.3,-0.3};
        /**
        * angles[0] = Right Mark - 15 degrees
         * angles[1] = Right Mark
        * angles[2] = Left Mark
        * angles[3] = Left Mark + 15 degrees
         */
        Graphics2D g2d = (Graphics2D) g;
        g.setFont(dispFont);
        BasicStroke stroke = new BasicStroke(40,BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL,10);
        // Sets Border of gauge Color
        g.setColor(Color.DARK_GRAY);
        g.fillOval((cx - dialDiameter / 2) - 3, (cy - dialDiameter / 2) - 3, dialDiameter + 6, dialDiameter + 6);

        g.setColor(gaugeBackground);
        // SET BACKGROUND Images FOR ROUND GAUGES
        g.fillOval((cx - dialDiameter / 2) + 3, (cy - dialDiameter / 2) + 3, dialDiameter - 6, dialDiameter - 6);
        //g.drawOval((cx - dialDiameter/2), (cy - dialDiameter/2), dialDiameter, dialDiameter);
        g.setColor(Color.BLACK);
        int offset = -15;
        for (int i = 0; i < angles.length; i++)
        {
            g2d.rotate(angles[i], cx, cy);
            //g.fillRect(cx-2, cy-(dialDiameter/2)+8, 4,24);
            Polygon ends = new Polygon();
            ends.addPoint(cx,cy-(dialDiameter/2)+20+offset);           // top
            ends.addPoint(cx-10, cy-(dialDiameter/2)+30+offset);    // left top
            ends.addPoint(cx-10, cy-(dialDiameter/2)+40+offset);    // left bottom
            //ends.addPoint(cx, cy-(dialDiameter/2)+40);          // right bottom
            ends.addPoint(cx+10, cy-(dialDiameter/2)+40+offset);
            ends.addPoint(cx+10,cy-(dialDiameter/2)+30+offset);


            //ends.addPoint(cx, cy-(dialDiameter/2)+20);


            //ends.addPoint(cx, cy-(dialDiameter/2)+40);


            g.fillPolygon(ends);
            g2d.rotate(-angles[i], cx,cy);
        }
        // Draw Center marker
        Polygon center = new Polygon();

        center.addPoint(cx,cy-113-offset);  //tip
        center.addPoint(cx+13, cy-123-offset);
        center.addPoint(cx+13, cy-142-offset);
        center.addPoint(cx-13,cy-142-offset);
        center.addPoint(cx-13,cy-123-offset);

        g.fillPolygon(center);

        // Draw Yaw Rate Background
        g.setColor(Color.DARK_GRAY);
        g2d.setStroke(stroke);

        //g.drawArc(-600, -1325, 1600, 1600, 265, 10);
        g.drawArc(cx-800, cy-1525-yawRateDisplayOffset, 1600, 1600, 265, 10);



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
        fontMetrics = g2d.getFontMetrics();
        fontRectangle = fontMetrics.getStringBounds(testtext, g2d);
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
        int x = (cx - (int) fontRectangle.getWidth() / 2) + _x;
        int y = (cy - (int) fontRectangle.getHeight() / 2) + _y + fontMetrics.getAscent();
        g.drawString(text, x, y);
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param c
     */
    public void setNumberColor(Color c) {
        numberColor = c;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param c
     */
    public void setBorderColor(Color c) {
        dialColor = c;
    }

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
     * @param f
     */
    public void setFont(Font f) {
        dispFont = f;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param diam
     */
    public void setDiameter(int diam) {
        dialDiameter = diam;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param startangle
     * @param spaceangle
     */
    public void setAngles(double startangle, double spaceangle) {
        startingAngle = startangle;
        spacingAngle = spaceangle;
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


}

