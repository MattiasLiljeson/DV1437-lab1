/*
 *  Copyright Mattias Liljeson Sep 14, 2011
 */
package gameserver;

import common.*;
import java.awt.Color;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class ClientHandler implements Runnable{
    public final static String SERVER_HOSTNAME = "localhost";
    public final static int COMM_PORT = 5679;
    //ServerSocket servSock;
    private HashMap<Integer, ClientConnection> clientConnections; // must be a map, not ArrayList, since removing clients from an array list would fuck up id mapping.
    private int nextClientID = 0; //Client ID counter. Increases with every connected client. Cannot use clients.size() since it decreases when clients are removed. We want a unique number.
    private GameServer server;
    private Channel channel;
    
    public ClientHandler(GameServer server){
//        try{
//            servSock = new ServerSocket(COMM_PORT);
//        }catch(IOException ignore){}
        channel = new Channel(COMM_PORT);
        
        this.server = server;
        clientConnections = new HashMap<Integer, ClientConnection>();
        System.out.println("ClientHandler started");
    }
    
    @Override
    public void run() {
        // Wait for clients to connect.
        // When a client has connected, create a new ClientConnection
        Socket clientSock = null;
        channel.startServer();
        
        while(true){
            clientSock = channel.accept();
            System.out.println("A new client has connected");

            if(clientSock != null){
                ClientConnection clientConn = new ClientConnection(clientSock, this, nextClientID);
                
                nextClientID++;

                Thread thread = new Thread(clientConn);
                thread.start();
                clientSock = null;
            }

            System.out.println("Client has been served by ClientHandler. "
                    + "Now looking for new connections");
        }
    }
    
    public boolean addClient(int id, ClientConnection conn, Car car){
        boolean success = false;
        synchronized(this) {
            if(clientConnections.get(id) == null) {
                server.addCar(id, car);
                clientConnections.put(id, conn);
                success = true;
            }
        }
        return success;
    }
    
    public void pollClients(){
        synchronized(this) {
            for(Integer clientID : clientConnections.keySet()){
				if(clientConnections.get(clientID) != null)
					clientConnections.get(clientID).poll();
            }
        }
    }
    
    public boolean flagClientForRemoval(int id) {
        boolean result = false;
        synchronized(this) {
            clientConnections.put(id, null);
        }
		server.flagCarForRemoval(id);
        return result;
    }
    
	public void removeFlaggedClients() {
        synchronized(this) {
			ArrayList<Integer> IDsToRemove = new ArrayList<Integer>(clientConnections.size());
			for(Map.Entry<Integer, ClientConnection> entry : clientConnections.entrySet()){
				if(entry.getValue() == null)
					IDsToRemove.add(entry.getKey());
			}
			for(Integer ID : IDsToRemove){
				clientConnections.remove(ID);
			}
		}
	}
	
    public void sendRaceUpdate(RaceUpdate update){
        synchronized(this) {
            for(Integer clientID : clientConnections.keySet()){
				update.clientID = clientID; //set the current clientID so that the client can know it's own ID
				if(clientConnections.get(clientID) != null) {
					clientConnections.get(clientID).sendRaceUpdate(update);
				}
            }
        }
    }
    
    public void updateKeyStates(int id, KeyStates keyStates){
        server.updateKeyStates(id, keyStates);
    }
}
