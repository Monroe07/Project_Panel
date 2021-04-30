package Gauges.HSI;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class NumericDialHSI {
	double startingAngle = 0; // start angle of the zero
	double spacingAngle = 0; //angle of the space before zero

	//for incontinous dials
	boolean incontinousDial;

	double maxValue;
	double minValue;
	double bigLineStep;

	int digitNumber = 1;
	int cutBeginningDigits = 0;
	boolean leadingZeros = true;
	boolean rotatingDisplay = false;
	int subScaleLines = 4;  //9
	int largeLinesSize = 2;
	int dialDiameter;
	int cx, cy;
	double offset;

	Font numDispFont;
	Font letterDispFont;
	Rectangle2D fontRectangle;
	FontMetrics fontMetrics;
	Color numberColor;
	Color dialColor;
	Color chromeYellow = new Color (255, 167, 0);
	Color gauge = new Color (221,215,191);

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param fontsize
     * @param _dialDiameter
     */
	NumericDialHSI(int fontsize, int _dialDiameter) {
		numDispFont = new Font("SansSerif", Font.PLAIN, fontsize);
		letterDispFont = new Font("SansSerif", Font.PLAIN, 35);
		dialDiameter = _dialDiameter;
		cx = 150;
		cy = 150;
		
		//numberColor = Color.white;	// color for numbers on dial
		//dialColor = Color.white;		// Dial Tick Marks

		numberColor = Color.black;
		dialColor = Color.black;

		maxValue = 10;
		minValue = 0;
		bigLineStep = 1;		// Sets Spacing of small lines on outside of Gauges.Altimeter.Altimeter
		
		incontinousDial = false;
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
	void draw(Graphics g) {
        Color gaugeBackground = new Color(219,186,133);
        Color NESW = new Color(198, 3, 0);
		g.setFont(numDispFont);
        // Sets Border Color of Gauge
		g.setColor(Color.DARK_GRAY);
		g.fillOval((cx - dialDiameter/2)-3, (cy - dialDiameter/2)-3, dialDiameter + 6, dialDiameter + 6);
		//draw outside border for both Gauges.Altimeter.Altimeter and Gauges.HSI.HSI
		g.setColor(gaugeBackground);
		// SET BACKGROUND Images FOR ROUND GAUGES
		g.fillOval((cx - dialDiameter/2) + 3, (cy - dialDiameter/2)+3, dialDiameter - 6, dialDiameter - 6);
		//g.drawOval((cx - dialDiameter/2), (cy - dialDiameter/2), dialDiameter, dialDiameter);
		
		
		double fullAngle = 2*Math.PI - spacingAngle;    // in radians
		
		//draw lines and numbers
		for (double i = minValue; i <= maxValue; i += bigLineStep) {
			if (!incontinousDial && i >= maxValue) break;
			
			double angle = i/maxValue*fullAngle + startingAngle;
				double mult = .36; // .375
			//draw text
			double sx = dialDiameter*mult*Math.sin(angle);
			double sy = -dialDiameter*mult*Math.cos(angle);
			
			g.setColor(numberColor);
            String dialText = "";
			int dispNumber = (int)(Math.floor(i) / Math.pow(10, cutBeginningDigits));

			if (dispNumber == 0 || dispNumber == 9 || dispNumber == 18 || dispNumber == 27)
            {
                switch(dispNumber)
                {
                    case 0: {
                        g.setColor(NESW);
                        g.setFont(letterDispFont);
                        dialText = "N";
                        break;
                    }
                    case 9: {
                        g.setColor(NESW);
                        g.setFont(letterDispFont);
                        dialText = "E";
                        break;
                    }
                    case 18: {
                        g.setColor(NESW);
                        g.setFont(letterDispFont);
                        dialText = "S";
                        break;
                    }
                    case 27: {
                        g.setColor(NESW);
                        g.setFont(letterDispFont);
                        dialText = "W";
                        break;
                    }
                    default:
                        g.setColor(Color.BLACK);
                }
            }   // if (dispNumber == 0 || dispNumber == 9 || dispNumber == 18 || dispNumber == 27)
            else
                {
                g.setFont(numDispFont);
			    dialText = Integer.toString(dispNumber);

                //leading zeros
                if (leadingZeros) while (dialText.length() < digitNumber) dialText = "0" + dialText;
            }
			//calculate text rect
			calcTextRectangle(g, dialText);
			
			Graphics2D g2d = (Graphics2D)g;
			if (rotatingDisplay) {
	    		g2d.rotate(angle, cx, cy);

	    		// Draws N,E,S,W or degree number (in 30 degree increments)
	    		drawCenteredString(dialText, 0, ((int)(-dialDiameter*0.375)) + 6, g);
	    		g2d.rotate(-angle, cx, cy);
			} else {
				//normal drawing
				drawCenteredString(dialText, (int)(sx), (int)(sy), g);
			}
            
            
            
            //draw large lines Every 30 Degrees
            g.setColor(dialColor);
            g2d.rotate(angle, cx, cy);
            g.fillRect(cx-1, cy+(int)(-dialDiameter*0.49), largeLinesSize, (int)(dialDiameter*0.083));
            g2d.rotate(-angle, cx, cy);
            
            
            if (i >= maxValue) break;
            //draw sub lines between 30 Degree seperators
            for (int j = 1; j <= subScaleLines; j++) {
            	double x2 = dialDiameter*0.475*Math.sin((i + (double)j * bigLineStep / ((double)subScaleLines+1))/maxValue*fullAngle + startingAngle);
	            double y2 = -dialDiameter*0.475*Math.cos((i + (double)j * bigLineStep / ((double)subScaleLines+1))/maxValue*fullAngle + startingAngle);
	            
            	g.drawLine(cx + (int)x2, cy + (int)y2, cx + (int)(x2*0.9), cy + (int)(y2*0.9));
            }
		}
		
	}

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
        int x = (cx - (int) fontRectangle.getWidth()/2) + _x;
        int y = (cy - (int) fontRectangle.getHeight()/2) + _y + fontMetrics.getAscent();
        g.drawString(text, x, y);
	}
	
	
	/**
     * NAME:
     * GAUGE:
     * PURPOSE:
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
		numDispFont = f;
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
	public void setLargeLineSize( int value ) {
		largeLinesSize = value;
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _offset
     */
	public void setOffset ( double _offset)
    {
        offset = _offset;
    }
}
