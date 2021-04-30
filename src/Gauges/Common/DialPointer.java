package Gauges.Common;//import javax.swing.*;
import java.awt.*;
        import java.io.IOException;

/**
 * The Dial Pointer Class Contains the various "Pointer" shapes for the Basic Instruments Project.
 * Gauge Pointers include;
 *          -Airspeed
 *          -Altitude
 *          -Turn Coordinator Roll Rate
 *          -Turn Coordinator Yaw Rate
 *          -Gauges.HSI.HSI (Compass) "Plane"
 *          -Vertical Speed Indicator
 * *Note* - This class in one of only a few shared between the instruments
 *
 * @author Kyle A. Monroe
 * @version 12.5
 * @since 2017-05-16
 */
public class DialPointer {
    int cx, cy;
    double value;
    double offvalue;
    int pointerDiameter;
    int pointerType = 1;

    Color pointerColor;
    Color pointerEdgeColor;
    Color tipColor;
    Color pointerStemColor;

    //final BufferedImage image1 = ImageIO.read(new File("C:\\Users\\Monroe\\IdeaProjects\\JavaProjects\\src\\BasicInstruments\\Pictures\\CubBlack.png"));

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param diameter
     * @throws IOException
     */
    public DialPointer(int diameter) throws IOException {
        pointerDiameter = diameter;

        cx = 150;   // 150
        cy = 150;

        pointerColor = new Color(255,0,0);
        pointerEdgeColor = new Color(220,0,0);
        tipColor = new Color(255,0,0);
        pointerStemColor = new Color(28, 28, 28);



    }

