package Data_Connectors;

// JSimConnect Imports
import flightsim.simconnect.SimConnect;
import flightsim.simconnect.SimConnectConstants;
import flightsim.simconnect.SimConnectDataType;
import flightsim.simconnect.SimConnectPeriod;
import flightsim.simconnect.config.ConfigurationNotFoundException;
import flightsim.simconnect.recv.*;


// Pi4J Imports
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;



import java.io.IOException;


public class Gauge_Update_With_Menu_JSimConnect_Pi4J {


    /**
     * Boolean Flags
     */
    boolean retrySC;
    boolean run = true;

    /**
     * Float Variables Needed For Gauges
     */
    float vertSpeed;    // Vertical Speed
    float alt;          // Altitude
    float airSpeed;     // Airspeed

    float bankRad;      // Roll
    float bankDeg;

    float pitchRad;     // Pitch


    float headingRad;   // Compass Heading
    int counter = 0;

    int RPM;            // Engine RPM


    int mhzUP = 1;      // Event ID for going up by 1 MHZ
    int mhzDN = 2;      // Event ID for going down by 1 MHZ
    int khzUP = 3;      // Event ID for going up by 1 KHZ
    int khzDN = 4;      // Event ID for going down by 1 KHZ
    int swap = 5;       // Event ID for swapping active and standby frequencies


    /**
     * Gauge initializer's
     */
    Gauges.ADI.ADI_Picture adi;
    Gauges.Airspeed.AirSpeed_Picture speed;
    Gauges.RPM.RPM_Picture rpm;
    Gauges.COM_1.Radio_Freq_Display Radio;
    Gauges.HSI.HSI_Picture hsi;
    Gauges.Altimeter.Altimeter_Picture Alt;

    /**
     * Previous Encoder Pin States
     */
    int lastA;  // Encoder Pin value for last A value for MHZ encoder (Bigger Knob)
    int lastC;  // Encoder Pin value for last C value for KHZ encoder (Smaller Knob)


    /**
     * Simconnect Initializer
     */
    SimConnect sc;

    /**
     * Raspberry Pi Initializer's
     */
    final GpioController gpio;
    // MHZ ENCODER
    GpioPinDigitalInput pinA;
    GpioPinDigitalInput pinB;
    // KHZ ENCODER
    GpioPinDigitalInput pinC;
    GpioPinDigitalInput pinD;
    GpioPinDigitalInput pinE;


/**
 * Frequency Variables
 */
    private float Afreq;    // Active Frequency
    private float Sfreq;    // Standby Frequency


