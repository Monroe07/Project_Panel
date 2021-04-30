package Gauges.Altimeter;

import Gauges.Common.DialPointer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 * The Gauges.Altimeter.Altimeter Class is responsible for creating the frame in which the Gauges.Altimeter.Altimeter gauge is shown
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class Altimeter_Picture extends JFrame {
	private static final long serialVersionUID = 1L;
	static double alt = 0.0;
	static double alt_set = 1013;
	private int inst_size = 258;
	private int cx, cy;
double offset;
    Color chromeYellow = new Color (255, 167, 0);


	int W = 400;
	int H = W;
    //int buff = 400 - W;
    int FrameSize = 288;

    /**
     * NAME: Gauges.Altimeter.Altimeter()
     * GAUGE: Altitude Indicator
     * PURPOSE: Constructor Sets the initial characteristics of the Gauges.Altimeter.Altimeter Frame
     * @throws IOException
     */
    public Altimeter_Picture() throws IOException{
        //create Window
        super("Altimeter");
        setSize(W, H);
        //default closing action
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(true);

        
        //add label and button
        setContentPane(new AltimeterPanel());
        setBackground(chromeYellow);
        setLocation(1072, -10);
        
        //arrange components inside window
        //frame.pack();
        setVisible(true);
        
        cx = this.getWidth()/2;
        cy = this.getHeight()/2;
    }   // End of public Gauges.Altimeter.Altimeter()

    /**
     * NAME: setAlt
     * GAUGE: Altitude Indicator
     * PURPOSE: Set method to set the altitude
     * @param _alt
     */
    public void setAlt(double _alt) {
    	alt = _alt - offset;
    }


    public void setOffset() {
        offset = offset + alt;
        //return offset;
    }
    /**
     * NAME: setAltSet
     * GAUGE: Altitude Indicator
     * PURPOSE: Sets Secondary Altitude Value
     * @param _altset
     */
    public void setAltSet(double _altset) {
    	alt_set = _altset;
    }

    /**
     * NAME: AltimeterPanel
     * GAUGE: Altitude Indicator
     * PURPOSE: Adds remaining parts of the panel
     */
	class AltimeterPanel extends JPanel {
		private static final long serialVersionUID = 1L;


    	NumericDialAltimeter_Picture altDial;


        /**
         * NAME: AltimeterPanel()
         * GAUGE: Altitude Indicator
         * PURPOSE: Adds remaining parts of the gauge
         * @throws IOException
         */
    	AltimeterPanel() throws IOException {
    		super();


    		altDial = new NumericDialAltimeter_Picture();  // 18


    	}

        /**
         * NAME: paintComponent
         * GAUGE: Altitude Indicator
         * PURPOSE:
         * @param g
         */
    	public void paintComponent(Graphics g)
        {
    		cx = this.getWidth()/2;
            cy = this.getHeight()/2;
            g.setColor(Color.BLACK);

            /*
            Fills the interior with a previously defined color
            using a Rectangle located at (0,0) with a width and height of the window
            */
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


			//Sec Color and fill background with
			//g.setColor(Color.BLACK);
    		//g.fillRect(0, 0, this.getWidth(), this.getHeight());
    		
    		//draw dial
    		altDial.reposition(cx, cy);
    		altDial.setAlt(alt);
    		altDial.draw(g);
    		



    	}   // End of public void paintComponent(Graphics g)
    	
    	//draw a centered string

        /**
         * NAME:
         * GAUGE: Altitude Indicator
         * PURPOSE:
         * @param text
         * @param _x
         * @param _y
         * @param g
         */
    	public void drawCenteredString(String text, int _x, int _y, Graphics g)
        {
    		Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            FontMetrics fm = g2d.getFontMetrics();
            
            Rectangle2D r = fm.getStringBounds(text, g2d);
            int x = (cx - (int) r.getWidth()/2) + _x;
            int y = (cy - (int) r.getHeight()/2) + _y + fm.getAscent();
            g.drawString(text, x, y);
    	}   // End of void drawCenteredString(String text, int _x, int _y, Graphics g)
    	
    }   // End of class AltimeterPanel extends JPanel

    /**
     * NAME:
     * GAUGE: Altitude Indicator
     * PURPOSE:
     */
    class ButtonListener implements ActionListener {

        ButtonListener() {
        }

        /**
         * NAME:
         * GAUGE: Altitude Indicator
         * PURPOSE:
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("  CLOSE  ")) {
                System.out.println("CLOSE has been clicked");
                System.exit(0);

            }
            if (e.getActionCommand().equals("SET NORTH")) {
                System.out.println("SET NORTH has been clicked");
                //System.exit(0);


            }
        }
    }
    public int getGaugeSize()
    {
        return W;
    }

}
