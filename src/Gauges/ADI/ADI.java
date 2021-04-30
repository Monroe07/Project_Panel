package Gauges.ADI;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

/**
 * The Gauges.ADI.ADI Class is Responsible for creating the frame in which the Artificial Horizon is shown.
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class ADI extends JFrame {
	private static final long serialVersionUID = 1L;
	/*
	 *Purpose - Place Holder for Center X and Y coordinates
	 *Side effect - makes cx and cy values available to entire class
	 */
	private int cx;
	private int cy;
	/*
	 *Purpose - Place holder for Pitch and bank values
	 *Side Effect - Makes pitch and bank values available to entire class
	 */
	double pitch = 0.0;
	double bank = 0.0;
	double locOffV = 0.0;

	// Size of Gauge in X and Y values
	int sizex = 258;    // 400
	int sizey = 258;    // 400

	// Size of Window in X and Y values
    int titleBuffer = 0;
    int W = 288;    // 400
	int H = W;    // 400
/**
 * NAME: Gauges.ADI.ADI()
 * GAUGE: Gauges.ADI.ADI
 * PURPOSE: Constructor Sets the initial characteristics of the Gauges.ADI.ADI Frame
 */
	public ADI() {
	    // Set the Title of the frame ( Not displayed )
		super("Gauges.ADI.ADI");
		// Sets the size of the frame
        setSize(W-titleBuffer, H-titleBuffer);
		// Sets the default closing action
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Hides the default "decorations" ex. the title bar, close/minimize/maximize buttons
        setUndecorated(true);
        // Disables the resize functionality of the frame
        setResizable(true);
        // Adds a new instance of ADIPanel() to the frame
        setContentPane(new ADIPanel());
        // Sets the location of the frame in reference to the upper left corner of the screen (0,0)
        // Right -> +X, Down -> +Y
        // Right 400px, down 0px (Second Gauge Position)
        setLocation(368, 64);
        // Make the frame Visible
        setVisible(true);
        // Assigns the center of the frame as a reference in X and Y
        cx = this.getWidth()/2;
        cy = this.getHeight()/2;
        // Assigns Frame as always on top
        setAlwaysOnTop(false);
	}

    /**
     * NAME: setPitchBankValues
     * GAUGE: Gauges.ADI.ADI
     * PURPOSE: Passes Roll and Pitch Values into the gauge
     * @param _pitch Value of pitch angle in radians
     * @param _bank Value of bank(aka roll) angle in radians
     */
	public void setPitchBankValues(double _pitch, double _bank) {
		pitch = _pitch;
		bank = _bank;
	}

    /**
     *
     * NAME: setGSAngle
     * GAUGE: Gauges.ADI.ADI
     * PURPOSE: Set Method to pass a GS ( Glide Slope ) value to the Glide Slope indicator (Left Center of Gauge)
     * @param value Angle of glide slope above or below proper approach angle to runway
     */
	public void setGSAngle(double value) {
		locOffV = value;
	}

    /**
     * NAME: ADIPanel \n
     * GAUGE: Gauges.ADI.ADI
     * PURPOSE: Contains the meat of the Gauges.ADI.ADI Gauge Design
     * Extends JPanel
     */
	public class ADIPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		ArtHorizon artHorizon;

        /**
         * NAME: ADIPanel()
         * GAUGE: Gauges.ADI.ADI
         * PURPOSE: Constructor used to call the superclasses (JPanel) default constructor
         */
		public ADIPanel() {
			// Call Constructor of superclass
		    super();
			// Greats an Instance of the Gauges.ADI.ArtHorizon Class
			artHorizon = new ArtHorizon(sizex, sizey);
			// Passes true parameter to the setDisplayGS method of the artHorizon instance of Gauges.ADI.ArtHorizon class
			artHorizon.setDisplayGS(false);
		}

        /**
         * NAME: paintComponent
         * GAUGE: Gauges.ADI.ADI
         * PURPOSE: Draws Gauge parts inside frame
         * @param g Instance of the Graphic object
         */
		public void paintComponent(Graphics g) {
		    // Placeholders for the center (X,Y) coordinate of frame in Pixels
			cx = this.getWidth()/2;
            cy = this.getHeight()/2;
            // Instance of Graphics
            Graphics2D g2d= (Graphics2D) g;
            // Enables Graphics Anti-Aliasing (makes round shapes appear smoother
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Sets the color used for all drawings until changed
            g.setColor(Color.BLACK);

            /*
            Fills the interior with a previously defined color
            using a Rectangle located at (0,0) with a width and height of the window
            */
    		g.fillRect(0, 0, this.getWidth(), this.getHeight());
    		
    		//draw artificial horizon
    		artHorizon.reposition(cx, cy);
    		artHorizon.setValues(pitch, bank);
    		artHorizon.setGSAngle(locOffV);
    		artHorizon.draw(g);

            // Uses center coordinate to find the height and width
    		int H = cy * 2;
    		int W = cx * 2;
    		// value used to control the size of the gauge circle
    		int adj = 5;
    		int diaAdj = 20;    // 40
            g.setColor(Color.BLACK);



            // Creates "mask" to make Gauges.ADI.ADI look like round gauge
            // Square space of frame
			Area a = new Area(new Rectangle(W, H));
            // Creates false edge around Gauges.ADI.ADI circular gauge face
    		Area b = new Area (new Ellipse2D.Double((diaAdj/2),(diaAdj/2),H-diaAdj, W-diaAdj));
            // Subtracts (Window) same shape (Circle) out of both square mask and false circular gauge edge
            a.subtract(new Area(new Ellipse2D.Double(cx - (sizex/2),cy - (sizey/2),sizex,sizey)));    //
            b.subtract(new Area(new Ellipse2D.Double(cx - (sizex/2),cy - (sizey/2),sizex,sizey)));
            // Fills "mask"
    		g2d.fill(a);
    		// Sets the false gauge edge to be "dark Grey
    		g.setColor(Color.DARK_GRAY);
    		// Fills remaining false gauge edge shape
    		g2d.fill(b);
		}
		
	}
}
