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
 *
 * <NOTE> NOT USED ANYMORE!! USING MENU_ADVANCED </NOTE>
 *
 */
public class Menu extends JFrame
{
    int WINDOW_HEIGHT = 200;
    int WINDOW_WIDTH = 150;
    int DISPLAY_HEIGHT = 768;
    int DISPLAY_WIDTH = 1024;
    JFrame frame;
    //JPanel panel;
    JButton zeroAlt;
    JButton zeroAS;
    JButton setNorth;
    JButton close;


    AirSpeed speed = new AirSpeed();
    ADI adi = new ADI();
    Altimeter alt = new Altimeter();
    TurnCoordinator tc = new TurnCoordinator();
    HSI hsi = new HSI();
    VSI vsi = new VSI();


    double newValues[] = {0,0,0,0,0,0,0,0};   // must have same # of members as the # of data types being sent
    double currentValues[] = {0,0,0,0,0,0,0,0};
    Color chromeYellow = new Color(255, 167, 0);

    public Menu() throws Exception
    {


        super("MENU");
        // Set Size of Frame
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        // Set What happens when the Close Button is clicked (Not Displayed)
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Disable Display of Border "Decorations" of Frame
        setUndecorated(true);
        // Disable the Ability To Resize the Frame
        setResizable(false);
        setAlwaysOnTop(true);
        setFocusable(false);
        // Set Background of Frame to Black
        setBackground(Color.BLACK);
        // Sets Upper Left-Hand Corner to the (X,Y) Coordinate in Pixels
        setLocation((DISPLAY_WIDTH/2) - (WINDOW_WIDTH/2),(DISPLAY_HEIGHT/2)- (WINDOW_HEIGHT/2));
        // Set The panel to hold a Grid Layout
        setLayout(new GridLayout(2, 2,5,5));
        // Show the Frame
        setVisible(false);

        zeroAlt = new JButton("0 ALT");
        zeroAS = new JButton("0 AS");
        setNorth = new JButton("Set North");
        close = new JButton("CLOSE");

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



        add(zeroAS);
        add(zeroAlt);
        add(setNorth);
        add(close);

        // Check That Contents of Frame are Displayed and If Not, Show Them
        validate();

    }

    public void setValues(double[] values, int len)
    {


        // SetGauge Values and repaint
        /*
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
