package Data_Connectors;



import flightsim.simconnect.*;
import flightsim.simconnect.config.ConfigurationNotFoundException;
import flightsim.simconnect.recv.*;

import java.io.IOException;
import java.net.URISyntaxException;





public class Gauge_Update_With_Menu_JSimConnect {

    boolean retrySC = true;

    boolean run = true;
    float vertSpeed;
    float alt;
    float airSpeed;

    float bankRad;
    float bankDeg;

    float pitchRad;
    //float pitchDeg;

    float headingRad;
    int counter = 0;

    int RPM;
    int mhzUP = 1;
    int mhzDN = 2;
    int khzUP = 3;
    int khzDN = 4;
    int swap = 5;
    Gauges.ADI.ADI_Picture adi;
    Gauges.Airspeed.AirSpeed_Picture speed;
    Gauges.RPM.RPM_Picture rpm;
    Gauges.COM_1.Radio_Freq_Display Radio;
    Gauges.HSI.HSI_Picture hsi;
    Gauges.Altimeter.Altimeter_Picture Alt;


    SimConnect sc;
    private float Afreq;
    private float Sfreq;


    public Gauge_Update_With_Menu_JSimConnect() throws Exception {
        adi = new Gauges.ADI.ADI_Picture();
        speed = new Gauges.Airspeed.AirSpeed_Picture();
        rpm = new Gauges.RPM.RPM_Picture();
        Radio = new Gauges.COM_1.Radio_Freq_Display();
        hsi = new Gauges.HSI.HSI_Picture();
        Alt = new Gauges.Altimeter.Altimeter_Picture();

        do {

            try {
                sc = new SimConnect("GetInfo", "192.168.1.107", 48447);
                retrySC = false;
                counter = 0;
                SimConnectPeriod p = SimConnectPeriod.SIM_FRAME;


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


                sc.requestDataOnSimObject(1, 1, SimConnectConstants.OBJECT_ID_USER, p, SimConnectConstants.DATA_REQUEST_FLAG_CHANGED, 0, 0, 0);

                DispatcherTask dt = new DispatcherTask(sc);

                dt.addOpenHandler(this::handleOpen);
                dt.addExceptionHandler(this::handleException);
                dt.addSimObjectDataHandler(this::handleSimObject);
                dt.addQuitHandler(this::handleQuit);


                while (run) {

                    sc.callDispatch(dt);
                    for(int i = 0; i < 5; i++)
                    {
                        // Gauge set Values Airspeed
                        for(int j = 0; i <161; i++)
                        {
                            speed.setAS(j);
                            speed.repaint();
                            Thread.sleep(20);
                        }
                        for(int j = 160; i >-1; i--)
                        {
                            speed.setAS(j);
                            speed.repaint();
                            Thread.sleep(20);
                        }

                    }

                }


            } catch (IOException e) {
                counter++;
                retrySC = true;
                System.out.println("Waiting..." + counter);
            }
        } while (retrySC);
    }

    public static void main(String[] args) throws ConfigurationNotFoundException, IOException, Exception {


        Gauge_Update_With_Menu_JSimConnect JSimConnect = new Gauge_Update_With_Menu_JSimConnect();
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


}


