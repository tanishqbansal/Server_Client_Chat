/**
 * File Name : Calculator.java Author: Tanishq Bansal, #040883753 Course: CST8221 - JAP, Lab
 * Section: 304 Assignment: Assignment 2, Part 2 Date: 7th December, 2018 Professor: Daniel Cormier
 * Purpose: 
 */
package Ass2;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

/**
 * The Class Server.
 */
public class Server{
	
  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    int port = 65533;
    int friend = 0;
    Socket socket;
    if (args.length > 0) {
        port = Integer.parseInt(args[0]);
        System.out.println("Using port: " + port);
    } else {
        System.out.println("Using default port: " + port);
    }
    while (true) {
        final String title = "Tanishq Friend's " + friend;
        try {
            ServerSocket s = new ServerSocket(port);
            socket = s.accept();

            if (socket.getSoLinger() != -1)
                socket.setSoLinger(true, 5);
            if (!socket.getTcpNoDelay())
                socket.setTcpNoDelay(true);
            friend++;

            launchClient(socket, title);

        } catch (IOException e) {
          e.getStackTrace();
          return;
        }
    }

}

	/**
     * Launch client.
     *
     * @param socket the socket
     * @param title the title
     */
	public static void launchClient(Socket socket, String title) {
		ServerChatUI client = new ServerChatUI(socket, title);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setLocationRelativeTo(null);
		client.getContentPane();
		EventQueue.isDispatchThread();
		client.setVisible(true);	
	}
}
