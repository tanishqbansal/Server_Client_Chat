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
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

/**
 * The Class ServerChatUI.
 */
public class ServerChatUI extends JFrame implements Accessible {

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
   * Instantiates a new server chat UI.
   *
   * @param socket the socket
   * @param name the name
   */
  public ServerChatUI(Socket socket, String name) {
    super(name);
    setFrame(createUI());
    runClient();
  }

  /**
   * Run client.
   */
  private void runClient() {
    connection = new ConnectionWrapper(socket);
    try {
      connection.createStreams();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    outputStream = connection.outputStream;
    Runnable runnable = new ChatRunnable<ServerChatUI>(this, connection);
    Thread thread = new Thread(runnable);
    thread.start();
    createUI();
    
  }

  /**
   * Sets the frame.
   *
   * @param panel the new frame
   */
  public final void setFrame(JPanel panel) {
    setContentPane(panel);
    setMinimumSize(new Dimension(588, 500));
    setResizable(false);
  }

  /**
   * Creates the UI.
   *
   * @return the j panel
   */
  public JPanel createUI() {

    JPanel panel2 = new JPanel();
    panel2.setLayout(new BorderLayout());

    JPanel connectionPanel = new JPanel();
    connectionPanel.setLayout(new GridLayout(2, 0));

    JPanel messagePanel = new JPanel(new FlowLayout());
    JPanel chatDisplayPanel = new JPanel(new BorderLayout());



    TitledBorder b = new TitledBorder("MESSAGE");
    b.setBorder(new MatteBorder(10, 10, 10, 10, Color.BLACK));
    TitledBorder bl = new TitledBorder("CHAT DISPLAY");
    bl.setBorder(new MatteBorder(10, 10, 10, 10, Color.BLUE));
    bl.setTitleJustification(TitledBorder.CENTER);
    messagePanel.setBorder(b);
    chatDisplayPanel.setBorder(bl);

    // JPanel messageInnerPanel = new JPanel(new FlowLayout());
    JButton sendButton = new JButton("Send");
    sendButton.setMnemonic('S');
    sendButton.setPreferredSize(new Dimension(88, 20));
    JTextField messageField = new JTextField("Type message", 40);
    messageField.setMargin(new Insets(0, 0, 0, 5));
    messageField.setCaretPosition(0);

    messagePanel.add(messageField);
    messagePanel.add(sendButton);
    messagePanel.setPreferredSize(new Dimension(30, 65));
    // messagePanel.add(messageInnerPanel);

    JTextArea text = new JTextArea();
    text.setRows(30);
    text.setColumns(45);
    text.setEditable(false);
    JScrollPane scroll = new JScrollPane(text);
    // scroll.setHorizontalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    chatDisplayPanel.add(scroll);

    panel2.add(messagePanel, BorderLayout.NORTH);
    panel2.add(chatDisplayPanel);


    return panel2;
  }

  /**
   * The Class WindowController.
   */
  private class WindowController extends WindowAdapter {
    
    /**
     * Window closing.
     */
    public void windowClosing() {
      display.setText("ServerUI Window closing!");
      try {
        outputStream.writeObject(ChatProtocolConstants.DISPLACMENT+ChatProtocolConstants.CHAT_TERMINATOR + ChatProtocolConstants.LINE_TERMINATOR);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }finally {
        display.setText("Closing Chat!");
        dispose();
        display.setText("Chat Closed!");
      }
   
      
    }
    
    /**
     * Window closed.
     */
    public void windowClosed() {
      display.setText("Server UI closed!");
    }
  }

  /**
   * The Class Controller.
   */
  private class Controller implements ActionListener {

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {


    }
    
    /**
     * Send.
     */
    private void send(){
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

  }
  
 

}
