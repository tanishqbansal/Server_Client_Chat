/**
 * File Name : Calculator.java Author: Tanishq Bansal, #040883753 Course: CST8221 - JAP, Lab
 * Section: 304 Assignment: Assignment 2, Part 2 Date: 7th December, 2018 Professor: Daniel Cormier
 * Purpose: 
 */
package Ass2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

/**
 * The Class ClientChatUI.
 */
public class ClientChatUI extends JFrame implements Accessible{
  
  /** The message. */
  JTextField message;
  
  /** The send button. */
  JButton sendButton;
  
  /** The display. */
  JTextArea display;
  
  /** The output stream. */
  ObjectOutputStream outputStream;
  
  /** The socket. */
  Socket socket;
  
  /** The connection. */
  ConnectionWrapper connection;
  
  
	/**
     * Instantiates a new client chat UI.
     *
     * @param name the name
     */
	public ClientChatUI(String name) {
		super(name);
		runClient();
	}
	
	/**
     * Run client.
     */
	private void runClient() {
		setContentPane(createClientUI());
		//adds the window listener to the frame.
	}
	
	/**
     * Creates the client UI.
     *
     * @return the j panel
     */
	public JPanel createClientUI() {
	  
		
		String portNumbers[] = {"", "8089", "65000", "65535"};
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		
		JPanel connectionPanel = new JPanel();
		connectionPanel.setLayout(new GridLayout(2,0));

		JPanel messagePanel = new JPanel(new FlowLayout());
		JPanel chatDisplayPanel = new JPanel(new BorderLayout());
		
		JPanel hostPanel = new JPanel();
		hostPanel.setLayout(new BorderLayout());
		JPanel portPanel = new JPanel();
		portPanel.setLayout(new BorderLayout());
	
		//connectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red, 10,true), "CONNECTION"));
		TitledBorder r = new TitledBorder("CONNECTION");
		r.setBorder(new MatteBorder(10,10,10,10,Color.RED));
		TitledBorder b = new TitledBorder("MESSAGE");
		b.setBorder(new MatteBorder(10,10,10,10,Color.BLACK));
		TitledBorder bl = new TitledBorder("CHAT DISPLAY");
		bl.setBorder(new MatteBorder(10,10,10,10,Color.BLUE));
		bl.setTitleJustification(TitledBorder.CENTER);
		connectionPanel.setBorder(r);
		messagePanel.setBorder(b);
		chatDisplayPanel.setBorder(bl);
		//messagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 10,true), "MESSAGE"));
		//chatDisplayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.blue, 10,true), "CHAT DISPLAY", TitledBorder.CENTER,TitledBorder.DEFAULT_POSITION));
		
		JLabel hostLabel = new JLabel("Host:");
		hostLabel.setDisplayedMnemonic('H');
	
		
		hostLabel.setPreferredSize(new Dimension(35,30));
		JTextField hostField = new JTextField();
		hostField.setColumns(45);
		hostField.setText("localhost");
		hostField.setBackground(Color.WHITE);
		hostField.setEditable(true);
		hostField.setAlignmentX(LEFT_ALIGNMENT);
		hostLabel.setLabelFor(hostField);
	    hostField.setMargin(new Insets(0, 5, 0, 5));
	    hostField.setCaretPosition(0);
		JPanel p = new JPanel(new FlowLayout());
		p.add(hostField);
		
		JLabel portLabel = new JLabel("Port:");
		
		portLabel.setDisplayedMnemonic('P');
		portLabel.setPreferredSize(new Dimension(35,30));
		JComboBox portBox = new JComboBox(portNumbers);
		portBox.setPreferredSize(new Dimension(100,20));
		portBox.setBackground(Color.WHITE);
		portBox.setEditable(true);
		
		portBox.setBounds(5, 5,0,0);
		portLabel.setLabelFor(portBox);
		JButton connectButton = new JButton("Connect");
		connectButton.setPreferredSize(new Dimension(100,20));
		connectButton.setMnemonic('C');
		connectButton.setBackground(Color.RED);

		p.add(hostLabel, FlowLayout.LEFT);
		hostPanel.add(p);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(portLabel);
		p2.add(portBox);
		p2.add(connectButton);
		portPanel.add(p2, BorderLayout.WEST);
		
		
		connectionPanel.add(hostPanel);
		connectionPanel.add(portPanel);
		
		//JPanel messageInnerPanel = new JPanel(new FlowLayout());
		JButton sendButton = new JButton("Send");
		sendButton.setMnemonic('S');
		sendButton.setPreferredSize(new Dimension(82,20));
		sendButton.setEnabled(false);
		JTextField messageField = new JTextField("Type message",41);
		messageField.setEditable(true);
		messageField.setBackground(Color.WHITE);
		messageField.setAlignmentX(LEFT_ALIGNMENT);
		
		messagePanel.add(messageField);
		messagePanel.add(sendButton);
		messagePanel.setPreferredSize(new Dimension(30,65));
		//messagePanel.add(messageInnerPanel);
		
		JTextArea text = new JTextArea();
		text.setRows(30);
		text.setColumns(45);
		text.setEditable(false);
		JScrollPane scroll = new JScrollPane(text);
		//scroll.setHorizontalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatDisplayPanel.add(scroll);
		
		panel2.add(messagePanel, BorderLayout.NORTH);
		panel2.add(chatDisplayPanel);
		panel.add(connectionPanel, BorderLayout.NORTH);
		panel.add(panel2);
		
		
		return panel;
	}
	
	/**
     * The Class WindowController.
     */
	private class WindowController extends WindowAdapter {	
		
		/**
         * Window closing.
         */
		public void windowClosing() {
			System.exit(0);
		}
		
	}
	
	/**
     * The Class Controller.
     */
	private class Controller implements ActionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
		}
		
		/**
         * Send.
         */
		public void send() {
		  String sendMessage = message.getText()+"\n";
	      try {
	         outputStream.writeObject(ChatProtocolConstants.DISPLACMENT
	                  + sendMessage
	                  +ChatProtocolConstants.LINE_TERMINATOR);
	     } catch (IOException e) {
	         display.setText(e.getMessage());            
	     }
	 }
		  
		  
		}

  /* (non-Javadoc)
   * @see Ass2.Accessible#getDisplay()
   */
  @Override
  public JTextArea getDisplay() {
    // TODO Auto-generated method stub
    return display;
  }

  /* (non-Javadoc)
   * @see Ass2.Accessible#closeChat()
   */
  @Override
  public void closeChat() {
    try {
      connection.closeConnection();
      dispose();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    enableConnectButton();
    
  }
	
	/**
     * Enable connect button.
     */
	public void enableConnectButton() {
	  
	}
}
