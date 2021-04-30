package Gauges.HSI;

import Gauges.Common.DialPointer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class HSI_Picture extends JFrame{
	private static final long serialVersionUID = 1L;
	private int cx, cy;
	double heading = 0.0;

    double offset;

	int W = 400;
	int H = W;

    //final BufferedImage image = ImageIO.read(new File("C:\\Users\\Monroe\\IdeaProjects\\JavaProjects\\src\\BasicInstruments\\Pictures\\CubDecal.png"));

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @throws Exception
     */
    public HSI_Picture() throws Exception{
		super("Gauges.HSI.HSI");
		
		//default closing action
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(W , H);
        // setSize(300, 300 + 20);
        setUndecorated(true);
        setResizable(true);
        //add label and button
        setContentPane(new HSIPanel());
		setBackground(Color.BLACK);
        setLocation(600, 430);
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
     */
	class HSIPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		NumericDialHSI_Picture numDial;


        /**
         * NAME:
         * GAUGE:
         * PURPOSE:
         * @throws IOException
         */
		HSIPanel() throws IOException{
			super();





			
			numDial = new NumericDialHSI_Picture();

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
			Color chromeYellow = new Color (255, 139, 0);
			// g.setColor(Color.black);
			g.setColor(Color.BLACK);       // Set Background
    		g.fillRect(0, 0, this.getWidth(), this.getHeight());

    		//g.setColor(Color.black);
			//draw number display
    		numDial.reposition(cx, cy);
    		numDial.setHeading(heading);
    		numDial.draw(g);
    		


    		
    		//draw second pointer
    		//dialPtL.reposition(cx, cy);
    		//dialPtL.setValue(largeA);
    		//dialPtL.draw(g);
		}
	}
}
