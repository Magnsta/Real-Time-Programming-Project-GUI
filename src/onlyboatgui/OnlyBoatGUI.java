package onlyboatgui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.Graphics;


/**
 * 
 * @author stava
 */
public class OnlyBoatGUI extends javax.swing.JFrame
{
    /**
    * 
    */
    private OnlyBoatGUI()throws IndexOutOfBoundsException
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
               new OnlyBoatGUI();
            }
        });
    }
}
 
