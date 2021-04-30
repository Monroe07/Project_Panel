package Demos;


import Gauges.Airspeed.AirSpeed;
import Gauges.Airspeed.AirSpeed_Picture;


import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class Test_Single_Gauge_Value extends JFrame
{


static AirSpeed_Picture speed = null;

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
////////////////////////////////////////////////////////////////////////////////////////////////////////



        // Gauges.ADI.ADI Variables

        String timeStamp = new SimpleDateFormat("MM dd yyyy").format(new Date());


        int delay = 5;

///////////////////////////////////////////////////////////////////////////////////////////////////////
        // Create Instance of Gauge Classes and Run


        speed = new Gauges.Airspeed.AirSpeed_Picture();

        //Gauges.Menus.Menu menu = new Gauges.Menus.Menu();



        // Initialize ALT at Zero


        // Initialize Gauges.HSI.HSI at Zero
        //hsi.setHeading(0);

        // Initialize Airspeed at Zero
        speed.setAS(0);
        //speed.setASSet(0);
        speed.repaint();



        JOptionPane.showMessageDialog(null, "Welcome To Kyle Monroe's \n Java GroundStation Panel Project. \n Created " + timeStamp);
        do {


            // SET GAUGE VALUES !!!! /////////////////////////////
            // TEST AIRSPEED
            for(int i = 0 ; i < 160 ; i++)
            {
                speed.setAS(i);
                speed.repaint();
                Thread.sleep(delay+10);
            }
            for(int i = 160; i >-1; i--)
            {
                speed.setAS(i);
                speed.repaint();
                Thread.sleep(delay+10);
            }




            // END OF SET GAUGE VALUES !!!! ////////////////////////


        } while (speed.isShowing()); // while(alt.isShowing());



    }
/*
    public Gauges.Altimeter.Altimeter getALT()
    {
        return alt;
    }
    */
}
