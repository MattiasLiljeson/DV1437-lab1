/*
 *  Copyright Mattias Liljeson Sep 14, 2011
 */
package gameserver;

import common.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class GameServer {
    private int pollingRate = 60;
    private Map<Integer,Car> cars;
    private boolean done = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Game Server started");
        
        GameServer gameServ = new GameServer();
        gameServ.run(); 
    }
    
    public GameServer(){
        cars = new HashMap<Integer,Car>();
    }
    
    public void run(){
        //Create clientHandlerThread thread to handle connection with clients
        ClientHandler clientHandler = new ClientHandler(this);
        Thread clientHandlerThread = new Thread(clientHandler);
        clientHandlerThread.start();
        
        //Create ExitListener thread to listen to exit message from std input
        Thread exitListenerThread = new Thread(new ExitListener());
        exitListenerThread.start();
        
        //Enter main loop
        long prevTime = System.currentTimeMillis();
        while(!done){
            //Poll clients, update, send new positions to clients
            //DEBUG: test algorithm
            double tmp1 = System.currentTimeMillis() - prevTime;
            double tmp2 = (1000/pollingRate);
            boolean tmp3 = (prevTime - System.currentTimeMillis()) > (1000/pollingRate);
            if((System.currentTimeMillis() - prevTime) > (1000/pollingRate)){
                clientHandler.pollClients();
                
                RaceUpdate update = new RaceUpdate(cars.size());
                for(Map.Entry<Integer, Car> entry : cars.entrySet()){
                    Car car = entry.getValue();
                    car.update(pollingRate);
                    update.addCar(car.getPosition(), car.getColor());
                }
                clientHandler.sendRaceUpdate(update);
            }
        }
        System.exit(0);
    }
    public void addCar(int id, Car car){
        cars.put(id,car);
    }
    
    
    public void updateKeyStates(int id, KeyStates keyStates){
        Car car = cars.get(id);
        if(car != null){
            car.setKeyStates(keyStates);
            // TODO: throw exception?
        }
    }
    
    private class ExitListener implements Runnable{

        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msg = "";
            
            // Handle exit msg
            try{
                msg = br.readLine();
            }catch(IOException ignore){}
            if(msg.equals("exit")){
                System.out.println("Exiting from gs");
                done = true;
            }
        }
        
    }
    
}
