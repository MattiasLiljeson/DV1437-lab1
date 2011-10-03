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
    
    private RaceCourse raceCourse;
    private GUI gui;
    private Channel channel;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
    
    public void run(){
        gui = new GUI();
        //while(!gui.isReadyToPlay()){
            // Wait for user to press the "Play!" button
        //}
        gui.doWait();
        
        String hostname = gui.getHostname();
        String playerName = gui.getPlayerName();
        String carColor = gui.getCarColor();
        
        channel = new Channel(hostname, PORT_RCS);
        fetchRaceCourse(hostname, PORT_RCS);
        
        channel = new Channel(hostname, PORT_GS);
        if(channel.connect()){
            channel.openStreams();
            gameLoop();
        }
    }
    
    public boolean fetchRaceCourse(String hostname, int port){
        boolean success = true;
        channel.connect();
        channel.openStreams();
        raceCourse = (RaceCourse)channel.readObject();
        
        if(raceCourse != null){
            System.out.println("Received payload:");
            System.out.println(this.raceCourse.toString());
            success = true;
        }
        
        channel.closeStreams();
        channel.closeSockets();
        return success;
    }

    private void gameLoop() {
        Object object = null;
        while(true){
            object = channel.readObject();
            if( object != null){
                if(object instanceof RaceUpdate){
                    gui.update((RaceUpdate)object);
                    System.out.println("RaceUpdate received");
                }
                else if(object instanceof KeyStatesReq){
                    System.out.println("KeyStatesReq received");
                    KeyStates keyStates =  gui.getKeyStates();
                    channel.sendObject(keyStates);
                }
            }
        }
    }  
}