    /**
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void draw(Graphics g) {
        switch (pointerType) {
            case 1:
                drawOuterPointerAlt(g);
                drawSimplePointerAltSm(g);
                drawSimplePointerAltLrg(g);
                break;
            case 2:
                drawArrowPointer(g);
                break;
            case 3:
                drawLargeArrowPointer(g);
                break;
            case 4:
                drawVORVertPointer(g);
                break;
            case 5:
                drawSimplePointerAS(g);
                break;
            case 6:
                drawSimplePointerVSI(g);
                break;
            case 7:
                drawPlanePointer(g);
                break;
            case 8:
                drawTurnCoordinatorPlane(g);
                break;
            case 9:
                drawTurnCoordinatorYaw(g);


            default:
                //System.out.println(pointerType);

        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Design Functions for Various Pointers   ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /** NOT USED ANYMORE
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void drawArrowPointer(Graphics g) {
        double angle = value * 2 * Math.PI;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(angle, cx, cy);

        Polygon p = new Polygon();
        p.addPoint(cx, cy - (int) (pointerDiameter * 0.4));
        p.addPoint(cx - 4, cy + 12 - (int) (pointerDiameter * 0.4));
        p.addPoint(cx + 4, cy + 12 - (int) (pointerDiameter * 0.4));

        g.setColor(Color.black);
        g.drawPolygon(p);
        //g.setColor(Color.blue);
        g.setColor(pointerColor);
        g.fillPolygon(p);
        g.fillRect(cx - 1, cy - (int) (pointerDiameter * 0.38), 2, (int) (pointerDiameter * 0.78));

        g2d.rotate(-angle, cx, cy);
    }

    /** NOT USED ANYMORE
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void drawPlanePointer(Graphics g) {
        double angle = value * 2 * Math.PI;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(angle, cx, cy);
        //g.fillRect(cx - 24, cy + 8, 48, 16);
        //g.drawImage(image1, cx-122,cy-110,null);
        g2d.rotate(-angle, cx, cy);
    }




    /** NOT USED ANYMORE
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void drawLargeArrowPointer(Graphics g) {
        double angle = value * 2 * Math.PI;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(angle, cx, cy);

        Polygon p = new Polygon();
        p.addPoint(cx - (int) (pointerDiameter * 0.035), cy - (int) (pointerDiameter * 0.34));
        p.addPoint(cx - (int) (pointerDiameter * 0.06), cy - (int) (pointerDiameter * 0.34));
        p.addPoint(cx, cy - (int) (pointerDiameter * 0.41));
        p.addPoint(cx + (int) (pointerDiameter * 0.06), cy - (int) (pointerDiameter * 0.34));
        p.addPoint(cx + (int) (pointerDiameter * 0.035), cy - (int) (pointerDiameter * 0.34));

        //g.setColor(Color.blue);
        g.setColor(pointerColor);
        g.drawPolygon(p);
        g.drawLine(cx, cy + (int) (pointerDiameter * 0.4), cx, cy + (int) (pointerDiameter * 0.34));

        g.drawLine(cx - (int) (pointerDiameter * 0.035), cy + (int) (pointerDiameter * 0.34), cx - (int) (pointerDiameter * 0.035), cy - (int) (pointerDiameter * 0.34));
        g.drawLine(cx + (int) (pointerDiameter * 0.035), cy + (int) (pointerDiameter * 0.34), cx + (int) (pointerDiameter * 0.035), cy - (int) (pointerDiameter * 0.34));

        g2d.rotate(-angle, cx, cy);
    }

    /** Gauge: Gauges.Altimeter.Altimeter
     * NAME:
     * GAUGE:
     * PURPOSE:
     * Gauge pointer showing 100's of feet
     * @param g
     */
    public void drawSimplePointerAltLrg(Graphics g) {       // Large Pointer for Gauges.Altimeter.Altimeter
        double angle = (value) * 2 * Math.PI;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(angle, cx, cy);

        // Draw Main part of pointer


        Polygon A = new Polygon();  // Stem
        A.addPoint(cx - 5, cy + 30);    // 1
        A.addPoint(cx + 5, cy + 30);    // 2
        A.addPoint(cx + 5, cy - 25);    // 3
        A.addPoint(cx - 5, cy - 25);    // 4

        Polygon B = new Polygon();  // Tip
        B.addPoint(cx - 5, cy - 25);    // 1
        B.addPoint(cx - 5, cy - 110);    // 2
        B.addPoint(cx, cy - 125);
        B.addPoint(cx + 5, cy - 110);    //
        B.addPoint(cx + 5, cy - 25);    //



        g.setColor(pointerColor);
        g.fillPolygon(B);               // Tip
        g.setColor(pointerEdgeColor);
        g.drawPolygon(B);
        g.setColor(Color.BLACK);
        g.fillPolygon(A);
        g.setColor(pointerStemColor);
        g.drawPolygon(A);
        // Draw Center Dot. D is Diameter of Dot
        int D = 24;
        // Center Circle
        g.setColor(Color.BLACK);
        g.fillOval(cx - (D / 2), cy - (D / 2), D, D);
        D = D - 8;
        // End Circle
        g.setColor(Color.BLACK);
        g.fillOval(cx - (D / 2), cy + 25, D, D);

        g2d.rotate(-angle, cx, cy);
    }

    /**
     * NOT USED ANYMORE
     * NAME:
     * GAUGE:
     * PURPOSE:
     * Gauge: Gauges.Altimeter.Altimeter
     * @param g
     */
    public void drawOuterPointerAlt(Graphics g)
    {
        double angle = ((value) * 2 * Math.PI)/100;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(angle, cx, cy);


        // Radius of inner circle
        int radius = 25;  // 30
        // Width of
        int width = 8;
        // Height of
        int height = 130;
        // Diameter of Outer Circle
        int dia = 135;
        // Angle of arc missing of 10000 feet pointer
        int arcDeg = 50;
        // Operation to make arced mask
        g.setColor(Color.DARK_GRAY);
        g.fillOval(cx - radius, cy - radius, (radius*2), (radius*2));
        g.fillArc(cx-(dia/2), cy-(dia/2), dia,dia, 180+(arcDeg/2),360-arcDeg);
        g.fillRect(cx-(width/2), cy - height, width,height);

        int triangleOffset = 26;
        Polygon A = new Polygon();  // Center upside-down triangle
        A.addPoint(cx, cy-76 - triangleOffset);    // 1
        A.addPoint(cx - (width/2)-7, cy - 100 - triangleOffset);    // 2
        A.addPoint(cx + (width/2)+7, cy - 100 - triangleOffset);    // 3



        // Draw Center Stem using 3 lines all parallel

        g.setColor(pointerColor);
        g.drawLine(cx,cy, cx, cy-125);
        g.drawLine(cx-1, cy, cx-1, cy-125);
        g.drawLine(cx+1, cy, cx+1, cy-125);
        g.fillPolygon(A);



        g2d.rotate(-angle, cx, cy);
    }

