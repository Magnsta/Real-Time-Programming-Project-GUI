package onlyboatgui;

import java.awt.Color;
import java.awt.Dimension;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * * The GUI class. Adds the labels and buttons to the GUI frame.
 * Aswell as assigning task to be excexuted by the SwingWorker threads
 * Gathers incomming data through UDP.
 * For our application it proved more beneficial to use UDP instead of TCP
 * @author stava
 */
public class GUI extends JFrame
{    
    
    
    //Create labels and buttons to GUI
    public JLabel image = new JLabel();
    public JLabel Platform = new JLabel("Platform is stable: ");
    public JLabel PxLabel = new JLabel(" Motor 1: ---");
    public JLabel PyLabel = new JLabel(" Motor 2: ---");
    public JLabel PzLabel = new JLabel(" Motor 3: ---");
    public JButton startButton = new JButton("Start");
    public JButton stopButton = new JButton("Stop");
    
    //Global variables to store X, Y and Z values gathered from the UDP swingworker
    //Variables updated in the GUI through the other swingworkers
    String M1 = "";
    String M2 = "";
    String M3 = "";
    
    //Variable that tracks if the GUI should start or stop
    // when 0, GUI is idle/stopped
    //when 1, GUI has started
    public int pressedOnce = 0;
    
    private static final long serialVersionUID = 1L; 
    
    
    DatagramSocket serverSocket = new DatagramSocket(1610);
    GraphicsDevice myDevice;
    Window myWindow;  
    
    /**
     * 
     * @param title of GUI frame
     * @throws SocketException 
     */
    public GUI(String title) throws SocketException, IOException
    {
        super(title);
        //Loads image to use as background
        //Put iamge into a JLabel, not optimal solution
        BufferedImage img = ImageIO.read(new File("C:\\Users\\stava\\Downloads\\RegTekEksamener\\moon.jpg"));                    
        setContentPane(new JLabel(new ImageIcon(img)));                    
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridwidth = GridBagConstraints.LINE_END;
               
        //Font customization and size
        Platform.setFont(new Font("serif", Font.BOLD, 30));
        PxLabel.setFont(new Font("serif", Font.BOLD, 35));
        PyLabel.setFont(new Font("serif", Font.BOLD, 35));
        PzLabel.setFont(new Font("serif", Font.BOLD, 35));
        startButton.setFont(new Font("serif",Font.BOLD,28));
        stopButton.setFont(new Font("serif",Font.BOLD,28));
        
        //Color customization for button and label
        startButton.setBackground(Color.green);
        stopButton.setBackground(Color.red);
        PxLabel.setForeground(Color.yellow);
        PyLabel.setForeground(Color.yellow);
        PzLabel.setForeground(Color.yellow);
        Platform.setForeground(Color.yellow);
        
        //Specify location for labels and buttons
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

        /**
         * Timer class from swing library.
         * Defines frequency for how often swingworkers should run
         * Updates every specified ms
         */
        Timer times = new Timer(500, (e) -> {  
        });
        
        /**
         * Swingworker thread responsible for updating the
         * Xlabel value
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
                    //Safely update the GUI from done()
                    protected void done()
                    {                                     
                            if(pressedOnce !=0)
                        {
                            PxLabel.setText(" Motor 1: "+ M1);                                                           
                    
                        }                                                 
                    }           
                };
                worker.execute();        
            }
        };
        
        
        /**
         * Swingworker thread responsible for updating the
         * Ylabel value
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
                    //Safely update the GUI from done()
                    protected void done()
                    {                                   
                            if(pressedOnce !=0)
                        {
                            PyLabel.setText(" Motor 2: "+ M2);                                                            
                        }                                                                        
                    }           
                };
                worker.execute();        
            }
        };
        
        /**
         * Swingworker thread responsible for updating the
         * Zlabel value
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
                    //Safely update the GUI from done()
                    protected void done()
                    { 
                        if(pressedOnce !=0)
                        {
                            PzLabel.setText(" Motor 3: "+ M3);                                                           
                        }
                    }
                };
                worker.execute();        
            }
        };
        
        /**
         * Swingworker thread responsible for updating the
         * platform state. true if stable, false if not stable
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
                                if(pressedOnce != 0)
                                {
                                status = get();
                                Platform.setText("Platform is stable: " + status);      
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
        
        /**
         * Swingworker thread responsible for 
         * handling a process for when stop button is pressed
         */
        stopButton.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                if(pressedOnce == 1)
                {
                    times.stop();
                    pressedOnce = 0;
                    PxLabel.setText(" Motor 1: ---");
                    PyLabel.setText(" Motor 2: ---");
                    PzLabel.setText(" Motor 3: ---");
                    Platform.setText("Platform GUI Stoped");
                }
            }
        });
             
        /**
         * Swingworker thread responsible for 
         * handling a process for when start button is pressed
         */
        startButton.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                if(pressedOnce == 0)
                {
                    pressedOnce += 1;
                    times.addActionListener(UDPlistener);
                    times.addActionListener(Xlabel);
                    times.addActionListener(Ylabel);
                    times.addActionListener(Zlabel);
                    times.addActionListener(PlatformState);
                    times.start();              
                }
            }
        });
    }
    
        /**
         * Swingworker thread responsible for 
         * handling the gathering process from the UDP.
         * Saves the recived variables in global variables
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
                        String data = new String(receiveData);
                        String [] splittedData = data.split(":",2);
                        
                        return splittedData; 

                    }
                    //Updates the global variables 
                    protected void done()
                    {
                        String guiData[];
                        try
                        {
                            {
                                guiData = get();
                                if(guiData[0].equals("M1"))
                                {
                                    M1 = guiData[1];
                                }
                                if(guiData[0].equals("M2"))
                                {
                                    M2 = guiData[1];
                                }
                                if(guiData[0].equals("M3"))
                                {
                                    M3 = guiData[1];
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
