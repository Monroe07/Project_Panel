package Gauges.COM_1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;


public class Radio_Freq_Display extends JFrame
{
    double act;
    double stdby;
    private int cx, cy;
    int w = 400;
    int h = 300;

    boolean visible = true;
    Color Background = new Color (35, 35, 35, 255);


    public Radio_Freq_Display() throws Exception
    {
       super("Com 1");
       setSize(w,h);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setUndecorated(true);
       setResizable(true);
       setContentPane(new RadioPanel());
       setBackground(Background);
       setLocation(1260,500);
       setLayout(null);
       setVisible(visible);
        cx = this.getWidth() / 2;
        cy = this.getHeight() / 2;
    }

    public void setAct(double a)
    {
        act = a;
    }
    public void setStdby(double s)
    {
        stdby = s;
    }


    class RadioPanel extends JPanel{
NumericDisplayRadio radio;

        RadioPanel() throws IOException{
            super();
            radio = new NumericDisplayRadio();
        }

        public void paintComponent(Graphics g)
        {
            Color chromeYellow = new Color (255, 139, 0);

            g.setColor(Background);       // Set Background
            g.fillRect(0,0,this.getWidth(),this.getHeight());


            radio.setActive(act);
            radio.setStandby(stdby);
            radio.draw(g);
        }
    }
}
