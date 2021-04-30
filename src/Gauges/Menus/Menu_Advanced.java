package Gauges.Menus;

import Gauges.ADI.ADI;
import Gauges.Airspeed.AirSpeed;
import Gauges.Altimeter.Altimeter;
import Gauges.HSI.HSI;
import Gauges.Turn_Coordinator.TurnCoordinator;
import Gauges.VSI.VSI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Monroe on 5/29/2017.
 */
public class Menu_Advanced extends JFrame
{
    int WINDOW_HEIGHT = 170;
    int WINDOW_WIDTH = 165;
    int DISPLAY_HEIGHT = 768;
    int DISPALY_WIDTH = 1024;

    JFrame frame;

    Graphics g;

    JPanel panel_One;
    JPanel panel_Two;
    JPanel panel_Three;
    JPanel panel_Four;

    JTabbedPane main_Panel;

    JButton zeroAlt;
    JButton zeroAS;
    JButton setNorth;
    JButton close;

    JLabel zero_ALT;
    JLabel zero_AS;
    JLabel set_NORTH;
    JLabel close_Label;



    AirSpeed speed = new AirSpeed();
    ADI adi = new ADI();
    Altimeter alt = new Altimeter();
    TurnCoordinator tc = new TurnCoordinator();
    HSI hsi = new HSI();
    VSI vsi = new VSI();


    //double newValues[] = {0,0,0,0,0,0,0,0};   // must have same # of members as the # of data types being sent
    //double currentValues[] = {0,0,0,0,0,0,0,0};
    Color chromeYellow = new Color(255, 167, 0);

    public Menu_Advanced() throws Exception
    {


        super("MENU");
        // Set Size of Frame
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        // Disable Display of Border "Decorations" of Frame
        setUndecorated(true);
        // Disable the Ability To Resize the Frame
        setResizable(false);
        // Set Gauges.Menus.Menu to be above all else
        setAlwaysOnTop(true);
        // add contents of panel to frame
        setContentPane(new MenuPanel());
        // Set Background of Frame to Black
        setBackground(chromeYellow);
        // Sets Upper Left-Hand Corner to the (X,Y) Coordinate in Pixels
        setLocation((DISPALY_WIDTH/2) - (WINDOW_WIDTH/2),(DISPLAY_HEIGHT/2)- (WINDOW_HEIGHT/2));

        // Set The panel to hold a Grid Layout
        //setLayout(new GridLayout(4, 1,0,0));
        setLayout(new FlowLayout());
        // Fill on background
        // Show the Frame
        setVisible(false);



    }

    public void setValues(double[] values, int len)
    {
        /*  SetGauge Values and repaint
         newValues[0] = Gauges.Airspeed.AirSpeed------> speed
         newValues[1] = Roll----------> adi
         newValues[2] = Pitch---------> adi
         newValues[3] = Altitude------> alt
         newValues[4] = Roll/Sec------> tc (setTC)
         newValues[5] = Yaw/Sec-------> tc (setTCSet)
         newValues[6] = Heading-------> hsi
         newValues[7] = Climb rate----> vsi
         */
        speed.setAS(values[0]);
        adi.setPitchBankValues(Math.toRadians(values[2]), Math.toRadians(values[1]));
        alt.setAlt(values[3]);
        tc.setTC(values[4]);
        tc.setTCSet(values[5]);
        hsi.setHeading(-Math.toRadians(values[6]));
        hsi.setSmallA(Math.toRadians(values[6]));
        vsi.setVSI(values[7]);
        speed.repaint();
        adi.repaint();
        alt.repaint();
        tc.repaint();
        hsi.repaint();
        vsi.repaint();

    }

    class MenuPanel extends JPanel
    {
        MenuPanel()
        {
            setBackground(chromeYellow);



            panel_One = new JPanel();
            panel_One.setBackground(Color.BLACK);

            panel_Two = new JPanel();
            panel_Two.setBackground(Color.BLACK);

            panel_Three = new JPanel();
            panel_Three.setBackground(Color.BLACK);

            panel_Four = new JPanel();
            panel_Four.setBackground(Color.BLACK);

            //main_Panel = new JPanel




            zeroAlt = new JButton("SET");
            zeroAS = new JButton("SET");
            setNorth = new JButton("SET");
            close = new JButton("          ");

            zero_ALT = new JLabel("Zero Alt");
            zero_AS = new JLabel("Zero AS");
            set_NORTH = new JLabel("Set North");
            close_Label = new JLabel("DONE");

            zero_ALT.setForeground(Color.WHITE);
            zero_AS.setForeground(Color.WHITE);
            set_NORTH.setForeground(Color.WHITE);
            close_Label.setForeground(Color.WHITE);

            /// ADD ACTION LISTENERS////////////////////
            zeroAlt.addActionListener((new ButtonListener()));
            zeroAS.addActionListener((new ButtonListener()));
            setNorth.addActionListener((new ButtonListener()));
            close.addActionListener((new ButtonListener()));

            /// SET PROPERTIES FOR BUTTONS
            zeroAlt.setBackground(chromeYellow);
            zeroAlt.setForeground(Color.BLACK);
            zeroAlt.setFocusPainted(false);

            zeroAS.setBackground(chromeYellow);
            zeroAS.setForeground(Color.BLACK);
            zeroAS.setFocusPainted(false);

            setNorth.setBackground(chromeYellow);
            setNorth.setForeground(Color.BLACK);
            setNorth.setFocusPainted(false);

            close.setBackground(Color.RED);
            close.setForeground(Color.WHITE);
            close.setFocusPainted(false);

            /// Enable sets for buttons
            zeroAlt.setEnabled(true);
            zeroAS.setEnabled(true);
            setNorth.setEnabled(true);
            close.setEnabled(true);



            panel_One.add(zero_AS);
            panel_One.add(zeroAS);

            panel_Two.add(zero_ALT);
            panel_Two.add(zeroAlt);

            panel_Three.add(set_NORTH);
            panel_Three.add(setNorth);

            panel_Four.add(close_Label);
            panel_Four.add(close);


            add(panel_One);
            add(panel_Two);
            add(panel_Three);
            add(panel_Four);

            // Check That Contents of Frame are Displayed and If Not, Show Them
            validate();
        }
    }
    class ButtonListener implements ActionListener {

        // Default Constructor
        ButtonListener() {
        }

        /**
         *  PURPOSE: Method to handle button events based on the action command of button pressed
         *  NAME:
         *  GAUGE:
         * PURPOSE:
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            // Handle the zeroAlt button press
            if (e.getSource().equals(zeroAlt)) {
                // Display message on console for debug purposes
                //System.out.println("zeroAlt Has Been Clicked");
                alt.setOffset();

            }
            // Handle the zeroAS button press
            else if (e.getSource().equals(zeroAS)) {
                // Display message for console Closed
                //System.out.println("zeroAS Has Been Clicked");
                speed.setOffset();
                //setVisible(false);
            }
            // Handle the setNorth button press
            else if (e.getSource().equals(setNorth)) {
                // Display message for console Closed
                //System.out.println("setNorth Has Been Clicked");
                hsi.setOffset();
                //setVisible(false);
            }

            // Handle the menu button press
            else if (e.getSource().equals(close)) {
                // Display message for console Closed
                //System.out.println("Gauges.Menus.Menu Has Been Closed");
                setVisible(false);
            }
        }
    }
}
