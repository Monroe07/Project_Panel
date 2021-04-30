package Gauges.Common;

import static java.lang.Math.*;

public class DistanceDirectionHaversine {

    double objOneLat;
    double objOneLon;
    double objTwoLat;
    double objTwoLon;

    double objectOneHeading;

    int viewSize;
    int viewDist;
    int roundedDist;
    double truncatedDist;



    double truncatedHeadDeg;
    double headingDeg;


    double headingRad;


    double screenBearingRad;
    int onScreenDist;


    public DistanceDirectionHaversine() {
        // Initializer
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////// Setting Methods ///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Sets the length and width of the "Screen"
     * @param s
     */
    void setViewSize(int s)
    {
        viewSize = s;
    }

    /**
     * Sets the max distance viewed by the gauge
     * @param d
     */
    void setViewDist(int d)
    {
        viewDist = d;
    }

    /**
     * Sets the Coordinates ofthe two objects, from objectOne - > objectTwo
     * @param objectOneLat
     * @param objectOneLon
     * @param objectTwoLat
     * @param objectTwoLon
     */
    void setLatLons(double objectOneLat, double objectOneLon, double objectTwoLat, double objectTwoLon)
    {
        objOneLat = objectOneLat;
        objOneLon = objectOneLon;
        objTwoLat = objectTwoLat;
        objTwoLon = objectTwoLon;
    }



    /**
     * sets Object One's heading
     * Used for calculating relative bearing from straight ahead
     * @param h
     */
    void setObjectOneHeading(double h)
    {
        objectOneHeading = h;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////// Calculating Methods ///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void calcDist()
    {
        // Calculate the distance between the two points from point one -> point two

        double R = 6378137;
        // Lat One
        double φ1 = toRadians(objOneLat);
        // Lon One
        double λ1 = toRadians(objOneLon);
        // Lat Two
        double φ2 = toRadians(objTwoLat);
        // Lon Two
        double λ2 = toRadians(objTwoLon);

        double Δφ = φ2 - φ1;
        double Δλ = λ2 - λ1;

        double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) + Math.cos(φ1) * Math.cos(φ2) * Math.sin(Δλ/2) * Math.sin(Δλ/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = R * c;
        // Convert Meters dist to miles dist
        dist = dist / 1609.344;
        truncatedDist = truncate(dist);
        roundedDist = (int) Math.round(dist);


    }

    /**
     * Calculates how many pixel away an displayed object should be on a map, based on real distance and max viewing distance
     * within the screen size in pixels
     */
    void calculateScreenDist()
    {
        double ratio = viewSize/viewDist;
        onScreenDist = (int)(ratio * roundedDist);


        // Calculate the distance on screen represented by the rounded actual distance
    }

    void calulateHeading()
    {
        // Calculate Heading in rads of object 2 in relation ot object 1
        double φ1 = toRadians(objOneLat);
        // Lon One
        double λ1 = toRadians(objOneLon);
        // Lat Two
        double φ2 = toRadians(objTwoLat);
        // Lon Two
        double λ2 = toRadians(objTwoLon);

        double Δφ = φ2 - φ1;
        double Δλ = λ2 - λ1;

        double y = Math.sin(λ2-λ1) * Math.cos(φ2);
        double x = Math.cos(φ1)*Math.sin(φ2) - Math.sin(φ1)*Math.cos(φ2)*Math.cos(λ2-λ1);


        headingRad = Math.atan2(y, x);

        headingDeg = toDegrees(headingRad);
        if(headingDeg < 0)
        {
            headingDeg = 360 + headingDeg;
        }
        truncatedHeadDeg = truncate(headingDeg);

    }



    void calculateScreenBearing()
    {
        screenBearingRad = headingRad - toRadians(objectOneHeading);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////// Getting Methods ///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    double getTruncatedDist()
    {
        return truncatedDist;
    }

    int getOnScreenDist()
    {
        return onScreenDist;
    }

    double getOnScreenBearingRad()
    {
        return screenBearingRad;
    }

    double getTruncatedHeadDeg()
    {
        return truncatedHeadDeg;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////// Utility Methods ///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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


    /**
     * Convert degree min sec format to decimal format
     * @param deg
     * @param min
     * @param sec
     * @return
     */
    double getDecimal(double deg, double min, double sec) {
        double dec = deg + min / 60 + sec / 3600;
        return deg;
    }

    /**
     *
     * @param d On Screen Dist
     * @param Θ On screen Relative Heading in Rads
     * @return
     */

    int[] getXYCoords(int d, double Θ)
    {
        int[] coords = new int[2];
        double X = d * sin(Θ);
        double Y = d * cos(Θ);
        coords[0] = (int)X;
        coords[1] = (int)Y;
        return coords;

    }


}
