/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlyboatgui;

/**
 *
 * @author stava
 */
public class OnlyBoatGUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    // create a common resource oneWriter
    ThreadStarter oneWriter = new ThreadStarter();
    
    // create 5 threads
    DataWriter writer1 = new DataWriter(oneWriter, 1);
    DataWriter writer2 = new DataWriter(oneWriter, 2);
    DataWriter writer3 = new DataWriter(oneWriter, 3);
    
    // and start the threads (Java will call their run()-method)
    writer1.start();
    writer2.start();
    writer3.start();
  }
}
    

