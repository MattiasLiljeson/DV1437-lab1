/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package client;

import common.*;
import java.io.*;
import java.net.*;
import javax.swing.ImageIcon;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class Client {
    public final static String SERVER_HOSTNAME = "localhost";
    public final static int PORT_RCS = 5678;
    public final static int PORT_GS = 5679;
    
    private RaceCourse raceCourse;
    private IntroFrame introGUI;
	private GameFrame gameGUI;
    private Channel channel;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
    
    public void run(){
        introGUI = new IntroFrame();
        //while(!gui.isReadyToPlay()){
            // Wait for user to press the "Play!" button
        //}
        introGUI.doWait();
        
        String hostname = introGUI.getHostname();
        String playerName = introGUI.getPlayerName();
        String carColor = introGUI.getCarColor();
        
		introGUI.dispose();
		introGUI = null;
		
        channel = new Channel(hostname, PORT_RCS);
        fetchRaceCourse(hostname, PORT_RCS);
        ImageIcon raceCourseImg = raceCourse.raceCourseImg;
        gameGUI = new GameFrame(raceCourseImg);
        
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
                    gameGUI.update((RaceUpdate)object);
                    System.out.println("RaceUpdate received");
                }
                else if(object instanceof KeyStatesReq){
                    System.out.println("KeyStatesReq received");
                    KeyStates keyStates =  gameGUI.getKeyStates();
                    channel.sendObject(keyStates);
                }
            }
        }
    }  
}
