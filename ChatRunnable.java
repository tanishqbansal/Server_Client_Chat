/**
 * File Name : Calculator.java Author: Tanishq Bansal, #040883753 Course: CST8221 - JAP, Lab
 * Section: 304 Assignment: Assignment 2, Part 2 Date: 7th December, 2018 Professor: Daniel Cormier
 * Purpose: 
 */
package Ass2;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Class ChatRunnable.
 *
 * @param <T> the generic type
 */
public class ChatRunnable <T extends JFrame & Accessible> implements Runnable{
  
  /** The ui. */
  final T ui;
  
  /** The socket. */
  final Socket socket;
  
  /** The input stream. */
  final ObjectInputStream inputStream;
  
  /** The output stream. */
  final ObjectOutputStream outputStream;
  
  /** The display. */
  final JTextArea display;
  
  
  
  /**
   * Instantiates a new chat runnable.
   *
   * @param ui the ui
   * @param connection the connection
   */
  ChatRunnable (T ui, ConnectionWrapper connection){
    this.ui = ui;
    this.socket = connection.getSocket();
    this.display= ui.getDisplay();
    this.inputStream = connection.getInputStream();
    this.outputStream = connection.getOutputStream();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
    
    String strin = null;
     while(true) {
       LocalDateTime now = LocalDateTime.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M d, K:m a");
       String formatDateTime = now.format(formatter);
       if (socket.isClosed()) {
         try {
          strin= inputStream.readObject().toString() ;
          strin.trim();
          
          if(strin.equals(ChatProtocolConstants.CHAT_TERMINATOR)) {
            final String terminate= ChatProtocolConstants.DISPLACMENT+ formatDateTime +ChatProtocolConstants.LINE_TERMINATOR+strin;
            display.append(terminate);
            break;
            
          }else {
            final String append =ChatProtocolConstants.DISPLACMENT+ formatDateTime +ChatProtocolConstants.LINE_TERMINATOR+strin;
            display.append(append);
          }
          
        } catch (ClassNotFoundException e) {
          
          e.printStackTrace();
        } catch (IOException e) {
          
          e.printStackTrace();
        }
        }else {
          break;
       }
       
       
     }
     try {
      outputStream.writeObject(ChatProtocolConstants.DISPLACMENT+ChatProtocolConstants.CHAT_TERMINATOR+ChatProtocolConstants.LINE_TERMINATOR);
    } catch (IOException e) {
      e.printStackTrace();
    }
     ui.closeChat();
  }

}
