package Demos;

import Gauges.ADI.ADI;
import Gauges.Airspeed.AirSpeed;
import Gauges.Altimeter.Altimeter;
import Gauges.Controls.Control_NOTUSED;
import Gauges.HSI.HSI;
import Gauges.Turn_Coordinator.TurnCoordinator;
import Gauges.VSI.VSI;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;


/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class MyFrame extends JFrame
{
static Altimeter alt = null;
static HSI hsi = null;
static ADI adi = null;
static AirSpeed speed = null;
static Control_NOTUSED panel = null;
static VSI vsi = null;
static TurnCoordinator tc = null;
    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
////////////////////////////////////////////////////////////////////////////////////////////////////////
        double offset = 0;


        // Gauges.ADI.ADI Variables
        double pitch = 0;
        double bank = 0;
        String timeStamp = new SimpleDateFormat("MM dd yyyy").format(new Date());


        int delay = 5;
        double maxADI_P = 90;
        double maxADI_R = 180;
        double maxAlt = 20000;
        double maxAs = 60;
        double maxVs = 20;
        double minH = 0;
        double maxH = 360;
        double maxRollRate = 15;
///////////////////////////////////////////////////////////////////////////////////////////////////////
        // Create Instance of Gauge Classes and Run
        alt = new Altimeter();    // Instance of Gauges.Altimeter.Altimeter() inside of Gauges.Altimeter.Altimeter Class File
        hsi = new HSI();                // Instance of Gauges.HSI.HSI() inside of Gauges.HSI.HSI Class File (Compass)
        adi = new ADI();                // Instance of Gauges.ADI.ADI (Artificial Horizon)
        //Gauges.RPM.RPM rpm = new Gauges.RPM.RPM();				// Instance of Gauges.RPM.RPM (Tachometer)
        speed = new AirSpeed();
        panel = new Control_NOTUSED();
        vsi = new VSI();
        tc = new TurnCoordinator();
        //Gauges.Menus.Menu menu = new Gauges.Menus.Menu();



        // Initialize ALT at Zero
        alt.setAlt(0);
        alt.setAltSet(0);
        alt.repaint();  // Calls

        // Initialize Gauges.HSI.HSI at Zero
        //hsi.setHeading(0);
        hsi.setLargeA(0);
        hsi.repaint();

        // Initialize Gauges.ADI.ADI at Zero
        adi.setPitchBankValues(0, 0);
        adi.setGSAngle(0);
        adi.repaint();

        // Initialize Tachometer at Zero
        //rpm.setRPM(0);
        //rpm.setRPMSet(0);
        //rpm.repaint();

        // Initialize Airspeed at Zero
        speed.setAS(0);
        //speed.setASSet(0);
        speed.repaint();

        vsi.setVSI(0);
        vsi.repaint();

        tc.setTC(0);
        tc.setTCSet(0);
        tc.repaint();


        // Show buttonPanel
        panel.repaint();

        JOptionPane.showMessageDialog(null, "Welcome To Kyle Monroe's \n Java GroundStation Panel Project. \n Created " + timeStamp);
        do {


            // SET GAUGE VALUES !!!! /////////////////////////////
            //TEST ALTIMETER
            for (double a = 0; a < maxAlt; a++) {
                // Set Gauges.Altimeter.Altimeter Values

                alt.setAlt(a);
                alt.setAltSet(a);
                alt.repaint();
                Thread.sleep(delay-3);
            }
            for (double a = maxAlt; a > -1; a--) {

                alt.setAlt(a);
                alt.setAltSet(a);
                alt.repaint();
                Thread.sleep(delay-3);
            }

            // TEST Gauges.VSI.VSI
            for (int i = 0; i < 21; i++)
            {
              vsi.setVSI(i);
              vsi.repaint();
              Thread.sleep(delay+10);
            }
            for (int i = 20; i > -21; i--)
            {
                vsi.setVSI(i);
                vsi.repaint();
                Thread.sleep(delay+10);
            }
            for (int i = -20; i < 1;i++)
            {
                vsi.setVSI(i);
                vsi.repaint();
                Thread.sleep(delay+10);
            }

            // TEST Gauges.ADI.ADI
            // TEST Gauges.ADI.ADI PITCH
            for (double c = 0; c < maxADI_P; c++) {
                // Set Gauges.ADI.ADI Values
                double k = Math.toRadians(c);
                adi.setPitchBankValues(k, bank);
                adi.setGSAngle(k);
                adi.repaint();
                Thread.sleep(delay+10);

            }
            for (double c = maxADI_P; c > -maxADI_P; c--) {
                // Set Gauges.ADI.ADI Values
                double k = Math.toRadians(c);
                adi.setPitchBankValues(k, bank);
                adi.setGSAngle(k);
                adi.repaint();
                Thread.sleep(delay+10);

            }
            for (double c = -maxADI_P; c < 1; c++) {
                // Set Gauges.ADI.ADI Values
                double f = Math.toRadians(c);
                adi.setPitchBankValues(f, bank);
                adi.setGSAngle(f);
                adi.repaint();
                Thread.sleep(delay+10);

            }
            // TEST Gauges.ADI.ADI BANK
            for (double c = 0; c < maxADI_R; c++) {
                // Set Gauges.ADI.ADI Values
                double e = Math.toRadians(c);
                adi.setPitchBankValues(pitch, e);
                adi.setGSAngle(e);
                adi.repaint();
                Thread.sleep(delay+10);

            }
            for (double c = maxADI_R; c > -maxADI_R; c--) {
                // Set Gauges.ADI.ADI Values
                double r = Math.toRadians(c);
                adi.setPitchBankValues(pitch, r);
                adi.setGSAngle(r);
                adi.repaint();
                Thread.sleep(delay+10);

            }
            for (double c = -maxADI_R; c < 1; c++) {
                // Set Gauges.ADI.ADI Values
                double y = Math.toRadians(c);
                adi.setPitchBankValues(pitch, y);
                adi.setGSAngle(y);
                adi.repaint();
                Thread.sleep(delay+10);

            }

            // TEST Gauges.HSI.HSI
            hsi.setHeading(0 + offset);
            hsi.setLargeA(0);
            for (double b = 0; b > -maxH; b--) {
                // Set Heading Values
                double j = Math.toRadians(b);

                double k = -j;
                hsi.setHeading(j);

                hsi.setSmallA(k);   //////////////////////////////////////////////////////
                hsi.repaint();
                Thread.sleep(delay+10);



            }
            for (double b = -maxH; b < 1; b++) {
                // Set Heading Values
                double j = Math.toRadians(b);

                double k = -j;
                hsi.setHeading(j);

                hsi.setSmallA(k);
                hsi.repaint();
                Thread.sleep(delay+10);



            }

            // TEST AIRSPEED
            for(int i = 0 ; i < 61 ; i++)
            {
                speed.setAS(i);
                speed.repaint();
                Thread.sleep(delay+10);
            }
            for(int i = 60; i >-1; i--)
            {
                speed.setAS(i);
                speed.repaint();
                Thread.sleep(delay+10);
            }

            // TEST TURN COORDINATOR
            for (double t = 0; t < maxRollRate; t++)
            {
                tc.setTC(t);    //  Roll Rate
                tc.setTCSet(t/4.2); // Yaw Rate
                tc.repaint();
                Thread.sleep(delay+10);
            }
            for(double t = maxRollRate; t > -maxRollRate; t--)
            {
                tc.setTC(t);
                tc.setTCSet(t/4.2);
                tc.repaint();
                Thread.sleep(delay+10);
            }

            for (double t = -maxRollRate; t < 1; t++)
            {
                tc.setTC(t);
                tc.setTCSet(t/4.2);
                tc.repaint();
                Thread.sleep(delay+10);
            }


            // END OF SET GAUGE VALUES !!!! ////////////////////////


        } while (adi.isShowing()); // while(alt.isShowing());



    }
/*
    public Gauges.Altimeter.Altimeter getALT()
    {
        return alt;
    }
    */
}
