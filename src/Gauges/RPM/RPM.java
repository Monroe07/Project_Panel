package Gauges.RPM;

import Gauges.Common.DialPointer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class RPM extends JFrame {

	private static final long serialVersionUID = 1L;
	static double RPM = 0.0;
	static double RPM_set = 1013;
	private int inst_size = 260;
	private int cx, cy;

	int W = 200;
	int H = 200;
	int buff = 30;

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @throws IOException
     */
    public RPM() throws IOException{
        //create Window
        super("Gauges.RPM.RPM");
        
        //default closing action
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(W + buff, H + 20 + buff);
        
        //add label and button
        setContentPane(new RPMPanel());
        
        setLocation(0, 0);
        
        //arrange components inside window
        //frame.pack();
        setVisible(true);
        
        cx = this.getWidth()/2;
        cy = this.getHeight()/2;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _RPM
     */
    void setRPM(double _RPM) {
    	RPM = _RPM;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _RPMset
     */
    void setRPMSet(double _RPMset) {
    	RPM_set = _RPMset;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     */
	class RPMPanel extends JPanel {

		NumericDisplayRPM RPMDispl;
    	NumericDisplayRPM RPMSetDispl;
    	NumericDialRPM RPMDial;
    	DialPointer dialPt;

        /**
         * NAME:
         * GAUGE:
         * PURPOSE:
         * @throws IOException
         */
    	RPMPanel() throws IOException{
    		super();
    		RPMDispl = new NumericDisplayRPM(20);
    		RPMSetDispl = new NumericDisplayRPM(12);
    		RPMSetDispl.digitNumber(4);
    		RPMDial = new NumericDialRPM(18, W);
    		RPMDial.setLargeLineSize(2);
    		dialPt = new DialPointer(W);
    	}

        /**
         * NAME:
         * GAUGE:
         * PURPOSE:
         * @param g
         */
    	public void paintComponent(Graphics g) {
    		cx = this.getWidth()/2;
            cy = this.getHeight()/2;

			// Define new custom color
			Color chromeYellow = new Color (255, 167, 0);

			//Sec Color and fill background with
			g.setColor(Color.black);
    		g.fillRect(0, 0, this.getWidth(), this.getHeight());
    		
    		//draw dial
    		RPMDial.reposition(cx, cy);
    		RPMDial.draw(g);
    		
    		//draw RPMGauge value and box
    		RPMDispl.reposition(cx, cy - 40);
    		RPMDispl.setValue(RPM);
    		RPMDispl.draw(g);

    		/*
    		//draw RPMGauge setting box
    		RPMSetDispl.reposition(cx, cy + 65);
    		RPMSetDispl.setValue(RPM_set);
    		RPMSetDispl.draw(g);
            */

            //draw Gauges.RPM.RPM label text
    		g.setFont(new Font("Helvertica", Font.BOLD, 11));
            drawCenteredString("x", 0 - 17, ((int)(+inst_size*0.1)), g);
			g.setFont(new Font("Helvertica", Font.BOLD, 18));
			drawCenteredString("1000", 0 + 5, ((int)(+inst_size*0.1)), g);

            //draw mbar text
            //g.setFont(new Font("Helvertica", Font.BOLD, 10));
            //drawCenteredString("mbar", (int)0, 80, g);



    		//draw pointers
            double th_RPM = RPM / 1000;
            dialPt.reposition(cx, cy);		// Instance of Gauges.Common.DialPointer.reposition
            dialPt.setValue(th_RPM);
            dialPt.draw(g);
    	}
    	
    	//draw a centered string

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
    		Graphics2D g2d = (Graphics2D) g;
            FontMetrics fm = g2d.getFontMetrics();
            
            Rectangle2D r = fm.getStringBounds(text, g2d);
            int x = (cx - (int) r.getWidth()/2) + _x;
            int y = (cy - (int) r.getHeight()/2) + _y + fm.getAscent();
            g.drawString(text, x, y);
    	}
    	
    }
}
