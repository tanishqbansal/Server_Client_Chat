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

/**
 * The Class ConnectionWrapper.
 */
public class ConnectionWrapper {

    /** The output stream. */
    ObjectOutputStream outputStream ;
    
    /** The input stream. */
    ObjectInputStream inputStream;
    
    /** The socket. */
    Socket socket;
    
    /**
     * Instantiates a new connection wrapper.
     *
     * @param socket the socket
     */
    ConnectionWrapper(Socket socket){   
        this.socket =socket;
    }
    
    /**
     * Gets the socket.
     *
     * @return the socket
     */
    Socket getSocket() {
        return socket;
        }
    
    /**
     * Gets the output stream.
     *
     * @return the output stream
     */
    ObjectOutputStream getOutputStream() {
        return outputStream;
        }
    
    /**
     * Gets the input stream.
     *
     * @return the input stream
     */
    ObjectInputStream getInputStream() {
        return inputStream;
        }
    
    /**
     * Creates the object I streams.
     *
     * @return the object input stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    ObjectInputStream createObjectIStreams() throws IOException {
         inputStream = new ObjectInputStream(socket.getInputStream());  
        return inputStream;
        
    }
    
    /**
     * Creates the object O streams.
     *
     * @return the object output stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    ObjectOutputStream createObjectOStreams() throws IOException{
         outputStream = new ObjectOutputStream(socket.getOutputStream());
        return outputStream;
        
    }
    
    /**
     * Creates the streams.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void createStreams() throws IOException{
     createObjectOStreams();
     createObjectIStreams();
        
    }
    
    /**
     * Close connection.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void closeConnection()throws IOException{
        
        if(outputStream!= null) {
            outputStream.close();
        }if(inputStream!= null) {
            outputStream.close();
        }
        if(!socket.isClosed()) {
            socket.close();
        }
        
    }
}