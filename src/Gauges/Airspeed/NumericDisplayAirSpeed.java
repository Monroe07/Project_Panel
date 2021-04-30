package Gauges.Airspeed;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class NumericDisplayAirSpeed {
    double value;
    boolean displayMinus = false;
    boolean fillZeros = true;
    boolean drawBorder = true;
    int digitNumber = 5;
    int cx, cy;
    double offset;

    Font dispFont;
    Rectangle2D fontRectangle;
    FontMetrics fontMetrics;
    Color numberColor;
    Color borderColor;

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param fontsize
     */
    NumericDisplayAirSpeed(int fontsize) {
        cx = 0;
        cy = 0;

        dispFont = new Font("Helvertica", Font.BOLD, fontsize);

        numberColor = Color.black;        // ets Color for numbers shown in 7 segment displays of Gauges.Altimeter.Altimeter and Gauges.HSI.HSI
        borderColor = Color.black;        // Sets color for border around digital display of altitude and mbar of altimeter, Center display name of Gauges.Altimeter.Altimeter, and label for "mbar" on altimeter
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void draw(Graphics g) {
        //calculate borders for text
        g.setFont(dispFont);
        calcTextRectangle(g);

        //draw value
        g.setColor(numberColor);
        String text = valueToString(value);
        drawCenteredString(text, 0, 0, g);

        //draw border around value
        if (drawBorder) {
            g.setColor(borderColor);
            int spacing = 3;
            g.drawRect(cx - (int) (fontRectangle.getWidth() / 2) - spacing - 1, cy - (int) (fontRectangle.getHeight() / 2) - spacing, (int) fontRectangle.getWidth() + 2 * spacing + 2, (int) fontRectangle.getHeight() + 2 * spacing);
        }
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    void calcTextRectangle(Graphics g) {
        String testtext = "";
        for (int i = 0; i < digitNumber; i++) testtext += "0";
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        fontMetrics = g2d.getFontMetrics();
        fontRectangle = fontMetrics.getStringBounds(testtext, g2d);
    }

    // Convert a value to a string

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _alt
     * @return
     */
    String valueToString(double _alt) {
        double maxValue = Math.pow(10, digitNumber);

        //negative values
        boolean minus = false;
        while (_alt < 0) {
            if (!displayMinus)
                _alt += maxValue;
            minus = true;
        }

        //overflow values
        while (_alt > maxValue) _alt -= maxValue;

        //parse value to string
        String salt = Integer.toString((int) _alt);
        int missingChars = digitNumber - salt.length();

        if (fillZeros)
            for (int i = 0; i < missingChars; i++) salt = "0" + salt;
        else
            for (int i = 0; i < missingChars; i++) salt = " " + salt;

        if (displayMinus && minus)
            salt = '-' + salt.substring(1, salt.length() - 1);

        return salt;
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


    //get and set methods

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _value
     */
    public void setValue(double _value) {
        value = _value - offset;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @return
     */
    public double getValue() {
        return value;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param b
     */
    public void displayMinus(boolean b) {
        displayMinus = b;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param b
     */
    public void fillZeros(boolean b) {
        fillZeros = b;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param i
     */
    public void digitNumber(int i) {
        digitNumber = i;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param b
     */
    public void drawBorder(boolean b) {
        drawBorder = b;
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
        borderColor = c;
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







	
}
