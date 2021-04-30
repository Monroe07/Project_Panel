package Gauges.ADI;

import java.awt.*;
import java.awt.geom.*;

/**
 * The Gauges.ADI.ArtHorizon Class is responsible for creating the Artifical Horizon and all of its accompaniy methods that;
 *          -Create the artifical horizon shape
 *          -Reposition the artifical horizon
 *          -Set the Position and rotation based on values take in a parameters when called by the main method of the Demos.MyFrame and Gauge update Classes
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class ArtHorizon {
	int sizex, sizey;
	int cx, cy;
	boolean displGS;
	double gsOffAngle = 0.0;
	
	Color skycolor;
	Color earthcolor;
	Color gsColor;
	Font numfont;
	
	FontMetrics fontMetrics;
	Rectangle2D fontRectangle;
	
	double pitch = 0.0;
	double bank = 0.0;

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param sx
     * @param sy
     */
	public ArtHorizon(int sx, int sy) {
		sizex = sx;
		sizey = sy;
		
		skycolor = new Color(30,130,190);
		//earthcolor = new Color(210,140,20);  // Old Color of Yellow
		earthcolor = new Color(89,60,9);
		gsColor = new Color(245, 255, 120);
		
		numfont = new Font("Helvertica", Font.BOLD, 10);
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
	public void draw(Graphics g) {
		int ipitch = (int)(Math.toDegrees(pitch) * sizey/50);
        BasicStroke stroke = new BasicStroke(6,BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER,10);
		
		//rotate
		Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.rotate(bank, cx, cy);
		
		
		//sky color
		g.setColor(skycolor);
		g.fillRect(cx - sizex, cy - sizey*3, sizex*2, sizey*3 + ipitch);
		
		//earth color
		g.setColor(earthcolor);
		g.fillRect(cx - sizex, cy + ipitch, sizex*2, sizey*3);
		
		
		//set line color and font
		g.setColor(Color.white);
		g.setFont(numfont);


		//horizontal mainline
		g.drawLine(cx - sizex, cy + ipitch, cx + sizex, cy + ipitch);
		
		//sub lines
		for(int i = -8; i <= 8; i++) {
			g.drawLine(cx - sizex/4, cy - i*sizey/5 + ipitch, cx + sizex/4, cy - i*sizey/5 + ipitch);
			
			int drawint = i * 10;
			String drawText = Integer.toString(drawint);
			calcTextRectangle(g, drawText);
			drawCenteredString(drawText, - sizex/3, - i*sizey/5-2 + ipitch, g);
			drawCenteredString(drawText, + sizex/3, - i*sizey/5-2 + ipitch, g);
		}
		for(double i = -8.5; i <= 8.5; i++) {
			g.drawLine(cx - sizex/6, cy - (int)(i*sizey/5) + ipitch, cx + sizex/6, cy - (int)(i*sizey/5) + ipitch);
		}
		
		//min and max circle
		g.drawOval(cx - 5, cy - 5 - 9*sizey/5 + ipitch, 10, 10);
		g.drawOval(cx - 5, cy - 5 + 9*sizey/5 + ipitch, 10, 10);
		
		//rotation triangle
		Polygon p = new Polygon();
		p.addPoint(cx-8, cy - (int)(sizey*0.38));
		p.addPoint(cx+8, cy - (int)(sizey*0.38));
		p.addPoint(cx, cy-9 - (int)(sizey*0.38));
		g.fillPolygon(p);
		
		
		//rotate back
		g2d.rotate(-bank, cx, cy);
		
		
		//draw rotation angle lines
		g.drawLine(cx, cy - (int)(sizey*0.45), cx, cy - (int)(sizey*0.40));
		g2d.rotate(Math.toRadians(10), cx, cy);
		g.drawLine(cx, cy - (int)(sizey*0.45), cx, cy - (int)(sizey*0.43));
		g2d.rotate(Math.toRadians(10), cx, cy);
		g.drawLine(cx, cy - (int)(sizey*0.45), cx, cy - (int)(sizey*0.43));
		g2d.rotate(Math.toRadians(10), cx, cy);
		g.drawLine(cx, cy - (int)(sizey*0.45), cx, cy - (int)(sizey*0.40));
		g2d.rotate(Math.toRadians(15), cx, cy);
		g.drawLine(cx, cy - (int)(sizey*0.45), cx, cy - (int)(sizey*0.40));
		g2d.rotate(Math.toRadians(-45), cx, cy);
		g2d.rotate(Math.toRadians(-10), cx, cy);
		g.drawLine(cx, cy - (int)(sizey*0.45), cx, cy - (int)(sizey*0.43));
		g2d.rotate(Math.toRadians(-10), cx, cy);
		g.drawLine(cx, cy - (int)(sizey*0.45), cx, cy - (int)(sizey*0.43));
		g2d.rotate(Math.toRadians(-10), cx, cy);
		g.drawLine(cx, cy - (int)(sizey*0.45), cx, cy - (int)(sizey*0.40));
		g2d.rotate(Math.toRadians(-15), cx, cy);
		g.drawLine(cx, cy - (int)(sizey*0.45), cx, cy - (int)(sizey*0.40));
		g2d.rotate(Math.toRadians(45), cx, cy);
		
		//draw aircraft static line
		g.setColor(Color.red);
		// Left Half (upper left corner X,Y; Width, Height)
		g.fillRect(cx - (int)(sizex*0.33), cy-3, (int)(sizex*0.23), 6);
		// Right Half (upper left corner X,Y; Width, Height)
		g.fillRect(cx + (int)(sizex*0.09), cy-3, (int)(sizex*0.23), 6);
		// Center "Dot"
		g.fillRect(cx -2, cy -2, 4, 4);
		// Left Half vertical tail
        //g.fillRect(cx-34, cy+3, 6,12);
        // Right Half Vertical Tail


        // Draw under loop and stand for plane indicator
        g.setColor(Color.DARK_GRAY);
        g2d.setStroke(stroke);
        g.drawArc(cx-30, cy-29, 59, 56, 180,180);
        g.fillRect(cx-5, cy+24, 10,50);

        Polygon T = new Polygon();
        T.addPoint(cx - 10, cy + 74);
        T.addPoint(cx + 10, cy + 74);
        T.addPoint(cx + 90, cy + 250);
        T.addPoint(cx - 90, cy + 250);
        g.fillPolygon(T);

        Polygon B = new Polygon();
        B.addPoint(cx, cy);
        B.addPoint(cx - 2, cy + 25);
        B.addPoint(cx + 2, cy + 25);
        g.setColor(Color.BLACK);
        g.fillPolygon(T);
        g.setColor(Color.DARK_GRAY);
        g.fillPolygon(B);

		g.setColor(Color.white);

		//draw Glide slope indication
		if (displGS) {
			Polygon pgs = new Polygon();
			pgs.addPoint(cx - (int)(sizex * 0.45), cy - 5);
			pgs.addPoint(cx - 4 - (int)(sizex * 0.45), cy);
			pgs.addPoint(cx - (int)(sizex * 0.45), cy + 5);
			pgs.addPoint(cx + 4 - (int)(sizex * 0.45), cy);
			
			//draw diamonds
			g.drawPolygon(pgs);
			
			g2d.translate(0, (int)sizey * 0.12);
			g.fillPolygon(pgs);
			g2d.translate(0, (int)sizey * 0.12);
			g.fillPolygon(pgs);
			g2d.translate(0, -(int)sizey * 2 * 0.12);
			
			g2d.translate(0, -(int)sizey * 0.12);
			g.fillPolygon(pgs);
			g2d.translate(0, -(int)sizey * 0.12);
			g.fillPolygon(pgs);
			g2d.translate(0, (int)sizey * 2 * 0.12);
			
			int offpix = (int)( 2 * gsOffAngle * sizey * 0.12);
			g.setColor(gsColor);
			g.fillRect(cx - (int)(sizex * 0.45) - 10, cy - 2 + offpix, 20, 4);
		}
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     * @param testtext
     */
	public void calcTextRectangle(Graphics g, String testtext) {
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
	public void drawCenteredString(String text, int _x, int _y, Graphics g) {
        int x = (cx - (int) fontRectangle.getWidth()/2) + _x;
        int y = (cy - (int) fontRectangle.getHeight()/2) + _y + fontMetrics.getAscent();
        g.drawString(text, x, y);
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param value
     * @param minmax
     * @return
     */
	public double clamp(double value, double minmax) {
		if (value > minmax)
			value = minmax;
		if (value < -minmax)
			value = -minmax;
		
		return value;
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
     * @param value
     */
	public void setDisplayGS(boolean value) {
		displGS = value;
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param value
     */
	public void setGSAngle(double value) {
		gsOffAngle = value;
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _pitch
     * @param _bank
     */
	public void setValues(double _pitch, double _bank)
	{
		pitch = _pitch;
		bank = _bank;
	}
}
