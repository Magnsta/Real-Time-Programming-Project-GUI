/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlyboatgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GraphicsDevice;
import java.awt.Window;
import java.util.List;
import java.util.concurrent.ExecutionException;


import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author stava
 */
public class GUI extends JFrame
{    
    public JLabel Platform = new JLabel("Platform is stable: ");
    public JLabel PxLabel = new JLabel(" X : ");
    public JLabel PyLabel = new JLabel(" Y : ");
    public JLabel PzLabel = new JLabel(" Z : ");
    public JButton startButton = new JButton("Start");
    
    private static final long serialVersionUID = 1L; 
 
 
    GraphicsDevice myDevice;
    Window myWindow;  
    CommunicationClient client = new CommunicationClient();
    public GUI(String title)
    {
        super(title);
  
        setLayout(new GridBagLayout());
  
        Platform.setFont(new Font("serif", Font.BOLD, 28));
        PxLabel.setFont(new Font("serif", Font.BOLD, 28));
        PyLabel.setFont(new Font("serif", Font.BOLD, 28));
        PzLabel.setFont(new Font("serif", Font.BOLD, 28));
        startButton.setFont(new Font("serif",Font.BOLD,28));
                
        GridBagConstraints gc = new GridBagConstraints();
        startButton.setBackground(Color.green);
  
  
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 1;
        gc.gridy = 1;
        gc.weightx = 1;
        gc.weighty = 1;
        add(Platform, gc);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        add(PxLabel, gc);

  
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 1;
        gc.weighty = 1;
        add(PyLabel, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        add(PzLabel, gc);
        
        gc.gridx = 1;
        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        add(startButton, gc);

        
        startButton.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                SwingWorker<Boolean,Boolean> worker = new SwingWorker<Boolean, Boolean>() 
                {
                    @Override
                    protected Boolean doInBackground() throws Exception 
                    {
                        return client.getPlatformState();            
                    }
                    protected void done()
                    {
                        boolean status;
                        try
                        {
                            {
                                status = get();
                                Platform.setText("Platform is stable: " + status);                
                            }
                        }
                        catch (InterruptedException | ExecutionException e)
                        {
                            System.out.println(e);
                        }                    
                    }           
                };
                worker.execute();        
            }
        });
    }
}
