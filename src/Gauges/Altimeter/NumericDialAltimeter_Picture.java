package Gauges.Altimeter;

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
public class NumericDialAltimeter_Picture {
	double startingAngle = 0; // start angle of the zero
	double spacingAngle = 0; //angle of the space before zero

	//for incontinous dials

	double maxAlt = 20000;
	double minAlt = 0;
	double maxPres = 1035;
	double minPres = 980;

	double fullAngle = 2*Math.PI;


	double bigNeedleAngle;
	double bigNeedleValue;
	double smallNeedleAngle;
	double smallNeedleValue;

	double needleOffset = Math.toRadians(90);

	int cx, cy;

	double altitude = 0;
	double pressure = 0;


	Color chromeYellow = new Color (255, 167, 0);
	Color gauge = new Color (221,215,191);

	final BufferedImage altitude_pressure;
	final BufferedImage altitude_ticks;
	final BufferedImage fi_needle;
	final BufferedImage fi_needle_small;
	final BufferedImage fi_circle;


    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param
     * @param
     */
	NumericDialAltimeter_Picture() throws IOException {

		altitude_pressure = ImageIO.read(getClass().getResourceAsStream("/Pictures/altitude_pressure.png"));
		altitude_ticks = ImageIO.read(getClass().getResourceAsStream("/Pictures/altitude_ticks.png"));
		fi_needle = ImageIO.read(getClass().getResourceAsStream("/Pictures/fi_needle.png"));
		fi_needle_small = ImageIO.read(getClass().getResourceAsStream("/Pictures/fi_needle_small.png"));
		fi_circle = ImageIO.read(getClass().getResourceAsStream("/Pictures/fi_circle.png"));

		cx = 200;
		cy = 200;
		
		//numberColor = Color.white;	// color for numbers on dial
		//dialColor = Color.white;		// Dial Tick Marks





	}

	void setPressure(double p)
	{
		pressure = p;
	}

	void setAlt(double a)
	{
		altitude = a;
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
	void draw(Graphics g) {
		// Handle Values outside of range
		if (pressure < minPres){pressure = minPres;}
		if (pressure > maxPres){pressure = maxPres;}
		if (altitude < minAlt) {altitude = minAlt;}
		if (altitude > maxAlt) {altitude = maxAlt;}

		smallNeedleValue = altitude / 1000;
		smallNeedleAngle = (smallNeedleValue/10)*fullAngle;

		bigNeedleValue = altitude - ((int)smallNeedleValue*1000);
		bigNeedleAngle = (bigNeedleValue/1000)*fullAngle;

		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC) ;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY) ;

		g2d.drawImage(altitude_pressure,0,0,null);
		g2d.drawImage(altitude_ticks,0,0,null);
		g2d.rotate(smallNeedleAngle, 200,200);
		g2d.drawImage(fi_needle_small, 0,0,null);
		g2d.rotate(-smallNeedleAngle,200,200);
		//g2d.drawImage(fi_circle,0,0,null);
		g2d.rotate(bigNeedleAngle+needleOffset,200,200);
		g2d.drawImage(fi_needle,0,0,null);
		g2d.rotate(-(bigNeedleAngle+needleOffset), 200,200);



	}



	//draws a centered string


	public void reposition(int _cx, int _cy) {
		cx = _cx;
		cy = _cy;
	}

}
