package Demos;

// JSimConnect Imports

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import flightsim.simconnect.SimConnect;
import flightsim.simconnect.SimConnectConstants;
import flightsim.simconnect.config.ConfigurationNotFoundException;
import flightsim.simconnect.recv.*;
import com.pi4j.util.Console;
import com.pi4j.util.ConsoleColor;

import java.io.IOException;

// Pi4J Imports


public class JSimConnect_Pi4J {

    boolean retrySC = true;

    boolean run = true;

    int counter = 0;


    int mhzUP = 1;
    int mhzDN = 2;
    int khzUP = 3;
    int khzDN = 4;
    int swap = 5;



    int lastA;
    int val = 0;


    SimConnect sc;

    final GpioController gpio;
    final Console console;
    // MHZ ENCODER
    GpioPinDigitalInput pinA;
    GpioPinDigitalInput pinB;
    // KHZ ENCODER
    GpioPinDigitalInput pinC;
    GpioPinDigitalInput pinD;
    GpioPinDigitalInput pinE;






    public JSimConnect_Pi4J() throws Exception {


        gpio = GpioFactory.getInstance();
        console = new Console();
        console.title("SimConnect With Pi4J");
        console.promptForExit();

        pinA = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "PinA", PinPullResistance.PULL_UP);
        pinB = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "PinB", PinPullResistance.PULL_UP);
        pinC = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29, "PinC", PinPullResistance.PULL_UP);
        pinD = gpio.provisionDigitalInputPin(RaspiPin.GPIO_23, "PinD", PinPullResistance.PULL_UP);
        pinE = gpio.provisionDigitalInputPin(RaspiPin.GPIO_25, "PinE", PinPullResistance.PULL_UP);



        do {

            try {
                sc = new SimConnect("Project Panel", "192.168.1.107", 48447);

                retrySC = false;
                counter = 0;



                sc.mapClientEventToSimEvent(mhzUP, "COM_RADIO_WHOLE_INC");
                sc.mapClientEventToSimEvent(mhzDN, "COM_RADIO_WHOLE_DEC");
                sc.mapClientEventToSimEvent(khzUP, "COM_RADIO_FRACT_INC");
                sc.mapClientEventToSimEvent(khzDN, "COM_RADIO_FRACT_DEC");
                sc.mapClientEventToSimEvent(swap, "COM_STBY_RADIO_SWAP");

                /*
                 To Send Commands to Increase/Decrease MHZ/KHZ, USE Following

                 INC MHZ:
                 sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER,mhzUP, 0, SimConnectConstants.OBJECT_ID_USER,SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY );

                 DEC MHZ:
                 sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER,mhzDN, 0, SimConnectConstants.OBJECT_ID_USER,SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY );

                 INC KHZ:
                 sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER,khzUP, 0, SimConnectConstants.OBJECT_ID_USER,SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY );

                 DEC KHZ:
                 sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER,khzDN, 0, SimConnectConstants.OBJECT_ID_USER,SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY );

                 SWAP ACTIVE and STANDBY:
                 sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER, swap, 0, SimConnectConstants.OBJECT_ID_USER, SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY);
                 */



                DispatcherTask dt = new DispatcherTask(sc);

                dt.addOpenHandler(this::handleOpen);
                dt.addExceptionHandler(this::handleException);

                dt.addQuitHandler(this::handleQuit);

                pinA.addListener(new MHZListener());
                pinB.addListener(new MHZListener());
                pinC.addListener(new KHZListener());
                pinD.addListener(new KHZListener());
                pinE.addListener(new SWAPListener());



                while (run) {

                    sc.callDispatch(dt);

                }


            } catch (IOException e) {
                counter++;
                retrySC = true;
                System.out.println("Waiting..." + counter);
            }
        } while (retrySC);
    }

    public static void main(String[] args) throws ConfigurationNotFoundException, IOException, Exception {


        JSimConnect_Pi4J JSimConnect = new JSimConnect_Pi4J();
    }

    public void handleOpen(SimConnect sender, RecvOpen e) {
        console.println("Connected to " + e.getApplicationName());
    }

    public void handleQuit(SimConnect sender, RecvQuit e) {
        retrySC = true;
        console.println("SimConnect Connection Closed!");
    }

    public void handleException(SimConnect sender, RecvException e) {
        console.println("Exception (" + e.getException() + ") packet " + e.getSendID());
    }



    public class MHZListener implements GpioPinListenerDigital
    {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e)
        {
            int a = pinA.getState().getValue();
            int b = pinB.getState().getValue();

            //System.out.println("Pin A : " + a + "\t" + "Pin B: " + b);

            if(lastA != a)
            {
                if(b == a) {
                    val--;
                    try {
                        sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER, mhzDN, 0, SimConnectConstants.OBJECT_ID_USER, SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY);
                    } catch (IOException I)
                    {

                    }
                    //System.out.println(val);
                }
                else {
                    val++;
                    try {
                        sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER, mhzUP, 0, SimConnectConstants.OBJECT_ID_USER, SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY);
                    } catch (IOException I)
                    {

                    }
                    //System.out.println(val);
                }
                lastA = a;
            }
            else
            {

            }
        }
    }

    public class KHZListener implements GpioPinListenerDigital
    {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e)
        {
            int a = pinC.getState().getValue();
            int b = pinD.getState().getValue();



            if(lastA != a)
            {
                if(b == a) {
                    //val--;
                    try
                    {
                        sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER, khzDN, 0, SimConnectConstants.OBJECT_ID_USER, SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY);
                    }
                    catch (IOException I)
                    {

                    }
                    //System.out.println(val);
                }
                else {
                    //val++;
                    try
                    {
                        sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER, khzUP, 0, SimConnectConstants.OBJECT_ID_USER, SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY);
                    }
                    catch (IOException I)
                    {

                    }
                    //System.out.println(val);
                }
                lastA = a;
            }
        }
    }
    /*
        WILL NEED TO BE SETUP TO LISTEN FOR THE RISING EDGE OF BUTTON PRESS INSTEAD OF ENCODER

     */
    public class SWAPListener implements GpioPinListenerDigital
    {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e)
        {
            if(e.getState().getValue() == 1)
            {
                try
                {
                    sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER, swap, 0, SimConnectConstants.OBJECT_ID_USER, SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY);
                }
                catch (IOException I)
                {

                }
            }



        }
    }

}


