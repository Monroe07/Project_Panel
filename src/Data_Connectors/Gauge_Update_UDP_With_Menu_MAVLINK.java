package Data_Connectors;

import Gauges.Common.SplashScreen;
import Gauges.Controls.ControlRight;

import javax.swing.*;
import java.awt.*;
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

public class Gauge_Update_UDP_With_Menu_MAVLINK extends JFrame
{
    // Place holder Values for the Center (X,Y) of the window
    private int cx;
    private int cy;
    Menu menu;
    // Create new Color for use
    Color chromeYellow = new Color(255, 167, 0);
    // Variables to hold the height and width of the frame
    int W = 80;
    int H = 800;


        Gauge_Update_UDP_With_Menu_MAVLINK() throws Exception
        {
            // Setup the splash screen


        }




        /**
         * NAME:
         * GUAGE:
         * PURPOSE:
         * @param args
         * @throws Exception
         */
    public static void main(String[] args) throws Exception
    {
        SplashScreen screen;
        ImageIcon splash = new ImageIcon(Gauge_Update_UDP_With_Menu_MAVLINK.class.getResource("Project Panel Splash 1024x768.png"));
        screen = new SplashScreen(splash);
        screen.setLocation(0,0);
        screen.setSize(1024,768);
        screen.setProgressMax(5000);
        screen.setScreenVisible(true);
        screen.setAlwaysOnTop(true);

////////////////////////////////////////////////////////////////////////////////////////////////////////

        screen.setProgress(0);
        double newValues[] = {0,0,0,0,0,0,0,0};   // must have same # of members as the # of data types being sent
        double currentValues[] = {0,0,0,0,0,0,0,0};

        int packetSize = 8;


		//int delay = 25;
///////////////////////////////////////////////////////////////////////////////////////////////////////
    	// Create Instance of Gauge Methods
        //Data_Connectors.GaugeUpdate_UDP_WO_Menu gauge = new Data_Connectors.GaugeUpdate_UDP_WO_Menu();
        //Gauges.Altimeter.Altimeter alt = new Gauges.Altimeter.Altimeter();
        //Gauges.HSI.HSI hsi = new Gauges.HSI.HSI();
        //Gauges.ADI.ADI adi = new Gauges.ADI.ADI();
        //Gauges.Airspeed.AirSpeed speed = new Gauges.Airspeed.AirSpeed();
        ControlRight panel = new ControlRight();
        //Gauges.VSI.VSI vsi = new Gauges.VSI.VSI();
        //Gauges.Turn_Coordinator.TurnCoordinator tc = new Gauges.Turn_Coordinator.TurnCoordinator();
        //Gauges.Menus.Menu menu = new Gauges.Menus.Menu();
        //menu.setVisible(true);

        for(int i = 0; i < 5000; i++)
        {
            screen.setProgress(i);
            Thread.sleep(1);
        }
        screen.setScreenVisible(false);



		//panel.repaint();
   do
   	{

	// SET GAUGE VALUES !!!! /////////////////////////////

		try {
			int port = 9876;

			// Create a socket to listen on the port.
			DatagramSocket dsocket = new DatagramSocket(port);
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
				//System.out.println(packet.getLength());
				String[] parts = msg.split(",");

				for (int i = 0; i < packetSize; i++)
                {
                    newValues[i] = Double.parseDouble(parts[i]);
                }

                panel.setValues(newValues, packetSize);


				// Reset the length of the packet before reusing it.
				packet.setLength(buffer.length);
			}
		} catch (Exception e) {
			System.err.println(e);
		}

	// END OF SET GAUGE VALUES !!!! ////////////////////////

        } while(panel.isShowing()); // while(alt.isShowing());
        System.out.println("Broke!!");

    }
}