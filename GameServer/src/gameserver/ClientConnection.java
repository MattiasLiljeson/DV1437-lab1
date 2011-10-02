/*
 *  Copyright Mattias Liljeson Sep 14, 2011
 */
package gameserver;

import common.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class ClientConnection implements Runnable{
    private Channel channel;
    private ClientHandler clientHandler;
    private int id;
    private boolean done = false;

    public ClientConnection(Socket socket, ClientHandler clientHandler, int id){
        channel = new Channel(socket);
        channel.openStreams();
        this.clientHandler = clientHandler;
        this.id = id;

        System.out.println("ClientConnection started");
    }

    @Override
    public void run(){
        KeyStates keyStates = null;
        while(!done){
            //Fetch keystates
            keyStates = (KeyStates)channel.readObject();

            if(keyStates != null){
                System.out.println("Received payload:");
                System.out.println(keyStates.toString());
                //Send em' up to the top
                clientHandler.updateKeyStates(id, keyStates);
            }
        }
    }

    public boolean close(){
        boolean success = true;
        done = true;

        // HACK: Wait for the objects own thread to end its while-loop.
        // TODO: Use join instead?
        try{
            Thread.sleep(1000); 
        }catch(InterruptedException ignore){}
        channel.closeStreams();

        // Leave time for the client to close its streams
        try{
            Thread.sleep(1000); 
        }catch(InterruptedException ignore){}
        channel.closeSockets();

        return success;
    }

    public boolean poll(){
        return channel.sendObject(new KeyStatesReq());
    }

    public boolean sendRaceUpdate(RaceUpdate update){
        return channel.sendObject(update);
    }
}
