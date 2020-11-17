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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.concurrent.ExecutionException;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * * The GUI class. Adds the labels and buttons to the GUI frame.
 * Aswell as assigning task to be excexuted by the SwingWorker threads
 * Gathers incomming data through UDP.
 * For our application it proved more beneficial to use UDP instead of TCP
 * @author stava
 */
public class GUI extends JFrame
{    
    
    JPanel panelTwo = new JPanel();
    //Create labels and buttons to GUI
    public JLabel image = new JLabel();
    String a = "test";
    
    private JLabel Platform = new JLabel("Platform is stable: ");
    private JLabel Motor1 = new JLabel(" Motor 1: ---");
    private JLabel Motor2 = new JLabel(" Motor 2: ---");
    private JLabel Motor3 = new JLabel(" Motor 3: ---");
    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stop");
    private JButton changePane = new JButton("Project information");
    private JButton goBack = new JButton("Return");
    private JLabel info = new JLabel("<html><br/> The goal of this project was to create a boat with an <br/> stabilized platform to counter the waves <br/> The points of the project was to explore <br/> and learn about real time programming and"
            + " <br/> how to implement threads and multi-threading in a thread safe way <br/><br/><br/> The group consists of four automation engineer students, Magnus Stava, <br/> Sophus Stokke Fredborg, Markus Grorud Gaasholt and Kaung Htet San <br/>"
            + "<br/> The project have three required points that has to be included <br/><br/> 1. Programmed in Object Oriented Java <br/> 2. Multi-threading,thread-safe and real-time <br/><br/><br/></html>");
    private JLabel intro = new JLabel("<html>This is the final project in subject IE303812<br/></html>");

    //private JLabel info = new JLabel("testing");
    
    
    //Global variables to store X, Y and Z values gathered from the UDP swingworker
    //Variables updated in the GUI through the other swingworkers
    String M1 = "";
    String M2 = "";
    String M3 = "";
    
    String M1old = "";
    String M2old = "";
    String M3old = "";
    
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
        BufferedImage img = ImageIO.read(new File("C:\\Users\\stava\\Downloads\\gave digge\\water_final.jpg"));
        JLabel main = new JLabel(new ImageIcon(img));
        getContentPane().add(panelTwo);
        setContentPane(main);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridwidth = GridBagConstraints.BOTH;
               
        //Font customization and size
        Platform.setFont(new Font("arial", Font.BOLD, 35));
        Motor1.setFont(new Font("serif", Font.BOLD, 35));
        Motor2.setFont(new Font("serif", Font.BOLD, 35));
        Motor3.setFont(new Font("serif", Font.BOLD, 35));
        info.setFont(new Font("Times new roman", Font.BOLD, 30));
        intro.setFont(new Font("serif", Font.BOLD, 40));
        startButton.setFont(new Font("serif",Font.BOLD,28));
        stopButton.setFont(new Font("serif",Font.BOLD,28));
        changePane.setFont(new Font("serif",Font.BOLD,35));
        goBack.setFont(new Font("serif",Font.BOLD,35));
        
        //Color customization for button and label
        startButton.setBackground(Color.green);
        stopButton.setBackground(Color.red);
        Motor1.setForeground(Color.black);
        Motor2.setForeground(Color.black);
        Motor3.setForeground(Color.black);
        Platform.setForeground(Color.black);
        changePane.setForeground(Color.black);
        intro.setForeground(Color.black);
        info.setForeground(Color.white);
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
        add(Motor1, gc);
                       
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 1;
        gc.weighty = 1;
        add(Motor2, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        add(Motor3, gc);
        
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
          
        gc.gridx = 0;
        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        panelTwo.add(intro, gc);
        
        gc.gridx = 0;
        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        panelTwo.add(info, gc);
        
        gc.gridx = 1;
        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 2;
        panelTwo.add(goBack, gc);

        gc.gridx = 2;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        add(changePane, gc);

        /**
         * Timer class from swing library.
         * Defines frequency for how often swingworkers should run
         * Updates every specified ms
         */
        Timer times = new Timer(500, (e) -> {  
        });
        
        /**
         * Swingworker thread responsible for keeping Motor 1 label updated. 
         * M1Label value to update the GUI with
         */
        ActionListener M1Label = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Boolean,String> worker = new SwingWorker<Boolean, String>() 
                {
                    @Override
                    //doInBackground task is given to a new thread
                    protected Boolean doInBackground() throws Exception 
                    {   
                        
                        return (!M1.equals(M1old));
                    }
                    //Safely update the GUI from done()
                    //done() is done from the Event Dispatch Thread (EDT)
                    protected void done()
                    {
                        boolean status;
                        try {
                            status = get();
                            if(pressedOnce !=0||status)
                            {
                                Motor1.setText(" Motor 1: "+ M1);
                                M1old = M1;
                            }                                                 
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
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
        ActionListener M2Label = new ActionListener() {
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
                            Motor2.setText(" Motor 2: "+ M2);                                                            
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
        ActionListener M3Label = new ActionListener() {
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
                            Motor3.setText(" Motor 3: "+ M3);                                                           
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
                    Motor1.setText(" Motor 1: ---");
                    Motor2.setText(" Motor 2: ---");
                    Motor3.setText(" Motor 3: ---");
                    Platform.setText("Platform GUI Stopped");
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
                    times.addActionListener(M1Label);
                    times.addActionListener(M2Label);
                    times.addActionListener(M3Label);
                    times.addActionListener(PlatformState);
                    times.start();              
                }
                
            }
        });
        
                /**
         * Swingworker thread responsible for 
         * handling a process for when stop button is pressed
         */
        changePane.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                if(pressedOnce == 1)
                {
                    times.stop();
                }
                    setContentPane(panelTwo);
                    panelTwo.setBackground(Color.GRAY);
                    setBackground(Color.yellow);
                    setSize(500, 500);
                    revalidate();
                    pack();
            }
        });
             
        /**
         * Swingworker thread responsible for 
         * handling a process for when start button is pressed
         */
        goBack.addActionListener(new ActionListener() 
        {        
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                if(pressedOnce == 1)
                {
                    times.start();
                }
                    setContentPane(main);
                    revalidate();
                    pack();
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
        
         /**
         * Swingworker thread for presentation purposes
         * GenericSwingWorker 
         */
        ActionListener GenericSwingWorker = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>() 
                {
                    @Override
                    protected Void doInBackground() throws Exception 
                    {   
                        //doInBackground task is given to a new thread
                        //Should be given more heavy work
                        return null;
                    }

                    @Override
                    protected void done()
                    {
                        //Safely update the GUI from done()
                        //done() is done from the Event Dispatch Thread (EDT)
                        //Should only be given lightweight work to avoid delays  
                    }           
                };
                worker.execute();        
            }
        };
}


