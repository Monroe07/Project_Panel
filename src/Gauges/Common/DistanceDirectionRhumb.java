package Gauges.Common;

import static java.lang.Math.*;

public class DistanceDirectionRhumb {


    // User Position Information
    double userLat;
    double userLon;
    double userHeading;

    // Object position Information
    double objectLat;
    double objectLon;
    double objectAltitude;

    // Display Info
    int viewSize;
    int viewDist;

    // Calculated Info

    // Calculated Distances
    double distanceMiles;
    double distanceTruncated;
    int distanceRounded;


    // Calculated Angles
    double truncatedHeadDeg;
    double headingDeg;
    double headingRad;

    // Calculated Screen Angle and Distance
    double screenBearingRad;
    int onScreenDist;

    // Calculated Screen Coordinates Relative to (0,0)
    int X;
    int Y;


    public DistanceDirectionRhumb() {
        // Initializer
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////// Setting Methods ///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Sets the length and width of the "Screen"
     *
     * @param s
     */
    public void setViewSize(int s) {
        viewSize = s;
    }

    /**
     * Sets the max distance viewed by the gauge
     *
     * @param d
     */
    public void setViewDist(int d) {
        viewDist = d;
    }


    /**
     * Sets User Planes Info
     *
     * @param _userLat
     * @param _userLon
     * @param _userHeading
     */
    public void setUserPosition(double _userLat, double _userLon, double _userHeading) {
        userLat = _userLat;
        userLon = _userLon;
        userHeading = _userHeading;
    }

    public void setObjectPosition(double _objectLat, double _objectLon, double _objectAltitude) {
        objectLat = _objectLat;
        objectLon = _objectLon;
        objectAltitude = _objectAltitude;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////// Calculating Methods ///////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void calculateDistance() {
        // Calculate the distance between the two points from point one -> point two
        long start  = System.currentTimeMillis();
        // Radius of Earth in meters
        double R = 6378137;
        // Lat One
        double ??1 = toRadians(userLat);
        // Lon One
        double ??1 = toRadians(userLon);
        // Lat Two
        double ??2 = toRadians(objectLat);
        // Lon Two
        double ??2 = toRadians(objectLon);

        double ???? = ??2 - ??1;
        double ???? = ??2 - ??1;

        double ???? = Math.log(Math.tan(PI / 4 + ??2 / 2) / Math.tan(PI / 4 + ??1 / 2));
        double q = Math.abs(????) > 10e-12 ? ???? / ???? : Math.cos(??1); // E-W course becomes ill-conditioned with 0/0
        // if dLon over 180?? take shorter rhumb line across the anti-meridian:
        if (Math.abs(????) > PI) ???? = ???? > 0 ? -(2 * PI - ????) : (2 * PI + ????);

        double dist = Math.sqrt(???? * ???? + q * q * ???? * ????) * R;
        // Distance ot miles
        distanceMiles = dist / 1609.344;
        distanceTruncated = truncate(distanceMiles);
        distanceRounded = toIntExact(round(distanceMiles));
        long end = System.currentTimeMillis();
        //System.out.println("Calculate Distance: " +(end-start) + " ms");

    }

    /**
     * Calculates how many pixel away an displayed object should be on a map, based on real distance and max viewing distance
     * within the screen size in pixels
     */
    private void calculateScreenDistance() {
        double ratio = viewSize / viewDist;
        onScreenDist = (int) floor(ratio * distanceMiles);


    }

    public void calculateHeading() {
        // Calculate Heading in rads of object 2 in relation ot object 1
        double ??1 = toRadians(userLat);
        // Lon One
        double ??1 = toRadians(userLon);
        // Lat Two
        double ??2 = toRadians(objectLat);
        // Lon Two
        double ??2 = toRadians(objectLon);

        double ???? = ??2 - ??1;
        double ???? = ??2 - ??1;

        double ???? = Math.log(Math.tan(PI / 4 + ??2 / 2) / Math.tan(PI / 4 + ??1 / 2));
        // if dLon over 180?? take shorter rhumb line across the anti-meridian:
        if (Math.abs(????) > PI) ???? = ???? > 0 ? -(2 * PI - ????) : (2 * PI + ????);

        double tempRad = Math.atan2(????, ????);
        double tempDeg = toDegrees(headingRad);

        // If Radian Angle is less than 0
        if (tempRad < 0) {
            headingRad = 2 * PI + tempRad;
        } else {
            headingRad = tempRad;
        }

        // If Degree Angle is less than 0
        if (tempDeg < 0) {
            headingDeg = 360 + tempDeg;
        } else {
            headingDeg = tempDeg;
        }

    }


    private void calculateScreenDirection() {

        double output = headingRad - userHeading;

        if (abs(output) > toRadians(180) && output < 0) {
            output = toRadians(360) + output;
        } else if (abs(output) > toRadians(180) && output > 0) {
            output = output - toRadians(360);
        }

        screenBearingRad = output;
    }

    public void calculateScreenCoordinates() {
        calculateDistance();
        calculateHeading();
        calculateScreenDistance();
        calculateScreenDirection();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////// Getting Methods ///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public double getTruncatedDist() {

        return distanceTruncated;
    }

    public int getOnScreenDist() {

        return onScreenDist;
    }

    public double getOnScreenBearingRad() {

        return screenBearingRad;
    }

    public double getTruncatedHeadDeg() {
        return truncate(headingDeg);
    }

    public int getX() {
        return (int) (onScreenDist * sin(screenBearingRad));
    }

    public int getY() {
        return (int) (onScreenDist * cos(screenBearingRad));
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
     *
     * @param deg
     * @param min
     * @param sec
     * @return
     */
    double getDecimal(double deg, double min, double sec) {
        double dec = deg + min / 60 + sec / 3600;
        return deg;
    }


}
