package Gauges.Controls;// Package Details
// Import Statements
import Gauges.ADI.ADI;
import Gauges.Airspeed.AirSpeed;
import Gauges.Altimeter.Altimeter;
import Gauges.HSI.HSI;
import Gauges.Menus.Menu;
import Gauges.Turn_Coordinator.TurnCoordinator;
import Gauges.VSI.VSI;

import javax.swing.*;
import java.awt.*;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




/**
 * The Gauges.Controls.Control_NOTUSED Class is responsible for creating the frame in which the "Gauges.Controls.Control_NOTUSED Panel" is shown, as well as adding the
 * relative additional contend such as buttons that;
 *          -Close the program
 *          -Open a menu window(Work In Progress)
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class Control_NOTUSED extends JFrame
{
    // Place holder Values for the Center (X,Y) of the window
    double newValues[] = {0,0,0,0,0,0,0,0};   // must have same # of members as the # of data types being sent
    double currentValues[] = {0,0,0,0,0,0,0,0};
    private int cx;
    private int cy;

    Gauges.Menus.Menu menu;
    AirSpeed speed;
    ADI adi;
    Altimeter alt;
    TurnCoordinator tc;
    HSI hsi;
    VSI vsi;


    // Create new Color for use
    Color chromeYellow = new Color(255, 167, 0);
    // Variables to hold the height and width of the frame
    int W = 80;
    int H = 800;


    public void setValues(double[] values, int len)
    {
        for(int i = 0; i < len; i++)
        {
            newValues[i] = values[i];
        }

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
        speed.setAS(newValues[0]);
        speed.repaint();

        adi.setPitchBankValues(newValues[2], newValues[1]);
        adi.repaint();

        alt.setAlt(newValues[3]);
        alt.repaint();

        tc.setTC(newValues[4]);
        tc.setTCSet(newValues[5]);
        tc.repaint();

        hsi.setHeading(newValues[6]);
        hsi.repaint();

        vsi.setVSI(newValues[7]);
        vsi.repaint();

    }
    /**
     * Gauges.Controls.Control_NOTUSED Constructor
     * NAME:
     * GAUGE:
     * PURPOSE:
     */
    public Control_NOTUSED() throws Exception
    {
        // Set The Title of the Frame (Not Displayed)
        super ("Gauges.Controls.Control_NOTUSED");
        // Set Size of Frame
        setSize(W,H);
        // Set What happens when the Close Button is clicked (Not Displayed)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Disable Display of Border "Decorations" of Frame
        setUndecorated(true);
        // Disable the Ability To Resize the Frame
        setResizable(false);
        // Adds the Contents of ControlPanel() to the Frame
        setContentPane(new ControlPanel());
        //display();
        // Set Background of Frame to Black
        setBackground(Color.BLACK);
        // Sets Upper Left-Hand Corner to the (X,Y) Coordinate in Pixels
        setLocation(1200,0);
        // Set The panel to hold a Grid Layout
        setLayout(new GridLayout(10, 1,5,20));
        // Show the Frame
        setVisible(true);
        // Check That Contents of Frame are Displayed and If Not, Show Them
        validate();








    }
    public void display() throws Exception
    {



        // Replace menu with frame
        menu = new Menu();
        speed = new AirSpeed();
        adi = new ADI();
        alt = new Altimeter();
        tc = new TurnCoordinator();
        hsi = new HSI();
        vsi = new VSI();


    }

    /**
     * Gauges.Controls.Control_NOTUSED Panel Class
     * NAME:
     * GAUGE:
     * PURPOSE:
     */
    class ControlPanel extends JPanel
    {



        ControlPanel()
        {
           super();


        }

        /**PURPOSE: Paints all Items on ControlPanel
         * NAME:
         * GAUGE:
         * PURPOSE:
         * @param g
         */
        public void paintComponent(Graphics g)
        {
            // Get and Store the Center Point of The Frame as (cx,cy)
            cx = this.getWidth() / 2;
            cy = this.getHeight() /2;

            // Create Close Button
            JButton  CButton = new JButton("CLOSE");
            // Add icon "CloseButton.png" to CButton
            //CButton.setIcon(new ImageIcon("CloseButton.png"));
            // Add instance of ButtonListener() to button
            CButton.addActionListener(new ButtonListener());
            // Set background of button to the color created and stored as "chromeYellow"
            CButton.setBackground(chromeYellow);
            // Set The Foreground (In This Case The Text Color" to Black
            CButton.setForeground(Color.BLACK);
            // Disable the "highlight" Feature That Makes The button "Glow" when hovered over
            CButton.setRolloverEnabled(false);
            //CButton.setFocusPainted(false);
            // Enable the button border
            CButton.setBorderPainted(true);
            // Enable the button (for use with possible disable feature in future
            CButton.setEnabled(true);
            // Add button to panel
            add(CButton);

            // Create new button named NButton ( Will serve as menu button in future
            JButton  MButton = new JButton("MENU");
            // Add icon "CloseButton.png" to CButton
            //CButton.setIcon(new ImageIcon("CloseButton.png"));
            // Add instance of ButtonListener() to button
            MButton.addActionListener(new ButtonListener());
            // Set background of button to the color created and stored as "chromeYellow"
            MButton.setBackground(chromeYellow);
            // Set The Foreground (In This Case The Text Color" to Black
            MButton.setForeground(Color.BLACK);
            // Disable the "highlight" Feature That Makes The button "Glow" when hovered over
            MButton.setRolloverEnabled(false);
            //NButton.setFocusPainted(false);
            // Enable the button border
            MButton.setBorderPainted(true);
            // Enable the button (for use with possible disable feature in future
            MButton.setEnabled(true);
            // Add button to panel
            add(MButton);




        }

    }

    /** PURPOSE: Button Listener used to handle all button events on the control panel
     * NAME:
     * GAUGE:
     * PURPOSE:
     */
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
            // Handle the close button press
            if (e.getActionCommand().equals("CLOSE")) {
                // Display message on console for debug purposes
                System.out.println("CLOSE has been clicked");
                // Close Program
                System.exit(0);

            }
            // Handle the menu button press
            if (e.getActionCommand().equals("MENU")) {
                // Display message for console debug purposes
                System.out.println("Gauges.Menus.Menu Has Been Opened");
                //menu.setVisible(true);
            }
        }
    }

}
