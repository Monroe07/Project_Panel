package Data_Connectors;

import Gauges.Common.SplashScreen;
import Gauges.Controls.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Menu;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */

/**
 * Mimics the functionality of the Original Gauges.Controls.Control_NOTUSED Panel
 */

public class Gauge_Update_UDP_With_Menu_LINK2FS extends JFrame {
    // Place holder Values for the Center (X,Y) of the window
    private int cx;
    private int cy;
    Menu menu;
    // Create new Color for use
    Color chromeYellow = new Color(255, 167, 0);
    // Variables to hold the height and width of the frame
    int W = 80;
    int H = 800;


    Gauge_Update_UDP_With_Menu_LINK2FS() throws Exception {
        // Setup the splash screen


    }


    /**
     * NAME:
     * GUAGE:
     * PURPOSE:
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SplashScreen screen;
        ImageIcon splash = new ImageIcon(Gauge_Update_UDP_With_Menu_LINK2FS.class.getResource("Project Panel Splash 1024x768.png"));
        screen = new SplashScreen(splash);
        screen.setLocation(0, 0);
        screen.setSize(1024, 768);
        screen.setProgressMax(5000);
        screen.setScreenVisible(true);
        screen.setAlwaysOnTop(true);

////////////////////////////////////////////////////////////////////////////////////////////////////////

        screen.setProgress(0);
        double newValues[] = {0, 0, 0, 0, 0, 0, 0, 0};   // must have same # of members as the # of data types being sent
        double currentValues[] = {0, 0, 0, 0, 0, 0, 0, 0};

        int packetSize = 8;


        //int delay = 25;
///////////////////////////////////////////////////////////////////////////////////////////////////////
        // Create Instance of Gauge Methods
        //Data_Connectors.GaugeUpdate_UDP_WO_Menu gauge = new Data_Connectors.GaugeUpdate_UDP_WO_Menu();
        //Gauges.Altimeter.Altimeter alt = new Gauges.Altimeter.Altimeter();
        //Gauges.HSI.HSI hsi = new Gauges.HSI.HSI();
        //Gauges.ADI.ADI adi = new Gauges.ADI.ADI();
        //Gauges.Airspeed.AirSpeed speed = new Gauges.Airspeed.AirSpeed();
        ControlRight panel = new ControlRight(); /////// REENABLE
        ControlLeft Left = new ControlLeft();
        ControlMid mid = new ControlMid();
        ControlTop top = new ControlTop();
        ControlBottom bottom = new ControlBottom();
        //Gauges.Menus.Menu_Advanced menu = new Gauges.Menus.Menu_Advanced();
        //Gauges.VSI.VSI vsi = new Gauges.VSI.VSI();
        //Gauges.Turn_Coordinator.TurnCoordinator tc = new Gauges.Turn_Coordinator.TurnCoordinator();
        //Gauges.Menus.Menu menu = new Gauges.Menus.Menu();
        //menu.setVisible(true);

        for (int i = 0; i < 5000; i++) {
            screen.setProgress(i);
            Thread.sleep(1);
        }
        screen.setScreenVisible(false);


        //panel.repaint();
        do {

            // SET GAUGE VALUES !!!! /////////////////////////////

            try {
                int port = 9876;
                int i;
                // Create a socket to listen on the port.
                DatagramSocket dsocket = new DatagramSocket(port);
                // Tell Java its ok to reuse socket
                dsocket.setReuseAddress(true);

                // Create a space for new data
                byte[] buffer = new byte[2048];

                // Create a packet to receive data into the buffer
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Now loop forever, waiting to receive packets and printing them.
                while (true) {
                    // Wait to receive a datagram
                    dsocket.receive(packet);

                    // Convert the contents to a string, and display them
                    String msg = new String(buffer, 0, packet.getLength());
                    i = 0;
                    //System.out.println(packet.getLength());
                    // WHILE PACKET STILL HAS DATA IN IT
                    while (i < msg.length()) {
                        //System.out.println("Got to while (i < msg.length())");
                        switch (msg.charAt(i)) {

                            case '<': {
                                //System.out.println("Got to case '<': ");
                                i++;
                                switch (msg.charAt(i)) {
                                    // ALTITUDE
                                    case 'D': {
                                        //System.out.println("Got to case 'D'");
                                        i++;
                                        int L = 5;
                                        String Alt = "";
                                        for (int j = 0; j < L; j++) {
                                            Alt = Alt + msg.charAt(i);
                                            i++;
                                        }
                                        newValues[3] = Integer.parseInt(Alt);
                                        //System.out.println(newValues[3]);
                                        break;
                                    }
                                    // HEADING
                                    case 'J': {
                                        i++;
                                        int L = 3;
                                        String Heading = "";
                                        for (int j = 0; j < L; j++) {
                                            Heading = Heading + msg.charAt(i);
                                            i++;
                                        }
                                        newValues[6] = Double.parseDouble(Heading);
                                        break;
                                    }
                                    // VERTICAL SPEED
                                    case 'L': {
                                        i++;
                                        int L = 6;
                                        String VerticalSpeed = "";
                                        for (int j = 0; j < L; j++) {
                                            VerticalSpeed = VerticalSpeed + msg.charAt(i);
                                            i++;
                                        }
                                        newValues[7] = (Double.parseDouble(VerticalSpeed))/100;
                                        break;
                                    }
                                    // TURN COORDINATOR YAW
                                    case 'N': {
                                        i++;
                                        int L = 3;
                                        String TC = "";
                                        for (int j = 0; j < L; j++) {
                                            TC = TC + msg.charAt(i);
                                            i++;
                                        }
                                        newValues[5] = (Double.parseDouble(TC));
                                        break;
                                    }
                                    // AIRSPEED
                                    case 'P': {
                                        i++;
                                        int L = 3;
                                        String AirSpeed = "";
                                        for (int j = 0; j < L; j++) {
                                            AirSpeed = AirSpeed + msg.charAt(i);
                                            i++;
                                        }
                                        newValues[0] = Double.parseDouble(AirSpeed);
                                        break;
                                    }
                                    // PITCH
                                    case 'Q': {
                                        i++;
                                        int L = 6;
                                        String Pitch = "";
                                        for (int j = 0; j < L; j++) {
                                            Pitch = Pitch + msg.charAt(i);
                                            i++;
                                        }
                                        newValues[2] = -(Double.parseDouble(Pitch));
                                        break;
                                    }
                                    // ROLL
                                    case 'R': {
                                        i++;
                                        int L = 6;
                                        String Roll = "";
                                        for (int j = 0; j < L; j++) {
                                            Roll = Roll + msg.charAt(i);
                                            i++;
                                        }
                                        newValues[1] = Double.parseDouble(Roll);
                                        break;
                                    }

                                }
                                break;
                            }
                            // FIRST CHARACTER DESIGNATOR
                            case '#':
                            {
                                i++;
                                switch(msg.charAt(i))
                                {
                                    case 'G':
                                    {
                                        i++;
                                        int L = 6;
                                        String RollRate = "";
                                        for (int j = 0; j < L; j++)
                                        {
                                            RollRate = RollRate +msg.charAt(i);
                                            i++;

                                        }
                                        newValues[4] = Double.parseDouble(RollRate);
                                        break;
                                    }
                                }
                            }

                        }
                        i++;
                        //System.out.println("Got to End of While Loop");
                    }

                    //System.out.println("Got out of while (i < msg.length())");

                    panel.setValues(newValues, packetSize); //REENABLE
                    // Then copy values in newValues to currentValues


                    // Reset the length of the packet before reusing it.
                    packet.setLength(buffer.length);
                    //System.out.println("Got to end of While(true) loop");
                }
            } catch (Exception e) {
                System.err.println(e);
            }

            // END OF SET GAUGE VALUES !!!! ////////////////////////

        } while (panel.isShowing()); // while(alt.isShowing());

        System.out.println("Broke!!");

    }
}