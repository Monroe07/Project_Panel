package Gauges.ADI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The Gauges.ADI.ArtHorizon Class is responsible for creating the Artifical Horizon and all of its accompaniy methods that;
 *          -Create the artifical horizon shape
 *          -Reposition the artifical horizon
 *          -Set the Position and rotation based on values take in a parameters when called by the main method of the Demos.MyFrame and Gauge update Classes
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class ArtHorizon_Picture {
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

	final BufferedImage fi_box;
	final BufferedImage fi_circle;
	final BufferedImage horizon_back;
	final BufferedImage horizon_ball;
	final BufferedImage horizon_circle;
	final BufferedImage horizon_mechanics;


    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param sx
     * @param sy
     */
	public ArtHorizon_Picture(int sx, int sy) throws IOException, URISyntaxException {
		sizex = sx;
		sizey = sy;

		//fi_box = ImageIO.read(new File(getClass().getResource("/Pictures/fi_box.png").toURI()));
		//fi_circle = ImageIO.read(new File(getClass().getResource("/Pictures/fi_circle.png").toURI()));
		//horizon_back = ImageIO.read(new File(getClass().getResource("/Pictures/horizon_back.png").toURI()));
		//horizon_ball = ImageIO.read(new File(getClass().getResource("/Pictures/horizon_ball.png").toURI()));
		//horizon_circle = ImageIO.read(new File(getClass().getResource("/Pictures/horizon_circle.png").toURI()));
		//horizon_mechanics = ImageIO.read(new File(getClass().getResource("../Pictures/horizon_mechanics.png").toURI()));


		fi_box = ImageIO.read(getClass().getResourceAsStream("/Pictures/fi_box.png"));
		fi_circle = ImageIO.read(getClass().getResourceAsStream("/Pictures/fi_circle.png"));
		horizon_back = ImageIO.read(getClass().getResourceAsStream("/Pictures/horizon_back.png"));
		horizon_ball = ImageIO.read(getClass().getResourceAsStream("/Pictures/horizon_ball.png"));
		horizon_circle = ImageIO.read(getClass().getResourceAsStream("/Pictures/horizon_circle.png"));
		horizon_mechanics = ImageIO.read(getClass().getResourceAsStream("/Pictures/horizon_mechanics.png"));









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
        //BasicStroke stroke = new BasicStroke(6,BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER,10);
		if (ipitch > 80)
			ipitch = 80;
		if (ipitch < -80)
			ipitch = -80;



		Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC) ;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY) ;

        // Draw fi_box
        //g2d.drawImage(fi_box, 0,0,null);

		// Rotate by -roll at cx,cy
		g2d.rotate(bank, 200, 200);
		// Draw horizon_back
		g2d.drawImage(horizon_back, 0,0,null);
		// Translate by -pitch
		g2d.translate(0,ipitch);
		// Draw horizon_ball
		g2d.drawImage(horizon_ball,0,0,null);
		// Translate by pitch
		g2d.translate(0,-ipitch);
		// Draw horizon_circle
		g2d.drawImage(horizon_circle,0,0,null);
		// Rotate by roll at cx,cy
		g2d.rotate(-bank, 200,200);
		// Draw horizon_mechanics
		g2d.drawImage(horizon_mechanics, 0,0,null);
		// Draw fi_circle
		//g2d.drawImage(fi_circle, 0,0,null);




		/*
		Draw fi_box
		Rotate by -roll with cx,cy as rotation point
		draw horizon_back
		Translate by -pitch
		Draw horizon_ball
		Translate by +pitch
		Draw horizon_circle
		Rotate by roll with cx,cy as rotation point
		Draw horizon_mechanics
		Draw fi_circle
		 */




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
