/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package client;

import common.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class Client {
    public final static String SERVER_HOSTNAME = "localhost";
    public final static int PORT_RCS = 5678;
    public final static int PORT_GS = 5679;
    
    private Socket socket;
    private RaceCourse raceCourse;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
    
    public void run(){
        GUI gui = new GUI();
        while(!gui.isDone()){
            // Do nothing
        }
        String hostname = gui.getHostname();
        String playerName = gui.getPlayerName();
        String carColor = gui.getCarColor();
        fetchRaceCourse(hostname, PORT_RCS);
        if(connectToServer(SERVER_HOSTNAME, PORT_GS)){
            gameLoop();
        }
    }
    
    public boolean connectToServer(String hostName, int port){
        boolean success = false;
        try{
            this.socket = new Socket(hostName, port);
            
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
            
            success = true;
        }
        catch (UnknownHostException ex){
            System.out.println("Host: " + hostName + " doesn't exist");
        }
        catch(IOException ex){
            System.out.println("Couldn't connect to: " + hostName + " : " + port);
            ex.printStackTrace();
        }
        return success;
    }
    
    public void closeConnection(){
        try{
            inStream.close();
            outStream.close();
            socket.close();
        }catch(IOException ex){
            System.out.println("Couldn't close socket. Has it been opened?");
        }
    }
    
    public boolean fetchRaceCourse(String hostname, int port){
        boolean success = false;
        if(connectToServer(hostname, port)){
            try{
                raceCourse = (RaceCourse)inStream.readObject();
            }
            catch(ClassNotFoundException ex)
            {
                System.out.println("Other Class than RaceCourse received, " + ex);
            }
            catch(IOException ex){
                System.out.println("Problem with socket input");
            }

            if(raceCourse != null){
                System.out.println("Received payload:");
                System.out.println(this.raceCourse.toString());
                success = true;
            }
            closeConnection();
        }
        return success;
    }

    private void gameLoop() {
        Object tmp = null;
        while(true){
            try{
                tmp = inStream.readObject();
            }
            catch(ClassNotFoundException ex)
            {
                System.out.println("Other Class than RaceCourse received, " + ex);
            }
            catch(IOException ex){
                System.out.println("Problem with socket input");
            }
            if( tmp != null){
                if(tmp instanceof RaceUpdate){
                    //TODO: update view
                }
                else if(tmp instanceof SendKeyStates){
                    //TODO: send keystates
                }
            }
        }
    }
    
    private void parse(String msg){
        if(msg.contains("returnKeys"))
            returnKeys();
    }

    private void returnKeys() {
        
    }
}