    /** Gauge: Gauges.Altimeter.Altimeter
     * Gauge pointer showing 1000's of feet
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void drawSimplePointerAltSm(Graphics g) {       // Small Pointer for Gauges.Altimeter.Altimeter

        /*
         * Calculates the rotation of the pointer based on the following factors
         * Value to be represented
         * Max value of full rotation of pointer
         * Radian measurement of calculated angle in degrees
         */
        double angle = (value / 10) * 2 * Math.PI;

        // Value to hold Diameter of Circle of pointer
        int D = 24;  //24
        Graphics2D g2d = (Graphics2D) g;
        // Enables Anti-Aliasing to smooth out drawings
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Rotate Whole Gauge opposite Direction of Value before drawing Pointer on Gauge
        g2d.rotate(angle, cx, cy);



        //// TIP OF ALT SMALL ////////////////////
        Polygon E = new Polygon();
        //// Add Points to Polygon E /////////////
        // Bottom Left of Pentagonal Shape
        E.addPoint(cx - 7, cy - 25);
        // Middle Left of Pentagonal Shape
        E.addPoint(cx - 10, cy - 55);
        // Tip of Pentagonal Shape
        E.addPoint(cx, cy - 85);
        // Middle Right of Pentagonal Shape
        E.addPoint(cx + 10, cy - 55);
        // Bottom Right of Pentagonal Shape
        E.addPoint(cx + 7, cy - 25);
        ///////////////////////////////////////////

        //// STEM OF ALT SMALL ////////////////////
        Polygon F = new Polygon();
        //// Add Points to Polygon F //////////////
        // Upper Left Corner of Stem
        F.addPoint(cx - 7, cy - 25);
        // Upper Right Corner of Stem
        F.addPoint(cx + 7, cy - 25);
        // Bottom Right Corner of Stem
        F.addPoint(cx + 5, cy);
        // Bottom Left  Corner of Stem
        F.addPoint(cx - 5, cy);
        ///////////////////////////////////////////

        //// TAIL OF ALT SMALL ////////////////////
        Polygon G = new Polygon();
        // Upper Left Corner of shape
        G.addPoint(cx-7, cy);
        // Bottom Left of Shape
        G.addPoint(cx - 12, cy + 25);
        // Bottom Right of Shape
        G.addPoint(cx + 12, cy + 25);
        // Top Right of Shape
        G.addPoint(cx+7, cy);
        ///////////////////////////////////////////



        //// Draw Parts of Pointer ////////////////
        // Set Color of Stem of Pointer
        g.setColor(Color.BLACK);
        // Draw Stem Polygon F
        g.fillPolygon(F);           // Stem
        g.fillPolygon(G);           // Tail


        g.fillOval(cx - 12, cy + 17, 24, 15);
        // Set Color of Tip of Pointer
        g.setColor(tipColor);
        // Draw Tip Polygon E
        g.fillPolygon(E);           // Tip
        g.setColor(pointerEdgeColor);
        g.drawPolygon(E);


        // Set Color of Circle in center of Pointer
        g.setColor(Color.BLACK);
        // Draw Center pointer circle on Gauges.Altimeter.Altimeter
        g.fillOval(cx - (D / 2), cy - (D / 2), D, D);

