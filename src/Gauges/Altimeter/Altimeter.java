package Gauges.Altimeter;

import Gauges.Common.DialPointer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.swing.*;

/**
 * The Gauges.Altimeter.Altimeter Class is responsible for creating the frame in which the Gauges.Altimeter.Altimeter gauge is shown
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class Altimeter extends JFrame {
	private static final long serialVersionUID = 1L;
	static double alt = 0.0;
	static double alt_set = 1013;
	private int inst_size = 258;
	private int cx, cy;
double offset;



	int W = 258;
	int H = W;
    //int buff = 400 - W;
    int FrameSize = 288;

    /**
     * NAME: Gauges.Altimeter.Altimeter()
     * GAUGE: Altitude Indicator
     * PURPOSE: Constructor Sets the initial characteristics of the Gauges.Altimeter.Altimeter Frame
     * @throws IOException
     */
    public Altimeter() throws IOException{
        //create Window
        super("Gauges.Altimeter.Altimeter");
        setSize(FrameSize, FrameSize);
        //default closing action
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(true);

        
        //add label and button
        setContentPane(new AltimeterPanel());
        setBackground(Color.BLACK);
        setLocation(656, 64);
        
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
		NumericDisplayAltimeter altDispl;
    	NumericDisplayAltimeter altSetDispl;
    	NumericDialAltimeter altDial;
    	DialPointer dialPt;

        /**
         * NAME: AltimeterPanel()
         * GAUGE: Altitude Indicator
         * PURPOSE: Adds remaining parts of the gauge
         * @throws IOException
         */
    	AltimeterPanel() throws IOException {
    		super();
    		altDispl = new NumericDisplayAltimeter(20);
    		altSetDispl = new NumericDisplayAltimeter(12);
    		altSetDispl.digitNumber(4);
    		
    		altDial = new NumericDialAltimeter(25, W);  // 18
    		altDial.setLargeLineSize(2);
    		dialPt = new DialPointer(W);
            dialPt.setPointerType(1);
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


            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color chromeYellow = new Color (255, 167, 0);

			//Sec Color and fill background with
			//g.setColor(Color.BLACK);
    		//g.fillRect(0, 0, this.getWidth(), this.getHeight());
    		
    		//draw dial
    		altDial.reposition(cx, cy);
    		altDial.draw(g);
    		
    		//draw altitude value and box
    		altDispl.reposition(cx, cy - 40);
    		altDispl.setValue(alt);
    		altDispl.draw(g);

    		/*
    		//draw altimeter setting box
    		altSetDispl.reposition(cx, cy + 65);
    		altSetDispl.setValue(alt_set);
    		altSetDispl.draw(g);
            */

            //draw Gauges.Altimeter.Altimeter label text
            g.setFont(new Font("Helvertica", Font.PLAIN, 12));
            drawCenteredString("FEET", 25, cy-225, g);
            drawCenteredString("100", -25, cy-225, g);
            
            //draw mbar text
            //g.setFont(new Font("Helvertica", Font.BOLD, 10));
            //drawCenteredString("mbar", (int)0, 80, g);

            JButton  NButton = new JButton("SET NORTH");
            //CButton.setIcon(new ImageIcon("CloseButton.png"));
            NButton.addActionListener(new ButtonListener());
            NButton.setBackground(Color.BLACK);
            NButton.setForeground(Color.RED);
            NButton.setFocusPainted(false);
            NButton.setBorderPainted(false);
           // NButton.setLocation(0, 0);
            //add(NButton);
            //NButton.setVisible(true);




    		//draw pointers
            double th_alt = alt / 1000;
            dialPt.reposition(cx, cy);		// Instance of Gauges.Common.DialPointer.reposition
            dialPt.setValue(th_alt);
            dialPt.draw(g);



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
