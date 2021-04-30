package Gauges.RPM;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class NumericDialRPM_Picture {
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

    //Font dispFont;
    //Rectangle2D fontRectangle;
    //FontMetrics fontMetrics;
    //Color numberColor;
    //Color dialColor;
    //Color chromeYellow = new Color (255, 167, 0);
    //Color gauge = new Color(221, 215, 191);
    Color gaugeBackground = new Color(219,186,133);

    final BufferedImage RPM_mechanics;
    final BufferedImage fi_needle;
    final BufferedImage fi_circle;

    double RPM;

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param
     * @param
     */
    NumericDialRPM_Picture() throws IOException {

        RPM_mechanics = ImageIO.read(getClass().getResourceAsStream("/Pictures/RPM_mechanics.png"));
        fi_needle = ImageIO.read(getClass().getResourceAsStream("/Pictures/fi_needle.png"));
        fi_circle = ImageIO.read(getClass().getResourceAsStream("/Pictures/fi_circle.png"));

        maxValue = 30;
        minValue = 0;
        //bigLineStep = 10;        // Sets Increment between big ticks in dial

        //incontinousDial = false;
    }



    void setRPM(double R)
    {
        RPM = R;
    }
    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    void draw(Graphics g) {
        RPM = (RPM /100)*.95;
        if (RPM < minValue)
            RPM = minValue;
        if (RPM > maxValue)
            RPM = maxValue;

        double theta = (Math.toRadians(225)-(RPM/30)*Math.toRadians(270)) ;
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC) ;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY) ;


        g2d.drawImage(RPM_mechanics, 0,0,null);
        g2d.rotate(theta,200,200);
        g2d.drawImage(fi_needle, 0,0,null);
        g2d.rotate(-theta,200,200);
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

