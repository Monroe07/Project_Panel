package Gauges.Common; /**
 * Created by Monroe on 6/6/2017.
 */

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow
{
    BorderLayout layout = new BorderLayout();
    JLabel imageLabel = new JLabel();
    JPanel southPanel = new JPanel();
    FlowLayout southPanelLayout = new FlowLayout();
    JProgressBar progress = new JProgressBar();
    ImageIcon imageIcon;

    public SplashScreen(ImageIcon imageIcon)
    {
        this.imageIcon = imageIcon;
        try{
            // run method to show splash screen
            runSplash();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    // Method to show splash screen
   public void runSplash()throws Exception
    {
        imageLabel.setIcon(imageIcon);
        this.getContentPane().setLayout(layout);
        southPanel.setLayout(southPanelLayout);
        southPanel.setBackground(Color.BLACK);
        this.getContentPane().add(imageLabel,BorderLayout.CENTER);
        this.getContentPane().add(southPanel,BorderLayout.SOUTH);
        southPanel.add(progress, null);
        this.pack();
    }

    // Method to set progress bar max value
    public void setProgressMax(int maxVal)
    {
        progress.setMaximum(maxVal);
    }

    // Method to set progress level without message
    public void setProgress(int Progress)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                progress.setValue(Progress);
            }
        });
    }

    // Method to set progress level With message
    public void setProgress(String message, int Progress)
    {
        setProgress(Progress);
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                progress.setValue(Progress);
                setMessage(message);
            }
        });
    }

    // Method to set Visibility of splash screen
    public void setScreenVisible(boolean visible)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                setVisible(visible);
            }
        });
    }

    // Method to set a message
    public void setMessage(String message)
    {
        if (message == null)
        {
            message = "";
            progress.setStringPainted(false);
        }
        else
        {
            progress.setStringPainted(true);
        }
        progress.setString(message);
    }

}
