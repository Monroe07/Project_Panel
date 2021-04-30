package Gauges.Turn_Coordinator;

import Gauges.Common.DialPointer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class TurnCoordinator extends JFrame
{

        private static final long serialVersionUID = 1L;
        static double TC = 0.0;
        static double TC_set = 0;
        private int inst_size = 260;
        private int cx, cy;
        double offset;


        int W = 258; // AKA Dial Diameter was 380
        int H = W;
        //int buff = 400 - W;  // 30
        int WindowSize = 288;
        //final BufferedImage image = ImageIO.read(new File("Pictures\\CubDecal.png"));

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @throws Exception
     */
        public TurnCoordinator() throws Exception
        {
                //create Window
                super("Turn Coordinator");

                setSize(WindowSize, WindowSize);
                //default closing action
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setUndecorated(true);
                setResizable(true);


                //add label and button
                setContentPane(new TurnCoordinatorPanel()); // TurnCoordinatorPanel() Located Below inside TurnCoordinatorPanel Class

                setLocation(80, 416);

                //arrange components inside window
                //frame.pack();
                setVisible(true);

                cx = this.getWidth() / 2;
                cy = this.getHeight() / 2;
        }   // End of public Gauges.Turn_Coordinator.TurnCoordinator() throws Exception

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _TC
     */
      public  void setTC(double _TC)
            {
                TC = _TC-offset;
            }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @return
     */
       public double getTC()
            {
                return TC;
            }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _TCset
     */
       public void setTCSet(double _TCset)
            {
                TC_set = _TCset;
            }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _offset
     * @return
     */
   public double setOffset(double _offset) {
                offset = offset + _offset;
                return offset;
            }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @return
     */
   public double getOffset()
            {
                return offset;
            }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     */
        class TurnCoordinatorPanel extends JPanel
        {

            NumericDisplayTC TCDispl;
            NumericDisplayTC TCSetDispl;
            NumericDialTC TCDial;
            DialPointer dialPt;
            DialPointer dialYaw;

            /**
             * NAME:
             * GAUGE:
             * PURPOSE:
             * @throws IOException
             */
                TurnCoordinatorPanel()throws IOException
                    {
                        super();
                        TCDispl = new NumericDisplayTC(20);   // 20
                        TCSetDispl = new NumericDisplayTC(12);    // 12
                        TCSetDispl.digitNumber(4);  // 4

                        TCDial = new NumericDialTC(35, W);    // fontsize sets size of numbers
                        TCDial.setLargeLineSize(2);
                        dialPt = new DialPointer(W);
                        dialYaw = new DialPointer(W);
                        dialPt.setPointerType(8);
                        dialYaw.setPointerType(9);

                    }   // End of TurnCoordinatorPanel()

            /**
             * NAME:
             * GAUGE:
             * PURPOSE:
             * @param g
             */
            public void paintComponent(Graphics g){
            cx = this.getWidth() / 2;
            cy = this.getHeight() / 2;
            int yawRateDisplayOffset = 15;


            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            //Sec Color and fill background with
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());





            //draw dial
            TCDial.reposition(cx, cy);
            TCDial.draw(g);



            //draw Gauges.Turn_Coordinator.TurnCoordinator label text
            g.setColor(Color.BLACK);

            g.setFont(new Font("Helvetica", Font.PLAIN, 25));
            // "L" Label
            drawCenteredString("L", -60, (this.getHeight()/2)-240, g);
            // "R" Label
            drawCenteredString("R", 60, (this.getHeight()/2)-240, g);

            // "2 MIN TURN" label
            g.setFont(new Font("Helvetica", Font.PLAIN, 22));
            drawCenteredString("2 MIN TURN", 0, ((int) (+inst_size * 0.1) + 83)- yawRateDisplayOffset, g);

            // "DC ELEC" label
            g.setFont(new Font("Helvetica", Font.PLAIN, 9));
            drawCenteredString("DC ELEC", 0, (int) (+inst_size * 0.1) + 100- yawRateDisplayOffset, g);

            //g.drawImage(image, cx-56,cy-70,null);


            //draw pointers
            double th_TC = (TC);
            double yaw = TC_set;

            dialPt.reposition(cx,cy);        // Instance of Gauges.Common.DialPointer.reposition
            dialYaw.reposition(cx,cy);

            dialPt.setValue(th_TC);
            dialYaw.setValue(yaw);

            dialPt.draw(g);
            dialYaw.draw(g);

            // Draw Center Lines for Yaw Rate Indicator
            g.setColor(Color.RED);
            int startY = 55 - yawRateDisplayOffset;
            int endY = startY + 38 ;
            int wid = 36;
            g.drawLine(cx - (wid/2),cy + startY,cx - (wid/2),cy + endY);
            g.drawLine(cx + (wid/2),cy + startY,cx + (wid/2),cy + endY);






        }   // End of public void paintComponent(Graphics g)



            /**
             * NAME:
             * GAUGE:
             * PURPOSE:
             * @param text
             * @param _x
             * @param _y
             * @param g
             */
            void drawCenteredString(String text, int _x, int _y, Graphics g)
            {
                Graphics2D g2d = (Graphics2D) g;
                FontMetrics fm = g2d.getFontMetrics();

                Rectangle2D r = fm.getStringBounds(text, g2d);
                int x = (cx - (int) r.getWidth() / 2) + _x;
                int y = (cy - (int) r.getHeight() / 2) + _y + fm.getAscent();
                g.drawString(text, x, y);
            }   // End of void drawCenteredString(String text, int _x, int _y, Graphics g)

        }   // End of class TurnCoordinatorPanel extends JPanel

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     */
    class ButtonListener implements ActionListener {
        /**
         *  NAME:
         *  GAUGE:
         * PURPOSE:
         */
        ButtonListener() {
        }

        /**
         * NAME:
         * GAUGE:
         * PURPOSE:
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("SET 0")) {
                setOffset(TC);

                System.out.println("Set Offset of " + getOffset());
                //System.exit(0);

            }
            if (e.getActionCommand().equals("SET NORTH")) {
                System.out.println("SET NORTH has been clicked");
                //System.exit(0);


            }
        }
    }



}   // End of public class Gauges.Turn_Coordinator.TurnCoordinator extends JFrame
