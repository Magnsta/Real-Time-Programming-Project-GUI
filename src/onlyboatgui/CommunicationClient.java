/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlyboatgui;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stava
 */
public class CommunicationClient 
{
    
   public static void main(String args[]) throws Exception
   {
        try 
        {
            DatagramSocket serverSocket = new DatagramSocket(1600);
            byte[] receiveData = new byte[10];
            byte[] sendData = new byte[10];
            while(true)
            {        
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                receiveData = receivePacket.getData();
                Byte a = receiveData[0];
                int b = a.intValue();
                System.out.println("The int value is: \n"+b);
                
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                
                sendData[0] = a;
                DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port);
            
                serverSocket.send(sendPacket);        
            }
        } 
        catch (SocketException ex) 
        {
            Logger.getLogger(CommunicationClient.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
           Logger.getLogger(CommunicationClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
    * 
    * @return 
    */   
    public boolean getPlatformState()
    {
        Random rand = new Random();
        int b = rand.nextInt(10);
        if(b>4)
        {
            return true; 
        }     
        return false;
    }
    
    /**
     * 
     * @return 
     */
    public int getPlatformX()
    {
        int a = 2; 
        return a; 
    }

    /**
     * 
     * @return 
     */
    public int getPlatformY()
    {
        int b = 2; 
        return b; 
    }


    /**
     * 
     * @return 
     */
    public int getPlatformZ()
    {
        int c = 2; 
        return c; 
    }    
}

    

