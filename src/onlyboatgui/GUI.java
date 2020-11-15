/*
*Simple GUI for semester project in subject true-time programming
*Main goal of this GUI is to implement Threads and take into considertaion
*the concept of thread safety.
*This is achived through utilizing the SwingWorker class to 
*do the background work and updating the GUI while the GUI remains
*interactive for the end-user. 
*
*The visual aspect of the GUI has not been a priority
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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author stava
 */
public class GUI extends JFrame
{    
    //Create labels and buttons to GUI
    public JLabel image = new JLabel();
    public JLabel Platform = new JLabel("Platform is stable: ");
    public JLabel PxLabel = new JLabel(" X: ---");
    public JLabel PyLabel = new JLabel(" Y: ---");
    public JLabel PzLabel = new JLabel(" Z: ---");
    public JButton startButton = new JButton("Start");
    public JButton stopButton = new JButton("Stop");
    
    //String to store X, Y and Z values
    String ax = "";
    String ay = "";
    String az = "";

    //Variable to start and stop GUI
    public int pressedOnce = 0;
    
    private static final long serialVersionUID = 1L; 
    
    DatagramSocket serverSocket = new DatagramSocket(1610);
    GraphicsDevice myDevice;
    Window myWindow;  
    
    /**
     * 
     * @param title
     * @throws SocketException 
     */
    public GUI(String title) throws SocketException
    {
        super(title);

        setLayout(new GridBagLayout());
  
        //Sets GUI font
        Platform.setFont(new Font("serif", Font.BOLD, 28));
        PxLabel.setFont(new Font("serif", Font.BOLD, 28));
        PyLabel.setFont(new Font("serif", Font.BOLD, 28));
        PzLabel.setFont(new Font("serif", Font.BOLD, 28));
        startButton.setFont(new Font("serif",Font.BOLD,28));
        stopButton.setFont(new Font("serif",Font.BOLD,28));
                
        GridBagConstraints gc = new GridBagConstraints();
        startButton.setBackground(Color.green);
        stopButton.setBackground(Color.red);
  
        //Add labels and buttons to GUI with position
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
        gc.weighty = 2;
        add(startButton, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        add(stopButton, gc);


        
        ///////////
        /**
         * Timer for updating the GUI
         * Updates every 1000ms
         */
        Timer times = new Timer(200, (e) -> {
            
        });
        
        /**
         * 
         */
        ActionListener Xlabel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Boolean,String> worker = new SwingWorker<Boolean, String>() 
                {
                    @Override
                    protected Boolean doInBackground() throws Exception 
                    {
                        return true;
                    }
                    //Safely update the GUI
                    protected void done()
                    {                                     
                            PxLabel.setText(" X: "+ ax);                                                 
                    }           
                };
                worker.execute();        
            }
        };
        
        /**
         * 
         */
        ActionListener Ylabel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Boolean,String> worker = new SwingWorker<Boolean, String>() 
                {
                    @Override
                    protected Boolean doInBackground() throws Exception 
                    {
                        return true;                        
                    }
                    //Safely update the GUI
                    protected void done()
                    {                                   
                            PyLabel.setText(" Y: "+ ay);                                                                        
                    }           
                };
                worker.execute();        
            }
        };
        
        /**
         * 
         */
        ActionListener Zlabel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Boolean,String> worker = new SwingWorker<Boolean, String>() 
                {
                    @Override
                    protected Boolean doInBackground() throws Exception 
                    {
                        return true;
                    }
                    //Safely update the GUI
                    protected void done()
                    {           
                            PzLabel.setText(" Z: "+ az);                                                           
                    }           
                };
                worker.execute();        
            }
        };
        
        /**
         * 
         */
        ActionListener PlatformState = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Boolean,Boolean> worker = new SwingWorker<Boolean, Boolean>() 
                {
                    @Override
                    protected Boolean doInBackground() throws Exception 
                    {
                        return true;            
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
        };
        
        /**
         * 
         */
        stopButton.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                SwingWorker<Integer,Void> worker = new SwingWorker<Integer, Void>() 
                {
                    @Override
                    protected Integer doInBackground() throws Exception 
                    {
                        return pressedOnce;
                    }
                    protected void done()
                    {     
                        try {
                            int status = get();
                            if(status >= 1)
                            {
                                pressedOnce = 0;
                                PxLabel.setText(" X: ---");
                                PyLabel.setText(" Y: ---");
                                PzLabel.setText(" Z: ---");
                                Platform.setText("Platform GUI Stoped");
                                times.stop();
                            }
                            
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ExecutionException ex) {
                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }                        
                    }           
                };
                worker.execute();        
            }
        });
        
 
        
        /**
         * 
         */
        startButton.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                SwingWorker<Integer,Void> worker = new SwingWorker<Integer, Void>() 
                {
                    @Override
                    protected Integer doInBackground() throws Exception 
                    {
                        return pressedOnce;
                    }
                    protected void done()
                    {             
                        int status;
                        try {
                            status = get();
                            if(status == 0)
                            {        
 
                                pressedOnce += 1;
                                times.addActionListener(UDPlistener);
                                times.addActionListener(Xlabel);
                                times.addActionListener(Ylabel);
                                times.addActionListener(Zlabel);
                                times.addActionListener(PlatformState);
                                times.start();              
                            } 
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ExecutionException ex) {
                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }                        
                    }           
                };
                worker.execute();        
            }
        });
    }
    
    
    /**
         * 
         */
        ActionListener UDPlistener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<String[],Boolean> worker = new SwingWorker<String[], Boolean>() 
                {
                    @Override
                    protected String[] doInBackground() throws Exception 
                    {
                        
                        byte[] receiveData = new byte[10];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        serverSocket.receive(receivePacket);
                        receiveData = receivePacket.getData();
                        String s = new String(receiveData);
                        String[] splittest = s.split(":",2);
                        return splittest;            
                    }
                    protected void done()
                    {
                        String ztest[];
                        try
                        {
                            {
                                ztest = get();
                                if(ztest[0].equals("M3"))
                                {
                                    az = ztest[1];
                                    System.out.println(az);
                                }
                                if(ztest[0].equals("M2"))
                                {
                                    ay = ztest[1];
                                    System.out.println(ay);
                                }
                                if(ztest[0].equals("M1"))
                                {
                                    ax = ztest[1];
                                    System.out.println(ax);
                                }        
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
        };        
}
