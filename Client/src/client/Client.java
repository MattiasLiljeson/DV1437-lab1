/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package client;

import common.*;
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
        ClientData clientData = new ClientData(introGUI.getPlayerName(),introGUI.getCarColor());
		introGUI.dispose();
		introGUI = null;
		
        raceCourse =  fetchRaceCourse(hostname, PORT_RCS);
        gameGUI = new GameFrame(raceCourse);
        
        channel = new Channel(hostname, PORT_GS);
        if(channel.connect()){
            channel.openStreams();
            
            boolean success = true;
            try{
                channel.sendObject(clientData);
            }catch(Channel.ConnectionLostException ex){
                System.out.println("Problem with the connection, couldn't send init message");
                success = false;
            }
            
            if(success)
                gameLoop();
        }
    }
    
    public RaceCourse fetchRaceCourse(String hostname, int port){
        Channel channel = new Channel(hostname, port);
        RaceCourse raceCourse;
        channel.connect();
        channel.openStreams();
        try{
            raceCourse = (RaceCourse)channel.readObject();
        }catch(Channel.ConnectionLostException ex){
            raceCourse = null;
        }
        
        channel.closeStreams();
        channel.closeSockets();
        return raceCourse;
    }

    private void gameLoop() {
        boolean done = false;
        Object object = null;
        while(!done){
            try{
                object = channel.readObject();
            }catch(Channel.ConnectionLostException ex){
                done = true;
            }
            
            if( object != null){
                if(object instanceof RaceUpdate){
                    gameGUI.update((RaceUpdate)object);
                }
                else if(object instanceof KeyStatesReq){
					KeyStates keyStates =  gameGUI.getKeyStates();
					try{
						synchronized(gameGUI.lockKeyStates) {
							channel.sendObject(keyStates);
						}
					}catch(Channel.ConnectionLostException ex){
						done = true;
					}
                }
            }
        }
    }  
}
