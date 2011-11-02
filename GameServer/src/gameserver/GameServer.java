/*
 *  Copyright Mattias Liljeson Sep 14, 2011
 */
package gameserver;

import common.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class GameServer {
    private double framesPerSecond = 1337;
    private Map<Integer,Car> cars;
    private boolean done = false;
    private final Object lockCars = new Object();
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
        // Create clientHandlerThread thread to handle connection with clients
        ClientHandler clientHandler = new ClientHandler(this);
        Thread clientHandlerThread = new Thread(clientHandler);
        clientHandlerThread.start();
        
        // Create ExitListener thread to listen to exit message from std input
        Thread exitListenerThread = new Thread(new ExitListener());
        exitListenerThread.start();
        
        // Fetch racecourse
        // TODO: Remove hard coding of host and port below
        RaceCourse raceCourse = fetchRaceCourse("localhost", 5678);
        
        // TODO: isolate ugly code below
        Image img = raceCourse.frictionMaskImg.getImage();
        BufferedImage buffImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = buffImg.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        
        // Enter main loop
        long prevTime = System.currentTimeMillis();
		double timeSinceLastFrame = 0;
		double timePerFrame = 1.0 / framesPerSecond;
        while(!done){
			
            //Poll clients, update, send new positions to clients
            
			/*
            //DEBUG: test algorithm
            double tmp1 = System.currentTimeMillis() - prevTime;
            double tmp2 = (1000/pollingRate);
            boolean tmp3 = (prevTime - System.currentTimeMillis()) > (1000/pollingRate);
            //!DEBUG
            */
			long currentTime = System.currentTimeMillis();
			timeSinceLastFrame += (currentTime - prevTime)/1000.0;
			prevTime = currentTime;
			
            if(timeSinceLastFrame > timePerFrame){
                clientHandler.pollClients();
                
				HashMap<Integer, CarUpdate> carUpdates = new HashMap<Integer, CarUpdate>();
				int i = 0;
				synchronized(lockCars) {
					for(Map.Entry<Integer, Car> entry : cars.entrySet()){
						Car car = entry.getValue();
						if(car == null)
							continue;
						
						//if(cars.size() > 1) //TODO: add 2player limit
							car.update(timeSinceLastFrame, buffImg, raceCourse.checkpoints);
						carUpdates.put(entry.getKey(), car.getCarUpdate());
						i++;
					}
				}
				RaceUpdate update = new RaceUpdate(carUpdates);
				clientHandler.sendRaceUpdate(update);
                
				//removed stuff that was flagged for removal
				removeFlaggedCars();
				clientHandler.removeFlaggedClients();
				
				timeSinceLastFrame = 0;
            }
        }
        System.exit(0);
    }
	
    public void addCar(int id, Car car){
		synchronized(lockCars) {
			cars.put(id,car);
		}
    }
			
	public void flagCarForRemoval(int id) {
		synchronized(lockCars) {
			cars.put(id, null);
		}
	}
	
	public void removeFlaggedCars() {
		synchronized(lockCars) {
			ArrayList<Integer> IDsToRemove = new ArrayList<Integer>(cars.size());
			for(Map.Entry<Integer, Car> entry : cars.entrySet()){
				if(entry.getValue() == null)
					IDsToRemove.add(entry.getKey());
			}
			for(Integer ID : IDsToRemove){
				cars.remove(ID);
			}
		}
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
                System.out.println("Exiting from game server");
                done = true;
            }
        }
        
    }
    
    public RaceCourse fetchRaceCourse(String hostname, int port){
        Channel channel = new Channel(hostname, port);
        RaceCourse raceCourse = null;
        if(channel.connect()) {
            if(!channel.openStreams())
                System.out.println("Failed to open streams to map server");

            try{
                raceCourse = (RaceCourse)channel.readObject();
            }catch(Channel.ConnectionLostException ex){
                raceCourse = null;
            }
        
            channel.closeStreams();
            channel.closeSockets();
        } else {
            System.out.println("Failed to connect to map server");
        }
        
        return raceCourse;
    }
    
}
