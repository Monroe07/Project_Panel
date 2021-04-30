package Gauges.HSI;

import Gauges.Common.DialPointer;

import java.awt.*;

import javax.swing.*;


import java.io.IOException;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class HSI extends JFrame{
	private static final long serialVersionUID = 1L;
	private int cx, cy;
	double heading = 0.0;
	double smallA = 0.0;
	double smallADrift = 0.0;
	double largeA = 0.0;
	double miles = 0.0;
    double offset;

	int W = 258;
	int H = W;
	int Size = 288;
	int buff = Size - W;
    //final BufferedImage image = ImageIO.read(new File("C:\\Users\\Monroe\\IdeaProjects\\JavaProjects\\src\\BasicInstruments\\Pictures\\CubDecal.png"));

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @throws IOException
     */
    public HSI() throws IOException{
		super("Gauges.HSI.HSI");
		
		//default closing action
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setSize(W + buff, H + buff);
        // setSize(300, 300 + 20);
        setUndecorated(true);
        setResizable(true);
        //add label and button
        setContentPane(new HSIPanel());

        setLocation(368, 416);
        // setLocation(300, 0);
        
        //arrange components inside window
        setVisible(true);
        
        cx = this.getWidth()/2;
        cy = this.getHeight()/2;
        //JOptionPane.showMessageDialog(null, "CX: " + cx + " CY: " + cy);
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param value
     */
	public void setHeading(double value) {
		heading = value - offset;
	}

    public void setOffset() {
        offset = offset + heading;
        //return offset;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param value
     */
	public void setSmallA(double value) {
		smallA = value + offset;
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param value
     */
	public void setLargeA(double value) {
		largeA = value;
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param value
     */
	public void setSmallADrift(double value) {
		smallADrift = value;
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param value
     */
	public void setMiles(double value) {
		miles = value;
	}

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     */
	class HSIPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		NumericDialHSI compassDial;
		NumericDisplayHSI numDisp;
		DialPointer dialPt;
		DialPointer dialPtL;
		StaticDial statDial;

        /**
         * NAME:
         * GAUGE:
         * PURPOSE:
         * @throws IOException
         */
		HSIPanel() throws IOException{
			super();
			/**
			* @param pointTypeVOR Sets the type of pointer displayed for VOR Indicator. When setPointerTpe is called from Gauges.Common.DialPointer Class
			* @param pointTypeLrgArrow Sets the type of pointer displayed for Heading Indicator. When setPointerTpe is called from Gauges.Common.DialPointer Class
			 */

			int pointTypeVOR = 3;
			int pointTypeLrgArrow = 4;
			compassDial = new NumericDialHSI(18, W);  //16

			compassDial.setMinMaxValue(0, 36, 3);
			compassDial.setLines(2);
			compassDial.setDigitNumbers(2, 0, false);
			compassDial.setRotatingDisplay(true);
			
			statDial = new StaticDial(W);
			
			numDisp = new NumericDisplayHSI(22);
			numDisp.digitNumber(4);
			//numDisp.setBorderColor(Color.black);
			numDisp.drawBorder(false);
			
			dialPt = new DialPointer(W);

			//dialPt.setPointerType(pointTypeLrgArrow);
			dialPt.setPointerType(4);  //4             // Defines the type of Pointer used on gauge. Used in Gauges.Common.DialPointer class in SwitchStatement
			
			dialPtL = new DialPointer(W);
			dialPtL.setPointerType(pointTypeVOR);
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
            
            
            // SET BACKGROUND COLOR FOR Gauges.HSI.HSI
			Color chromeYellow = new Color (255, 167, 0);
			// g.setColor(Color.black);
			g.setColor(Color.BLACK);       // Set Background
    		g.fillRect(0, 0, this.getWidth(), this.getHeight());

    		g.setColor(Color.black);
			//draw number display
    		numDisp.reposition(cx, cy - 50);
    		numDisp.setValue(miles * 10);
    		numDisp.draw(g);
    		
    		//draw comma and line
    		//g.setColor(Color.white);
			g.setColor(Color.black);
    		g.drawLine(cx-30, cy-36, cx+11, cy-36);
    		g.drawRect(cx + 16, cy - 37, 1, 1);
    		
    		//draw static display
    		statDial.reposition(cx, cy);
    		statDial.draw(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		g2d.rotate(heading, cx, cy);
    		
    		//draw compass dial
    		compassDial.reposition(cx, cy);
    		compassDial.draw(g);
    		
    		//draw pointer
    		dialPt.reposition(cx, cy);
    		dialPt.setValue(smallA);
    		dialPt.setoffValue(smallADrift);
    		dialPt.draw(g);
    		
    		//draw second pointer
    		//dialPtL.reposition(cx, cy);
    		//dialPtL.setValue(largeA);
    		//dialPtL.draw(g);
		}
	}
}
