package Gauges.HSI;

import java.awt.*;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class StaticDial {
	int cx, cy;
	int diameter;
	Color dialColor;

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param dialDiam
     */
	public StaticDial(int dialDiam) {
		dialColor = Color.white;

		//dialColor = Color.black;	// Sets Standard color for all objects displayed on gauges
		diameter = dialDiam;
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
	public void draw(Graphics g) {
        Color gaugeBackground = new Color(219,186,133);
		Polygon p = new Polygon();
		p.addPoint(cx, (cy - diameter/2)+5);
		p.addPoint(cx - 4, (cy - 8 - diameter/2)+5);
		p.addPoint(cx + 4, (cy - 8 - diameter/2)+5);
		
		g.setColor(gaugeBackground);
		Graphics2D g2d = (Graphics2D)g;
		
		//small triangles
		// Draws Small Triangles around outside edge of Gauges.HSI.HSI
		g.setColor(Color.red);
		for (int i = 0; i < 8; i++) {
			g2d.rotate(Math.PI/4, cx, cy);
			//g.fillPolygon(p);
		}
		g.setColor(dialColor);
		
		//large triangle
		// Draws Large Triangles around outside edge of Gauges.HSI.HSI
		Polygon p2 = new Polygon();
		p2.addPoint(cx, (cy - diameter/2) + 20);
		p2.addPoint(cx - 8, (cy - 8 - diameter/2) + 20);
		p2.addPoint(cx + 8, (cy - 8 - diameter/2) + 20);
		g.fillPolygon(p2);
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
}
