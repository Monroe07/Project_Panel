package Data_Connectors;

import flightsim.simconnect.*;
import flightsim.simconnect.config.ConfigurationNotFoundException;
import flightsim.simconnect.data.LatLonAlt;
import flightsim.simconnect.recv.*;

import java.io.IOException;
import java.util.ArrayList;

public class Radar_Update_JSimConnect_V2 implements OpenHandler, SimObjectDataTypeHandler, FacilitiesListHandler {
    boolean retry = true;
    int viewSize = 534;
    int viewDist = 15;
    int windowH = 800;
    int windowW = 480;
    Gauges.Radar.DistanceDirectionRhumb ddr;
    Gauges.Radar.Radar_Picture radar;
    Gauges.Radar.AirportList AIRPORT;

    final int frameID = 1;
    final int refresh = 2;

    LatLonAlt userPosition;
    double userHeading;
    Object[] Plane;
    boolean airplaneFlag = false;
    long start = 0;
    long end = 0;

    public Radar_Update_JSimConnect_V2() throws IOException, ConfigurationNotFoundException, InterruptedException, Exception {

        System.out.println("Radar_Update_JSimConnect_V2");


        ddr = new Gauges.Radar.DistanceDirectionRhumb();
        ddr.setViewSize(viewSize);
        System.out.println("ddr ViewSize: " + viewSize);
        ddr.setViewDist(viewDist);
        System.out.println("ddr ViewDist: " + viewDist);

        radar = new Gauges.Radar.Radar_Picture(viewDist, viewSize, windowH, windowW);
        radar.setViewSize(viewSize);
        System.out.println("radar ViewSize: " + viewSize);
        radar.setViewDist(viewDist);
        System.out.println("radar ViewSize: " + viewSize);

        AIRPORT = new Gauges.Radar.AirportList();

        while (retry) {
            try {





                SimConnect sc = new SimConnect("RADAR",  "192.168.1.107", 48447);
                SimConnectPeriod p = SimConnectPeriod.VISUAL_FRAME;
                retry = false;


                /* Set up the data definition, but do not yet do anything with it  */

                // Add Latitude and Longitude of Object
                sc.addToDataDefinition(DATA_DEFINE_ID.DEFINITION_1, "STRUCT LATLONALT", null, SimConnectDataType.LATLONALT);

                // Add Title of Object
                //sc.addToDataDefinition(DATA_DEFINE_ID.DEFINITION_1, "Title", null, SimConnectDataType.STRING256);

                // ATC ID of Object
                sc.addToDataDefinition(DATA_DEFINE_ID.DEFINITION_1, "ATC ID", null, SimConnectDataType.STRING32);

                // Add Heading of Object (Only used for User Object
                sc.addToDataDefinition(DATA_DEFINE_ID.DEFINITION_1, "PLANE HEADING DEGREES TRUE", "Radians", SimConnectDataType.FLOAT32);



                /*  Subscribe to events */
                // Request an event periodically when simulation is running
                sc.subscribeToSystemEvent(frameID, "frame");
                sc.subscribeToSystemEvent(refresh, "6Hz");
                sc.subscribeToFacilities(FacilityListType.AIRPORT, EVENT_ID.EVENT_AIRPORT);

                /* Setup Dispatcher */
                // dispatcher
                DispatcherTask dt = new DispatcherTask(sc);



                /* Add handlers to dispatcher */
                dt.addOpenHandler(this::handleOpen);
                dt.addQuitHandler(this::handleQuit);
                dt.addEventFrameHandler(this::handleEventFrame);
                dt.addEventHandler(this::handleEvent);
                dt.addSimObjectDataTypeHandler(this::handleSimObjectType);
                dt.addFacilitiesListHandler(this);
                /* Create thread for dispatcher and start */
                //dt.createThread().start();
                while (true) {
                    sc.callDispatch(dt);
                }

            } catch (Exception e) {
                // Connection Failed, wait a short time and try again
                //System.out.println(e);
                Thread.sleep(500);
                System.out.println("Waiting...");

            } // End of Try Catch block
        }
    }


