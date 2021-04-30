package Gauges.Radar;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Airspeed Class is responsible for creating the frame in which the Airspeed gauge is shown
 *
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class Radar_Picture extends JFrame {

    // variable to hold data
    ArrayList<Object[]> Planes = new ArrayList<>();
    ArrayList<Object[]> Airports = new ArrayList<>();

    private int cx, cy;
    int viewDistance;
    int screenSize;

    // Variables to hold sizes
    int windowH; // Size of Frame
    int windowW;

    boolean visibility = true;
    //final BufferedImage image = ImageIO.read(new File("CubDecal.png"));

    /**
     * NAME: Gauges.Airspeed.AirSpeed()
     * GAUGE: Airspeed Indicator
     * PURPOSE: Constructor Sets the initial characteristics of the Gauges.Airspeed.AirSpeed Frame
     *
     * @throws Exception
     */
    public Radar_Picture(int _viewDistance, int _viewSize, int _windowH, int _windowW) throws Exception {
        super("Radar");
        System.out.println("Radar_Picture");
        windowH = _windowH;
        windowW = _windowW;
        // Call constructor of superclass

        // Set size of frame
        setSize(_windowW, _windowH);
        //default closing action
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // disable display of top bar
        setUndecorated(false);
        // disable resize of frame
        setResizable(false);
        // add an instance of AirSpeedPanel to Airspeed frame

        setContentPane(new RadarPanel(_viewDistance, _viewSize)); // AirSpeedPanel() Located Below inside AirSpeedPanel Class



        // Set Background color of frame
        setBackground(Color.GRAY);
        // Set Location of upper left corner to (0,0)
        setLocation(0, 0);
        // Sets the usage of absolute positioning of components
        setLayout(null);
        // Set Frame to visible
        setVisible(visibility);
        // get the center (X,Y) coordinate of center of frame
        cx = this.getWidth() / 2;
        cy = this.getHeight()*2/3;


    }   // End of public Gauges.Airspeed.Radar_Picture() throws Exception


    public void clearPlaneList()
    {
        Planes.clear();
    }

    public void addPlane(Object[] _Plane) {
        //Planes.clear();
        Planes.add(_Plane);
    }



    public void clearAirportList()
    {
        Airports.clear();
    }

    public void addAirports(ArrayList<Object[]> _Airports) {
        //Planes.clear();
        //System.out.println(_Airport[0]);
        Airports = _Airports;

        //System.out.println(Airports.get(0)[0]);
    }




    public void setViewDist(int _viewDistance)
    {
        viewDistance = _viewDistance;
        System.out.println("Radar_Picture setViewDist");
    }

    public void setViewSize(int _viewSize) {
        screenSize = _viewSize;
        System.out.println("Radar_Picture setViewSize");
    }


    /**
     * NAME: AirSpeedPanel Class
     * GAUGE: Airspeed Indicator
     * PURPOSE: Adds remaining parts of the gauge including;
     * -Gauge Background
     * Extends JPanel
     */
    class RadarPanel extends JPanel {


        NumericDialRadar_Picture Radar;
        //DialPointer dialPt;

        /**
         * NAME: AirSpeedPanel Constructor
         * GAUGE: Airspeed Indicator
         * PURPOSE: Adds Remaining components to panel
         *
         * @throws IOException
         */
        RadarPanel(int _viewDist, int _viewSize) throws IOException {
            super();
            System.out.println("RadarPanel: " + _viewDist + "\t" + _viewSize);

            //ASDispl = new Gauges.Airspeed.NumericDisplayAirSpeed_Picture();   // 20
            //ASSetDispl = new Gauges.Airspeed.NumericDisplayAirSpeed_Picture();    // 12
            //ASSetDispl.digitNumber(4);  // 4

            Radar = new NumericDialRadar_Picture(_viewDist, _viewSize);    // font size sets size of numbers
            Radar.setViewSize(_viewSize);
            System.out.println("Radar screenSize: " + _viewSize);
            Radar.setViewDist(_viewDist);
            System.out.println("Radar viewDistance: " + _viewDist);
            //ASDial.setLargeLineSize(2);
            //dialPt = new DialPointer(W);
            //dialPt.setPointerType(5);


        }   // End of AirSpeedPanel()

        /**
         * NAME: paintComponent
         * GAUGE: Airspeed Indicator
         * PURPOSE: Paints Gauge components
         *
         * @param g
         */
        public void paintComponent(Graphics g) {
            cx = this.getWidth() / 2;
            cy = this.getHeight() / 2;

            // Define new custom color
            //Color gaugeGreen = new Color(46, 138, 0);
            //Color gaugeRed = new Color(198, 3, 0);
            //Color chromeYellow = new Color(255,167,0,255);

            //Graphics2D g2 = (Graphics2D) g;
            //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //final BasicStroke skinnyStroke = new BasicStroke(5.0f);
            //final BasicStroke midStroke = new BasicStroke(7.0f);

            g.setColor(Color.DARK_GRAY);

            /*
            Fills the interior with a previously defined color
            using a Rectangle located at (0,0) with a width and height of the window
            */
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            //draw dial
            Radar.reposition(cx, cy*3/2);
            // make sure Plane with Object ID = 1 is first
            Radar.setPlanes(Planes);
            Radar.setAirports(Airports);



            Radar.draw(g);


        }   // End of public void paintComponent(Graphics g)


        /**
         * NAME: drawCenteredString
         * GAUGE: Airspeed Indicator
         * PURPOSE: Draws Text on gauge
         *
         * @param text Text to be displayed
         * @param _x   X-Coordinate of upper left corner of text box
         * @param _y   Y-Coordinate of upper left corner of text box
         * @param g    instance of Graphics object
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
