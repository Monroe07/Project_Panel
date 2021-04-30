package Gauges.Radar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AirportList {
    ArrayList<String[]> csvImport;

    DistanceDirectionRhumb D;
    InputStream is;
    InputStreamReader isr;
    BufferedReader br;
    String line;


    /**
     * Initializer
     * Will Open designated file and import in all Airports in the file
     *
     * @throws IOException
     */
    public AirportList() throws IOException {
        csvImport = new ArrayList<>();
        long start = System.currentTimeMillis();
        try {
            D = new DistanceDirectionRhumb();
            is = getClass().getResourceAsStream("/Data_Files/Airport_List_US_CA.csv");
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            br.readLine();
        } catch (Exception e) {

        }
        // Save CSV File as ArrayList
        while ((line = br.readLine()) != null) {

            csvImport.add(line.split(","));

        } // End of while ((line = br.readLine()) != null)

        long end = System.currentTimeMillis();

        System.out.println("Time to Complete: " + (end - start) + " ms");

    } // End of public AirportList() throws IOException



    /**
     * Initializer that takes a file name as an parameter
     *
     * @param file
     * @throws IOException
     */
    public AirportList(String file) throws IOException {
        csvImport = new ArrayList<>();
        long start = System.currentTimeMillis();
        try {

            D = new DistanceDirectionRhumb();
            is = getClass().getResourceAsStream("/Data_Files/" + file);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            br.readLine();
        } catch (Exception e) {

        }

        // Save CSV File as ArrayList
        while ((line = br.readLine()) != null) {

            csvImport.add(line.split(","));

        } // End of while ((line = br.readLine()) != null)

        long end = System.currentTimeMillis();
        System.out.println("Time to Complete: " + (end - start) + " ms");

    } // End of public AirportList() throws IOException





    public ArrayList<Object[]> getNearbyAirports(double _lat, double _lon, int _radius, double _userHeading) {

        ArrayList<Object[]> nearbyAirports = new ArrayList<>();

        D.setUserPosition(_lat, _lon);
        D.setUserHeading(_userHeading);
        D.setViewDist(_radius);
        D.setViewSize(533);

        for (int i = 0; i < csvImport.size(); i++) {
            Object[] temp;

            D.setObjectPosition(Double.parseDouble(csvImport.get(i)[7]), Double.parseDouble(csvImport.get(i)[8]), 0);
            D.calculateScreenCoordinates();



            double dist = D.getTruncatedDist();
            //double dir = D.getTruncatedHeadDeg();


            if (dist <= _radius) {
                temp = new Object[3];
                temp[0] = csvImport.get(i)[0];
                //System.out.print(temp[0] + "\t");
                temp[1] = D.getX();
                //System.out.print(temp[1] +"\t");
                temp[2] = -D.getY();
                //System.out.println(temp[2]);
                nearbyAirports.add(temp);
            }
        }
        return nearbyAirports;
    }
}