        // Rotates gauge face and newly drawn pointer back to 0 Rad.
        g2d.rotate(-angle, cx, cy);
    }

    /** Gauge: Airspeed
     * Gauge pointer showing Airspeed in MPH
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void drawSimplePointerAS(Graphics g) {       // Pointer for Gauges.Airspeed.AirSpeed
        double angle = (value) * 2 * Math.PI;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(angle, cx, cy);
        //g.setColor(Color.blue);
        // Draw Main part of pointer
        g.setColor(pointerColor);

        Polygon C = new Polygon();  // Stem
        C.addPoint(cx - 5, cy + 30);    // 1
        C.addPoint(cx + 5, cy + 30);    // 2
        C.addPoint(cx + 5, cy - 25);    // 3
        C.addPoint(cx - 5, cy - 25);    // 4

        Polygon D = new Polygon();  // Tip
        D.addPoint(cx - 5, cy - 25);    // 1
        D.addPoint(cx - 5, cy - 110);    // 2
        D.addPoint(cx, cy - 125);
        D.addPoint(cx + 5, cy - 110);    //
        D.addPoint(cx + 5, cy - 25);    //

        // Draw Center pointer circle on Gauges.Altimeter.Altimeter
        //g.setColor(Color.black);
        //g.drawPolygon(A);
        g.setColor(Color.RED);
        g.fillPolygon(D);
        g.setColor(Color.BLACK);
        g.fillPolygon(C);
        // Draw Center Dot. D is Diameter of Dot
        int d = 24;
        // Center Circle
        g.fillOval(cx - (d / 2), cy - (d / 2), d, d);
        d = d - 8;
        // End Circle
        g.fillOval(cx - (d / 2), cy + 25, d, d);

        g2d.rotate(-angle, cx, cy);
    }   // End of private void drawSimplePointerAS(Graphics g) // Gauges.Airspeed.AirSpeed Pointer

    /** Gauge: Vertical Speed Indicator (Gauges.VSI.VSI)
     * Gauge Pointer showing "vertical" speed derived from altitude changes over a given period
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void drawSimplePointerVSI(Graphics g) {       // Pointer for Gauges.Airspeed.AirSpeed
        double VSIangle = ((value) * 2 * Math.PI) - .5 * Math.PI;
        //double VSIangle = ((value) *Math.PI/180)- .5*Math.PI;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(VSIangle, cx, cy);
        //g.setColor(Color.blue);
        // Draw Main part of pointer
        g.setColor(pointerColor);

        //g.fillRect(cx - 1, cy - (int) (pointerDiameter * 0.48), 2, (int) (pointerDiameter * 0.48));
        // Draw Tip
        //g.setColor(tipColor);
        //g.fillRect(cx - 1, cy - (int) (pointerDiameter * 0.48), 2, (int) (pointerDiameter * 0.1));

        Polygon A = new Polygon();  // Stem
        A.addPoint(cx - 5, cy + 30);    // 1
        A.addPoint(cx + 5, cy + 30);    // 2
        A.addPoint(cx + 5, cy - 25);    // 3
        A.addPoint(cx - 5, cy - 25);    // 4

        Polygon B = new Polygon();  // Tip
        B.addPoint(cx - 5, cy - 25);    // 1
        B.addPoint(cx - 5, cy - 110);    // 2
        B.addPoint(cx, cy - 125);
        B.addPoint(cx + 5, cy - 110);    //
        B.addPoint(cx + 5, cy - 25);    //

        // Draw Center pointer circle on Gauges.Altimeter.Altimeter
        //g.setColor(Color.black);
        //g.drawPolygon(A);
        g.setColor(Color.RED);
        g.fillPolygon(B);
        g.setColor(Color.BLACK);
        g.fillPolygon(A);
        // Draw Center Dot. D is Diameter of Dot
        int d = 24;
        // Center Circle
        g.fillOval(cx - (d / 2), cy - (d / 2), d, d);
        d = d - 8;
        // End Circle
        g.fillOval(cx - (d / 2), cy + 25, d, d);

        g2d.rotate(-VSIangle, cx, cy);
    }   // End of private void drawSimplePointerAS(Graphics g) // Gauges.Airspeed.AirSpeed Pointer

    /** Gauge: Gauges.HSI.HSI
     * Gauge pointer symbolizing/signifying a basic airframe and its compass heading
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void drawVORVertPointer(Graphics g) {

        double angle = value;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(angle, cx, cy);

        // Triangle at front of plane
        Polygon A = new Polygon();
        int adj = 29;
        A.addPoint(cx, cy - 140 + adj);
        A.addPoint(cx - 12, cy - 155 + adj);
        A.addPoint(cx + 12, cy - 155 + adj);

        // Red Extension from nose of plane
        g.setColor(Color.red);
        g.fillRect(cx - 1, cy - 125, 2, 135);
        g.fillPolygon(A);

        // Plane
        g.setColor(Color.BLACK);
        g.fillRect(cx - 4, cy - 36, 8, 96);   // fuselage
        g.fillRect(cx - 50, cy - 4, 100, 8);   // wing
        g.fillRect(cx - 18, cy + 39, 36, 8);   // stab


        g2d.rotate(-angle, cx, cy);
    }

    /** Gauge Turn Coordinator
     * Gauge Pointer used to show Rate of roll (aka Bank) over a given period of time
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void drawTurnCoordinatorPlane(Graphics g) {       // Large Pointer for Gauges.Altimeter.Altimeter
        double angle = (value * Math.PI / 180);
        double maxVal = .35;
        double minVal = -.35;

        // Constrain angle
        if (angle > maxVal)
            angle = maxVal;
        if (angle < minVal)
            angle = minVal;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(angle, cx, cy);


        // Draw Center Pivot Point Circle
        int dia = 28;
        int rad = dia/2;
        g.setColor(Color.BLACK);
        g.fillOval(cx-rad, cy-rad, dia,dia);

        // Draw Small triangle to give bottom of stem a tapered look
        int y_shift = 9;
        int height = 20;
        int width = 11;
        Polygon tri = new Polygon();
        tri.addPoint(cx, cy-y_shift-height);
        tri.addPoint(cx-width, cy-y_shift);
        tri.addPoint(cx+width,cy-y_shift);
        g.fillPolygon(tri);

        int lenAdj = 15;
        // Draw Stem
        g.fillRect(cx-5, cy-100, 10,100-lenAdj);

        // Draw Tapered top of stem
        Polygon tri2 = new Polygon();
        tri2.addPoint(cx, cy-80+lenAdj);
        tri2.addPoint(cx-9, cy-105+lenAdj);
        tri2.addPoint(cx+9, cy-105+lenAdj);

        g.fillPolygon(tri2);
        // Draw tip of pointer
        g.setColor(tipColor);
        g.fillRoundRect(cx-9, cy-135+lenAdj, 18,35,5,5);
        g2d.rotate(-angle, cx, cy);
    }

    /** Gauge: Turn Coordinator
     * Gauge Pointer used to know the Rate of Yaw over a given period of time
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param g
     */
    public void drawTurnCoordinatorYaw(Graphics g) {
        BasicStroke stroke = new BasicStroke(2,BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL,10);
        double maxVal = .05;
        double minVal = -.05;
        double angle = (value * Math.PI / 180);
        int yawPointerHeightOffset = 15;
        // Constrain angle
        if (angle > maxVal)
            angle = maxVal;
        if (angle < minVal)
            angle = minVal;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(stroke);
        g2d.rotate(angle, cx, -1000-yawPointerHeightOffset); // -1325
        // Draw Oval or "Ball"
        g.setColor(Color.RED);
        //g.drawOval(cx-13,cy+56,26,37);

        g.fillOval(cx-13,cy+56-yawPointerHeightOffset,26,37);
        g2d.rotate(-angle, cx, -1000-yawPointerHeightOffset);// -1325
    }

    /** Method used to set the value that a gauge pointer needs to visually point to (Rotate/translate)
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _value
     */
    public void setValue(double _value) {
        // from zero to one
        value = _value;
        //value -= Math.floor(value);
    }

    /** Method to set an offset to the value that a guage pointer needs to visually point to (Rotate/translate)
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param _value
     */
    public void setoffValue(double _value) {
        offvalue = _value;
    }

    /** Method used to set the center of a pointers rotation
     * @param _cx
     * @param _cy
     */
    public void reposition(int _cx, int _cy) {
        cx = _cx;
        cy = _cy;
    }

    /** Method used to pass a value to the Gauges.Common.DialPointer class to determine which type of pointer to display
     * NAME:
     * GAUGE:
     * PURPOSE:
     * @param value
     */
    public void setPointerType(int value) {
        pointerType = value;
    }
}