    /**
     * MAIN LOOP
     *
     * @param args
     * @Name main
     * @Purpose Main Method that is executed
     */
    public static void main(String[] args) {
        try {
            Radar_Update_JSimConnect_V2 sample = new Radar_Update_JSimConnect_V2();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Triggered when Simconnect is Connected
     *
     * @param sender
     * @param e
     * @Name handleOpen
     * @Purpose Run when the Simulator is connected
     */
    public void handleOpen(SimConnect sender, RecvOpen e) {
        System.out.println("Connected to : " + e.getApplicationName() + " "
                + e.getApplicationVersionMajor() + "."
                + e.getApplicationVersionMinor());



    }

    /**
     * Triggered when SimConnect is Disconnected
     *
     * @param sender
     * @param e
     * @Name handleQuit
     * @Purpose Run when disconnected from the Simulation
     */
    public void handleQuit(SimConnect sender, RecvQuit e) {
        System.out.println("Disconnected from Simulator");

    }

    /**
     * Triggered every Sim Frame
     *
     * @param sender
     * @param e
     * @Name handleEventFrame
     * @Purpose Run every Simulator Frame
     */
    int i = 0;
    public void handleEvent(SimConnect sender, RecvEvent e)
    {

        /*
        if(e.getEventID() == refresh)
        {
            try {
                if(userPosition != null)
                {

                    //System.out.println("GOT USER DATA");
                    ArrayList<Object[]> ap = AIRPORT.getNearbyAirports(userPosition.latitude, userPosition.longitude, viewDist, userHeading);
                    radar.addAirports(ap);

                }
            } catch(Exception eV)
            {

            }
        }
        */
    }
boolean paint = true;
    public void handleEventFrame(SimConnect sender, RecvEventFrame e) {

        // Now the sim is running, request information on the user aircraft
        if (e.getEventID() == frameID) {
            try {
                sender.requestDataOnSimObjectType(DATA_REQUEST_ID.REQUEST_1, DATA_DEFINE_ID.DEFINITION_1, (viewDist*1609), SimObjectType.AIRCRAFT);
                if(paint)
                {
                    if(userPosition != null)
                    {

                        //System.out.println("GOT USER DATA");

                        ArrayList<Object[]> ap = AIRPORT.getNearbyAirports(userPosition.latitude, userPosition.longitude, viewDist, userHeading);
                        //radar.clearAirportList();
                        radar.addAirports(ap);

                    }

                }
                paint = !paint;

                // Set Radius

                // if User Lat/Lon is present, set User Lat Lon




            } catch (IOException IOe) {
            }

        }
    }


    /**
     *  Triggered when Plane Object Data is Received
     * @param sender
     * @param e
     */
    public void handleSimObjectType(SimConnect sender, RecvSimObjectDataByType e) {
        //System.out.println("In: handleSimObjectType");
        // Clear Aircraft List
        if (airplaneFlag == true) {
            radar.clearPlaneList();

            start = System.currentTimeMillis();
        }
        airplaneFlag = false;
        LatLonAlt position = e.getLatLonAlt();
        //String Title = e.getDataString32();
        String atcID = e.getDataString32();
        float heading = e.getDataFloat32();

        // if User Plane
        if (e.getObjectID() == 1) {
            userPosition = position;
            userHeading = heading;
            ddr.setUserPosition(userPosition.latitude, userPosition.longitude);
            ddr.setUserHeading((double) heading);
            // DO NOT REPAINT !!
        }
        // if Plane is not the user plane, and I have the user plane already
        if (e.getObjectID() != 1 && userPosition != null) {
            // Create Object with needed data for Radar
            Plane = new Object[4];
            // ATC ID
            Plane[0] = atcID;
            // ALTITUDE
            Plane[1] = truncate(position.altitude * 3.28084);

            // Calculate X and Y Coordinate
            ddr.setObjectPosition(position.latitude, position.longitude, position.altitude);
            ddr.calculateScreenCoordinates();
            // X-COORDINATE
            Plane[2] = ddr.getX();
            // Y-COORDINATE
            Plane[3] = -ddr.getY();
            // Send data to Radar
            radar.addPlane(Plane);

        }
        // If Object is the last from the request
        if (e.getEntryNumber() == e.getOutOf()) {
            // Send array list of airports to radar
            radar.repaint();
            end = System.currentTimeMillis();
            //System.out.println("Duration for plane retrieval: " + (end - start) + "ms");
            // Flag to clear the Airplane List next time Plane Data is Received
            airplaneFlag = true;
        }
    } // End of handleSimObjectType




    public void handleRefresh()
    {
        if(userPosition != null)
        {

            //System.out.println("GOT USER DATA");
            ArrayList<Object[]> ap = AIRPORT.getNearbyAirports(userPosition.latitude, userPosition.longitude, viewDist, userHeading);
            radar.addAirports(ap);

        }
    }
    /**
     * Triggered when a list of airports nearby is received
     * WILL NOT BE USING THE DATA
     * WILL BE USING TO TRIGGER A SEARCH OF A CSV DATABASE FOR AIRPORTS INSTEAD
     * @param sender
     * @param list
     */
    public void handleAirportList(SimConnect sender, RecvAirportList list) {

        // Get List of nearby Airports with their X and Y Coordinates in the form of an array list



        // Send the list to the radar

        // Will be repainted when Airplane data is finished


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //// NOT USED BUT MUST BE HERE /////////////////////////////////////////////////////////////////////////////////////

    public void handleNDBList(SimConnect sender, RecvNDBList list) {
    }

    public void handleVORList(SimConnect sender, RecvVORList list) {
    }

    public void handleWaypointList(SimConnect sender, RecvWaypointList list) {
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Used as a helper to truncate values to 2 decimal places
     *
     * @param val
     * @return
     */
    static double truncate(double val) {
        double v = val * 100;
        v = (int) v;
        return (v / 100);
    }

}

enum EVENT_ID {
    EVENT_SIM_START,
    EVENT_SIM_RUNNING,
    EVENT_SIM_UNPAUSED,
    EVENT_SIM_END,
    EVENT_AIRPORT,
}

enum DATA_DEFINE_ID {
    DEFINITION_1,
    DEFINITION_2
}

enum DATA_REQUEST_ID {
    REQUEST_1,
    REQUEST_2,
}



