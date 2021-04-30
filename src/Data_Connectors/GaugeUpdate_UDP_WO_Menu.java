package Data_Connectors;

import Gauges.ADI.ADI;
import Gauges.Airspeed.AirSpeed;
import Gauges.Altimeter.Altimeter;
import Gauges.Controls.Control_NOTUSED;
import Gauges.HSI.HSI;
import Gauges.Turn_Coordinator.TurnCoordinator;
import Gauges.VSI.VSI;

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
public class GaugeUpdate_UDP_WO_Menu extends JFrame
{
    // Place holder Values for the Center (X,Y) of the window
    private int cx;
    private int cy;
    //Gauges.Menus.Menu menu;
    // Create new Color for use
    Color chromeYellow = new Color(255, 167, 0);
    // Variables to hold the height and width of the frame
    int W = 80;
    int H = 800;

        GaugeUpdate_UDP_WO_Menu() throws Exception
        {

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
////////////////////////////////////////////////////////////////////////////////////////////////////////
        double newValues[] = {0,0,0,0,0,0,0,0};   // must have same # of members as the # of data types being sent
        double currentValues[] = {0,0,0,0,0,0,0,0};

        int packetSize = 8;


		//int delay = 25;
///////////////////////////////////////////////////////////////////////////////////////////////////////
    	// Create Instance of Gauge Methods
        //Data_Connectors.GaugeUpdate_UDP_WO_Menu gauge = new Data_Connectors.GaugeUpdate_UDP_WO_Menu();
        Altimeter alt = new Altimeter();
        HSI hsi = new HSI();
        ADI adi = new ADI();
        AirSpeed speed = new AirSpeed();
        Control_NOTUSED panel = new Control_NOTUSED();
        VSI vsi = new VSI();
        TurnCoordinator tc = new TurnCoordinator();


        // Initialize ALT at Zero
		alt.setAlt(0);
		alt.setAltSet(0);
		alt.repaint();

		// Initialize Gauges.HSI.HSI at Zero
		//hsi.setHeading(0);
		hsi.setLargeA(0);
		hsi.repaint();

		// Initialize Gauges.ADI.ADI at Zero
		adi.setPitchBankValues(0, 0);
		adi.setGSAngle(0);
		adi.repaint();

		speed.setAS(0);
		speed.setASSet(0);
		speed.repaint();

        vsi.setVSI(0);
        vsi.repaint();

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


				// Set Altitude with oldValues[3]
				if (newValues[3] != currentValues[3])
                {
                    currentValues[3] = newValues[3];
                    alt.setAlt(newValues[3]);
                    alt.repaint();
                    currentValues[3] = newValues[3];
                }
                else
                {
                    // Do not Refresh Gauges.Altimeter.Altimeter
                }
                // Check if Gauges.HSI.HSI Values are new
				if (newValues[6] != currentValues[6]) {
                    // Set Heading with values [6]
                    double j = -(Math.toRadians(newValues[6]));
                    double k = -j;

                    hsi.setHeading(j);
                    hsi.setSmallA(k);
                    hsi.repaint();
                    currentValues[6] = newValues[6];
                }
                else
                {
                    // Do Not Refresh Gauges.HSI.HSI
                }
                if (newValues[2] != currentValues[2] || newValues[1] != currentValues[1]) {
                    // Set Pitch with values[2] and Bank with values[1]
                    double p = Math.toRadians(newValues[2]);
                    double b = -1 * (Math.toRadians(newValues[1]));
                    adi.setPitchBankValues(p, b);
                    //adi.setGSAngle(l);
                    adi.repaint();
                    currentValues[1] = newValues[1];
                    currentValues[2] = newValues[2];
                }
                else
                {
                    // Do Not Refresh Artifical Horizon
                }
				if (newValues[0] != currentValues[0]) {
                    speed.setAS(newValues[0]);
                    //speed.setASSet(values[0]);
                    speed.repaint();
                    currentValues[0] = newValues[0];
                }
                else
                {
                    // Do Not Refresh Airspeed
                }

                if (newValues[7] != currentValues[7]) {
                    vsi.setVSI(newValues[7]);
                    vsi.repaint();
                    currentValues[7] = newValues[7];
                }
                else
                {
                    // Do Not Refresh Gauges.VSI.VSI
                }
				//System.out.println(values[0]);

                // Set Turn Coordinater Roll Rate with oldValues[4]
                if (newValues[4] != currentValues[4])
                {
                    currentValues[4] = newValues[4];
                    tc.setTC(newValues[4]/1.5);
                    tc.repaint();
                    currentValues[4] = newValues[4];
                }
                else
                {
                    // Do not Refresh Turn Coordinator
                }

                // Set Turn Coordinater Yaw Rate with oldValues[5]
                if (newValues[5] != currentValues[5])
                {
                    currentValues[5] = newValues[5];
                    tc.setTCSet(newValues[5]/1.5);
                    tc.repaint();
                    currentValues[5] = newValues[5];
                }
                else
                {
                    // Do not Refresh Turn Coordinator
                }

				// Reset the length of the packet before reusing it.
				packet.setLength(buffer.length);
			}
		} catch (Exception e) {
			System.err.println(e);
		}

	// END OF SET GAUGE VALUES !!!! ////////////////////////

        } while(adi.isShowing()); // while(alt.isShowing());
        System.out.println("Breaked");

    }
}