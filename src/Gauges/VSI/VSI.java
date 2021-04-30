package Gauges.VSI;

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
public class VSI extends JFrame {

    private static final long serialVersionUID = 1L;
    static double VSI = 0.0;
    static double VSI_set = 1013;
    private int inst_size = 260;
    private int cx, cy;

    int W = 258; // AKA Dial Diameter was 380
    int H = W;
    int Size = 288;
    int buff = Size - W;  // 30
        //final BufferedImage image = ImageIO.read(new File("CubDecal.png"));

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @throws Exception
     */
    public VSI() throws Exception {
        //create Window
        super("Airspeed");

        setSize(W + buff, H + buff);
        //default closing action
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(true);



        //add label and button
        setContentPane(new VSIPanel()); // AirSpeedPanel() Located Below inside AirSpeedPanel Class

        setLocation(656, 416);

        //arrange components inside window
        //frame.pack();
        setVisible(true);

        cx = this.getWidth() / 2;
        cy = this.getHeight() / 2;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _VSI
     */
   public void setVSI(double _VSI) {
        VSI = _VSI;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _VSIset
     */
    public void setVSISet(double _VSIset)
    {
        VSI_set = _VSIset;
    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     */
    class VSIPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        NumericDisplayVSI VSIDispl;
        NumericDisplayVSI VSISetDispl;
        NumericDialVSI VSIDial;
        DialPointer dialPt;

        /**
         * NAME:
         * GAUGE:
         * PURPOSE:
         * @throws IOException
         */
        VSIPanel() throws IOException{
            super();
            VSIDispl = new NumericDisplayVSI(20);   // 20
            VSISetDispl = new NumericDisplayVSI(12);    // 12
            VSISetDispl.digitNumber(4);  // 4

            VSIDial = new NumericDialVSI(30, W);    // fontsize sets size of numbers
            VSIDial.setLargeLineSize(2);
            dialPt = new DialPointer(W);
            dialPt.setPointerType(6);
        }   // End of AirSpeedPanel()

        /**
         * NAME:
         * GAUGE:
         * PURPOSE:
         * @param g
         */
        public void paintComponent(Graphics g){
            cx = this.getWidth() / 2;
            cy = this.getHeight() / 2;

            // Define new custom color
            Color chromeYellow = new Color(255, 167, 0);
            Color gaugeGreen = new Color(46, 138, 0);
            Color gaugeRed = new Color(198, 3, 0);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            final BasicStroke skinnyStroke = new BasicStroke(5.0f);
            final BasicStroke midStroke = new BasicStroke(7.0f);
            final BasicStroke wideStroke = new BasicStroke(9.0f);

            //Sec Color and fill background with
            g.setColor(Color.BLACK);       // Background
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            //draw dial
            VSIDial.reposition(cx, cy);
            VSIDial.draw(g);



            //draw Gauges.Airspeed.AirSpeed label text
            g.setFont(new Font("Helvertica", Font.PLAIN, 25));
            drawCenteredString("VSI", 0, +40, g);

            g.setColor(Color.RED);
            g.setFont(new Font("Helvertica", Font.PLAIN, 20));
            drawCenteredString("UP", -75, -25 , g);
            drawCenteredString("DN", -75, 25, g);

            //g.drawImage(image, cx-56,cy-70,null);


            //draw pointers
            double th_VSI = VSI / 50;
            dialPt.reposition(cx, cy);        // Instance of Gauges.Common.DialPointer.reposition
            dialPt.setValue(th_VSI);
            dialPt.draw(g);
        }   // End of public void paintComponent(Graphics g)

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
       public void drawCenteredString(String text, int _x, int _y, Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            FontMetrics fm = g2d.getFontMetrics();

            Rectangle2D r = fm.getStringBounds(text, g2d);
            int x = (cx - (int) r.getWidth() / 2) + _x;
            int y = (cy - (int) r.getHeight() / 2) + _y + fm.getAscent();
            g.drawString(text, x, y);
        }

    }
}   // End of public class Gauges.Airspeed.AirSpeed extends JFrame
