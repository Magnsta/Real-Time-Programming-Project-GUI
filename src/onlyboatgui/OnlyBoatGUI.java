package onlyboatgui;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 
* Simple GUI for semester project in subject true-time programming
 * Main goal of this GUI is to implement Threads and take into considertaion
 * the concept of thread safety.
 * This is achived through utilizing the SwingWorker class to 
 * do the background work and updating the GUI while the GUI remains
 * interactive for the end-user. 
 *
 * The visual aspect of the GUI has not been a priority
 * @author Magnus Stava
 */
public class OnlyBoatGUI 
{
    
    /**
    * Create the GUI frame 
    */
    private static void createAndSHowGUI() throws IOException
    {
        GUI frame = new GUI("ONLYBOAT GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        frame.setBackground(Color.black);
        frame.setPreferredSize(new Dimension(1200,1000));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);     
    }
    
    /**
    * 
    * @param args 
    */
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {              
            @Override
            /**
             * Invoke run() method from Runnable interface
             */
            public void run() 
            {
                try {
                    createAndSHowGUI();
                    
                } catch (IOException ex) {
                    Logger.getLogger(OnlyBoatGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
 
