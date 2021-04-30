package Gauges.RPM;

import Gauges.Common.DialPointer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 * The Airspeed Class is responsible for creating the frame in which the Airspeed gauge is shown
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class RPM_Picture extends JFrame {

    // variable to hold data
    static double RPM = 0.0;
    //static double AS_set = 1013;
    private int inst_size = 275;
    private int cx, cy;
    double offset;

    // Variables to hold sizes
    // Width
    int W = 400;    // 334 to obtain frame of 400
    // Height
    int H = W;
    int WindowSize = 400; // Size of Frame
    //int buff = 400 - W;  // 30

    boolean visibility = true;
    //final BufferedImage image = ImageIO.read(new File("CubDecal.png"));

    /**
     * NAME: Gauges.RPM.RPM()
     * GAUGE: Airspeed Indicator
     * PURPOSE: Constructor Sets the initial characteristics of the Gauges.Airspeed.AirSpeed Frame
     * @throws Exception
     */
    public RPM_Picture() throws Exception {
        // Call constructor of superclass
        super("RPM");
        // Set size of frame
        setSize(WindowSize, WindowSize);
        //default closing action
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // disable display of top bar
        setUndecorated(true);
        // disable resize of frame
        setResizable(true);
        // add an instance of AirSpeedPanel to Airspeed frame
        setContentPane(new RPMPanel()); // AirSpeedPanel() Located Below inside AirSpeedPanel Class
        // Set Background color of frame
        setBackground(Color.BLACK);
        // Set Location of upper left corner to (0,0)
        setLocation(126, 430);
        // Sets the usage of absolute positioning of components
        setLayout(null);
        // Set Frame to visible
        setVisible(visibility);
        // get the center (X,Y) coordinate of center of frame
        cx = this.getWidth() / 2;
        cy = this.getHeight() / 2;


    }   // End of public Gauges.Airspeed.AirSpeed() throws Exception




    /**
     *
     * NAME: setAS
     * GAUGE: Air Speed Indicator
     * PURPOSE: Takes parameter ans assigns to local variable
     * @param _RPM Assigns Airspeed
     */
    public void setRPM(double _RPM) {
        RPM = _RPM;
    }


    /**NAME: getAS
     * GAUGE: Airspeed Indicator
     * PURPOSE: Returns the current Airspeed
     * @return Current Airspeed
     */
    public double getRPM() {
        return RPM;
    }



    /**
     *
     * NAME: setOffset
     * GAUGE: Airspeed Indicator
     * PURPOSE: Used to set offset applied ot current value
     *
     * @return
     */
    public void setOffset() {
        offset = offset + RPM;
        //return offset;
    }


    /**NAME: getOffset
     * GAUGE: Airspeed Indicator
     * PURPOSE: Used to get current offset of value
     * @return Offset Applied to Airspeed
     */
   public double getOffset() {
        return offset;
    }




    /**
     *
     * NAME: AirSpeedPanel Class
     * GAUGE: Airspeed Indicator
     * PURPOSE: Adds remaining parts of the gauge including;
     *          -Gauge Background
     * Extends JPanel
     */
    class RPMPanel extends JPanel {

        //Gauges.RPM.NumericDisplayRPM_Picture ASDispl;
        //Gauges.Airspeed.NumericDisplayAirSpeed_Picture ASSetDispl;
        NumericDialRPM_Picture RPMDial;
        DialPointer dialPt;

        /**
         *
         * NAME: AirSpeedPanel Constructor
         * GAUGE: Airspeed Indicator
         * PURPOSE: Adds Remaining components to panel
         * @throws IOException
         */
        RPMPanel() throws IOException {
            super();
            //ASDispl = new Gauges.Airspeed.NumericDisplayAirSpeed_Picture();   // 20
            //ASSetDispl = new Gauges.Airspeed.NumericDisplayAirSpeed_Picture();    // 12
            //ASSetDispl.digitNumber(4);  // 4

            RPMDial = new NumericDialRPM_Picture();    // font size sets size of numbers
            //ASDial.setLargeLineSize(2);
            //dialPt = new DialPointer(W);
            //dialPt.setPointerType(5);


        }   // End of AirSpeedPanel()

        /**
         *
         * NAME: paintComponent
         * GAUGE: Airspeed Indicator
         * PURPOSE: Paints Gauge components
         * @param g
         */
        public void paintComponent(Graphics g) {
            //cx = this.getWidth() / 2;
            //cy = this.getHeight() / 2;

            // Define new custom color
            //Color gaugeGreen = new Color(46, 138, 0);
            //Color gaugeRed = new Color(198, 3, 0);
            //Color chromeYellow = new Color(255,167,0,255);

            //Graphics2D g2 = (Graphics2D) g;
            //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //final BasicStroke skinnyStroke = new BasicStroke(5.0f);
            //final BasicStroke midStroke = new BasicStroke(7.0f);

            g.setColor(Color.BLACK);

            /*
            Fills the interior with a previously defined color
            using a Rectangle located at (0,0) with a width and height of the window
            */
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            //draw dial
            RPMDial.reposition(cx, cy);
            RPMDial.setRPM(RPM);
            RPMDial.draw(g);


            //draw Gauges.Airspeed.AirSpeed label text
            //g.setFont(new Font("Helvertica", Font.PLAIN, 20));
            //drawCenteredString("MPH", 0, (int) (+inst_size * 0.1) + 55, g);
            //drawCenteredString("MPH", 0, (int)(W/2 * .4), g);


            //int offset = 3;
            //int adjhw = 4;

            // Draw Red Arc on ASI
            //g.setColor(gaugeRed);
            //g2.setStroke(skinnyStroke);
            //g2.draw(new Arc2D.Double(((cx - W / 2) + adjhw), ((cy - W / 2) + adjhw), W - (adjhw * 2), H - (adjhw * 2), 90 - offset, -95 - offset, Arc2D.OPEN));

            // Draw Green Arc on ASI
            //g.setColor(gaugeGreen);
            //g2.setStroke(midStroke);
            //g2.draw(new Arc2D.Double(((cx - W / 2) + adjhw), ((cy - W / 2) + adjhw), W - (adjhw * 2), H - (adjhw * 2), -11 - offset, -95 - offset, Arc2D.OPEN));

            // Draw Center Gauge Image
            //g.drawImage(image, cx-56,cy-70,null);

            //draw pointers
            //double th_AS = (AS) / 120;
            //dialPt.reposition(cx, cy);
            //dialPt.setValue(th_AS);
            //dialPt.draw(g);

            //JButton  NButton = new JButton("");
            //CButton.setIcon(new ImageIcon("CloseButton.png"));
            //NButton.addActionListener(new ButtonListener());
            //NButton.setLayout(null);
            //NButton.setBackground(chromeYellow);
            //NButton.setForeground(Color.BLACK);
            //NButton.setFocusPainted(false);
            //NButton.setBorderPainted(false);
            //NButton.setActionCommand("ZERO");
            //NButton.setBounds(8,325,70,70);
            //add(NButton);

        }   // End of public void paintComponent(Graphics g)



        /**
         *
         * NAME: drawCenteredString
         * GAUGE: Airspeed Indicator
         * PURPOSE: Draws Text on gauge
         * @param text Text to be displayed
         * @param _x X-Coordinate of upper left corner of text box
         * @param _y Y-Coordinate of upper left corner of text box
         * @param g instance of Graphics object
         */
       public void drawCenteredString(String text, int _x, int _y, Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(text, g2d);
            int x = (cx - (int) r.getWidth() / 2) + _x;
            int y = (cy - (int) r.getHeight() / 2) + _y + fm.getAscent();
            g.drawString(text, x, y);
        }   // End of void drawCenteredString(String text, int _x, int _y, Graphics g)
    }   // End of class AirSpeedPanel extends JPanel

/*
    class ButtonListener implements ActionListener {

        ButtonListener() {
        }


        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("ZERO")) {
                setOffset(AS);

                System.out.println("Set Offset of " + getOffset());

                //System.exit(0);

            }
            if (e.getActionCommand().equals("SET NORTH")) {
                System.out.println("SET NORTH has been clicked");
                //System.exit(0);


            }
        }
    }
*/

}   // End of public class Gauges.Airspeed.AirSpeed extends JFrame
