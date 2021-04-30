package Gauges.HSI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class NumericDialHSI_Picture {
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

	double heading;

	Font numDispFont;
	Font letterDispFont;
	Rectangle2D fontRectangle;
	FontMetrics fontMetrics;
	Color numberColor;
	Color dialColor;
	Color chromeYellow = new Color (255, 167, 0);
	Color gauge = new Color (221,215,191);

	final BufferedImage heading_yaw;
	final BufferedImage heading_mechanics;
	final BufferedImage fi_circle;

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param
     * @param
     */
	NumericDialHSI_Picture() throws IOException {
		heading_mechanics = ImageIO.read(getClass().getResourceAsStream("/Pictures/heading_mechanics.png"));
		heading_yaw = ImageIO.read(getClass().getResourceAsStream("/Pictures/heading_yaw.png"));
		fi_circle = ImageIO.read(getClass().getResourceAsStream("/Pictures/fi_circle.png"));
	}



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

		g2d.rotate(-heading, 200,200);
		g2d.drawImage(heading_yaw,0,0,null);
		g2d.rotate(heading, 200,200);
		g2d.drawImage(heading_mechanics,0,0,null);
		//g2d.drawImage(fi_circle,0,0,null);


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
     * @param _offset
     */
	public void setOffset ( double _offset)
    {
        offset = _offset;
    }

    public void setHeading(double _heading) { heading = _heading;}
}
