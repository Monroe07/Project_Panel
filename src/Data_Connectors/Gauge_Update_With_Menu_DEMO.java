package Data_Connectors;


import flightsim.simconnect.SimConnect;

import static java.lang.Math.toRadians;


public class Gauge_Update_With_Menu_DEMO {

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


    public Gauge_Update_With_Menu_DEMO() throws Exception {


        adi = new Gauges.ADI.ADI_Picture();
        speed = new Gauges.Airspeed.AirSpeed_Picture();
        rpm = new Gauges.RPM.RPM_Picture();
        Radio = new Gauges.COM_1.Radio_Freq_Display();
        hsi = new Gauges.HSI.HSI_Picture();
        Alt = new Gauges.Altimeter.Altimeter_Picture();


        while (true) {


            // AIRSPEED
            for (int j = 0; j < 161; j++) {
                speed.setAS(j);
                speed.repaint();
                Thread.sleep(25);
            }
            for (int j = 160; j > -1; j--) {
                speed.setAS(j);
                speed.repaint();
                Thread.sleep(25);
            }
            // ADI PITCH
            for (int j = 0; j < 50; j++) {
                adi.setPitchBankValues(toRadians(j),0);
                adi.repaint();
                Thread.sleep(25);
            }
            for (int j = 50; j > -50; j--) {
                adi.setPitchBankValues(toRadians(j),0);
                adi.repaint();
                Thread.sleep(25);
            }
            for (int j = -50; j > -1; j++) {
                adi.setPitchBankValues(toRadians(j),0);
                adi.repaint();
                Thread.sleep(25);
            }
            // ADI ROLL
            // ALTITUDE
            // RPM
            // ALTITUDE
        }


    }


    public static void main(String[] args) throws Exception {


        Gauge_Update_With_Menu_DEMO JSimConnect = new Gauge_Update_With_Menu_DEMO();


    }


}


