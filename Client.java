/*
 * 
 */
package Ass2;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * The Class Client.
 */
public class Client{
	
	/**
     * The main method.
     *
     * @param args the arguments
     */
	public static void main(String[] args) {	
		ClientChatUI client = new ClientChatUI("Tanishq's ClientChatUI");
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setMinimumSize(new Dimension(588,500));
		client.setResizable(false);
		client.setLocationByPlatform(true);
		client.getContentPane();
		EventQueue.isDispatchThread();
		client.setVisible(true);			
	}	
}