    public Gauge_Update_With_Menu_JSimConnect_Pi4J() throws Exception {
        adi = new Gauges.ADI.ADI_Picture();
        speed = new Gauges.Airspeed.AirSpeed_Picture();
        rpm = new Gauges.RPM.RPM_Picture();
        Radio = new Gauges.COM_1.Radio_Freq_Display();
        hsi = new Gauges.HSI.HSI_Picture();
        Alt = new Gauges.Altimeter.Altimeter_Picture();

        gpio = GpioFactory.getInstance();

        pinA = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29, "PinA", PinPullResistance.PULL_UP);
        pinB = gpio.provisionDigitalInputPin(RaspiPin.GPIO_23, "PinB", PinPullResistance.PULL_UP);
        pinC = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "PinC", PinPullResistance.PULL_UP);
        pinD = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "PinD", PinPullResistance.PULL_UP);
        pinE = gpio.provisionDigitalInputPin(RaspiPin.GPIO_25, "PinE", PinPullResistance.PULL_UP);


        do {

            try {
                sc = new SimConnect("Project Panel", "192.168.1.107", 48447);
                retrySC = false;
                counter = 0;
                SimConnectPeriod p = SimConnectPeriod.VISUAL_FRAME;


                sc.addToDataDefinition(1, "Vertical Speed", "Feet per second", SimConnectDataType.FLOAT32);
                sc.addToDataDefinition(1, "Indicated Altitude", "Feet", SimConnectDataType.FLOAT32);
                sc.addToDataDefinition(1, "Airspeed True", "Knots", SimConnectDataType.FLOAT32);
                sc.addToDataDefinition(1, "Plane Bank Degrees", "Radians", SimConnectDataType.FLOAT32);
                sc.addToDataDefinition(1, "Plane Pitch Degrees", "Radians", SimConnectDataType.FLOAT32);
                sc.addToDataDefinition(1, "PLANE HEADING DEGREES GYRO", "Radians", SimConnectDataType.FLOAT32);
                sc.addToDataDefinition(1, "GENERAL ENG RPM:1", "Rpm", SimConnectDataType.INT32);
                sc.addToDataDefinition(1, "COM ACTIVE FREQUENCY:1", null, SimConnectDataType.FLOAT32);
                sc.addToDataDefinition(1, "COM STANDBY FREQUENCY:1", null, SimConnectDataType.FLOAT32);


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


                sc.requestDataOnSimObject(1, 1, SimConnectConstants.OBJECT_ID_USER, p, SimConnectConstants.CLIENT_DATA_REQUEST_FLAG_DEFAULT, 0, 0, 0);

                DispatcherTask dt = new DispatcherTask(sc);

                dt.addOpenHandler(this::handleOpen);
                dt.addExceptionHandler(this::handleException);
                dt.addSimObjectDataHandler(this::handleSimObject);
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


        Gauge_Update_With_Menu_JSimConnect_Pi4J JSimConnect = new Gauge_Update_With_Menu_JSimConnect_Pi4J();
    }

    public void handleOpen(SimConnect sender, RecvOpen e) {
        System.out.println("Connected to " + e.getApplicationName());
    }

    public void handleQuit(SimConnect sender, RecvQuit e) {
        retrySC = true;
        System.out.println("SimConnect Connection Closed!");
    }

    public void handleException(SimConnect sender, RecvException e) {
        System.out.println("Exception (" + e.getException() + ") packet " + e.getSendID());
    }

    public void handleSimObject(SimConnect sender, RecvSimObjectData e) {
        vertSpeed = e.getDataFloat32();
        alt = e.getDataFloat32();
        airSpeed = (e.getDataFloat32()) * 1.15f;
        bankRad = e.getDataFloat32();
        pitchRad = e.getDataFloat32();
        headingRad = e.getDataFloat32();
        RPM = e.getDataInt32();
        Afreq = e.getDataFloat32();
        Sfreq = e.getDataFloat32();


        //bankDeg = (float) (bankRad * 180 / Math.PI);
        //pitchDeg = (float) (pitchRad * 180 / Math.PI);

        double pitch = Double.parseDouble(Float.toString(-pitchRad));
        double roll = Double.parseDouble(Float.toString(bankRad));
        double AS = Double.parseDouble(Float.toString(airSpeed));
        double head = Double.parseDouble(Float.toString(headingRad));
        double A = Double.parseDouble(Float.toString(alt));

        adi.setPitchBankValues(pitch, roll);
        adi.repaint();

        speed.setAS(AS);
        speed.repaint();

        rpm.setRPM(RPM);
        rpm.repaint();

        Radio.setAct(Afreq);
        Radio.setStdby(Sfreq);
        Radio.repaint();

        hsi.setHeading(head);
        hsi.repaint();

        Alt.setAlt(A);
        Alt.repaint();


        //System.out.println(freq);
    }

    public class MHZListener implements GpioPinListenerDigital
    {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e)
        {
            int a = pinA.getState().getValue();
            int b = pinB.getState().getValue();

            if(lastA != a)
            {
                if(b == a) {
                    //val--;
                    try {
                        sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER, mhzDN, 0, SimConnectConstants.OBJECT_ID_USER, SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY);
                    } catch (IOException I)
                    {

                    }
                    //System.out.println(val);
                }
                else {
                    //val++;
                    try {
                        sc.transmitClientEvent(SimConnectConstants.OBJECT_ID_USER, mhzUP, 0, SimConnectConstants.OBJECT_ID_USER, SimConnectConstants.EVENT_FLAG_GROUPID_IS_PRIORITY);
                    } catch (IOException I)
                    {

                    }
                    //System.out.println(val);
                }
                lastA = a;
            }
        }
    }

    public class KHZListener implements GpioPinListenerDigital
    {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e)
        {
            int c = pinC.getState().getValue();
            int d = pinD.getState().getValue();

            if(lastC != c)
            {
                if(d == c) {
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
                lastC = c;
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

            if(e.getState().isLow())
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


