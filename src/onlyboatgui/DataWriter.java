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
import java.lang.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 
 * @author stava
 */
public class DataWriter extends Thread{
  //some variables
  private ThreadStarter writer;
  private int threadNo;

  
  public DataWriter(ThreadStarter writer,int thredNo){
      writer = this.writer;
      threadNo = this.threadNo;
  }
      

  /**
   * run() overrides the Thread-class' run()-method
   */
  public void run() {
      for (int i = 0; i < 20; i++)
      {
      
        System.out.print("Nice");
        System.out.print("Message from"+this.writer+"\n"); // whitespace in between each number
      }
  }
    

}



