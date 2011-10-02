/*
 *  Copyright Mattias Liljeson Sep 14, 2011
 */
package gameserver;

import common.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class ClientHandler implements Runnable{
    public final static String SERVER_HOSTNAME = "localhost";
    public final static int COMM_PORT = 5679;
    //ServerSocket servSock;
    private ArrayList<ClientConnection> clients; // TODO: Change impl to Map?
    private int numClients = 0;
    private GameServer server;
    private Channel channel;
    
    public ClientHandler(GameServer server){
//        try{
//            servSock = new ServerSocket(COMM_PORT);
//        }catch(IOException ignore){}
        channel = new Channel(COMM_PORT);
        
        this.server = server;
        clients = new ArrayList<ClientConnection>();
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
                ClientConnection clientConn = new ClientConnection(clientSock, this, numClients);
                clients.add(clientConn);
                numClients++;
                // TODO: Add a car for the client, fetch car color etc
                
                Thread thread = new Thread(clientConn);
                thread.start();
                clientSock = null;
            }

            System.out.println("Client has been served by ClientHandler. "
                    + "Now looking for new connections");
        }
    }
    
    public void pollClients(){
        for(ClientConnection client : clients){
            client.poll();
        }
    }
    
    public void sendRaceUpdate(RaceUpdate update){
        for(ClientConnection client : clients){
            client.sendRaceUpdate(update);
        }
    }
    
    public void updateKeyStates(int id, KeyStates keyStates){
        server.updateKeyStates(id, keyStates);
    }
}
