package Demos;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class UDPReceive {
    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param args
     */
    public static void main(String args[])
    {
        double values[] = {0,0,0,0,0,0,0,0};   // must have same # of members as the # of datatypes being sent

        int packetSize = 8;
        try {
            int port = 9876;

            // Create a socket to listen on the port.
            DatagramSocket dsocket = new DatagramSocket(port);

            // Create a buffer to read data-grams into. If a
            // packet is larger than this buffer, the
            // excess will simply be discarded!
            byte[] buffer = new byte[2048];

            // Create a packet to receive data into the buffer
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // Now loop forever, waiting to receive packets and printing them.
            while (true) {
                // Wait to receive a datagram
                dsocket.receive(packet);

                // Convert the contents to a string, and display them
                String msg = new String(buffer, 0, packet.getLength());
                String[] parts = msg.split(",");
                // To Print Out Data in their String Format
                /*
                for (int i = 0; i < 7; i++) {
                    System.out.print(parts[i]);
                    System.out.print(" , ");
                }
                System.out.println(parts[7]);
                */
                for (int i = 0; i < packetSize; i++)
                {
                    values[i] = Double.parseDouble(parts[i]);
                }
                //System.out.println(packet.getAddress().getHostName() + ": " + msg);
                //System.out.println("Pitch: " + values[0] + " Roll: " + values[1]);
                for (int j=0; j < packetSize; j++) {
                    System.out.print(values[j]);
                    if ( j < 7)
                    {
                        System.out.print(" , ");
                    }
                    else
                    {
                        System.out.println();
                    }
                }
                // Reset the length of the packet before reusing it.

                packet.setLength(buffer.length);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}