package onlyboatgui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 
 * @author stava
 */
public class OnlyBoatGUI 
{
    
    /**
    * 
    */
    private static void createAndSHowGUI()
    {
        GUI frame = new GUI("ONLYBOAT GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setBackground(Color.yellow);
        frame.setPreferredSize(new Dimension(800,800));
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
            public void run() 
            {
                createAndSHowGUI();
            }
        });
    }
}
 
