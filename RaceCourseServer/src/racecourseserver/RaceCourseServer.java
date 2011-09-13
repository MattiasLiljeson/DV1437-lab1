/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package racecourseserver;

import common.*;
import java.io.*;
import java.net.*;


/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class RaceCourseServer {
    public final static String SERVER_HOSTNAME = "localhost";
    public final static int COMM_PORT = 5678;
    ServerSocket servSock;
    RaceCourse raceCourse;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Race Course Server started");
        
        RaceCourseServer raceCourseServ = new RaceCourseServer();
        raceCourseServ.run(); 
    }
    
    public RaceCourseServer(){
        // DEBUG:
        //System.out.println("Constructor entered");
        try{
            servSock = new ServerSocket(COMM_PORT);
        }catch(IOException ignore){
            //ignore
        }
        
        //TODO: fix so that racecourse works as it should.
        raceCourse = RaceCourse.getInstance();
    }
    
    public void run(){
        //Create accepter thread to handle new map requests
        Thread accepter = new Thread(new RaceCourseAccepter());
        accepter.start();
//        try{
//            accepter.join();
//        }catch(InterruptedException ignore){}
        
        //Enter main loop which listens to the 'exit' signal
        boolean done = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while(!done){
            try{
                input = br.readLine();
            }catch(IOException ignore){}
            
            if(input.equals("exit")){
                System.out.println("Exiting from rcs");
                done = true;
            }
        }
        System.exit(0);
    }
    private class RaceCourseAccepter implements Runnable{

        @Override
        public void run() {
            Socket clientSock = null;
            while(true){
                try{
                    clientSock = servSock.accept();
                }catch(IOException ex){
                    System.out.println("Error when waiting for connection");
                    ex.printStackTrace();
                }

                System.out.println("A new client has connected");

                if(clientSock != null){
                    Thread thread = new Thread(new RaceCourseSend(clientSock, raceCourse));
                    thread.start();
                    clientSock = null;
                }

                System.out.println("Client has been served");
                int i = 0; 
            }
        }
        
    }
    private class RaceCourseSend implements Runnable{
        Socket socket;
        RaceCourse payload;
        
        public RaceCourseSend(Socket sock, RaceCourse payload){ 
            this.socket = sock;
            this.payload = payload;
        }

        @Override
        public void run() {
            OutputStream oStream = null;
            ObjectOutputStream ooStream = null;
            
            // HACK: Prevents Client from getting recv failed.
            // TODO: Why is this needed?
            try{
                Thread.sleep(1); 
            }catch(InterruptedException ex){
                // Do nothing.
            }
            
            try{
                oStream = socket.getOutputStream();
            }catch(IOException ex){
                System.out.println("Error in socket creation");
            }
            
            try{
                if(oStream != null)
                    ooStream = new ObjectOutputStream(oStream);
            }catch(IOException ex){
                System.out.println("Error in output stream creation");
            }
            
            try{
                if(ooStream != null){
                    ooStream.writeObject(this.payload);  // send serilized payload
                    ooStream.flush();
                }
            }catch(IOException ex){
                System.out.println("Error when sending object through stream");
            }
            
            try{
                if(ooStream != null)
                    ooStream.close();
            }catch(IOException ex){
                System.out.println("Error when closing stream");
            }
            
            try{
                // Leave time for the client to close its input stream
                Thread.sleep(1000); 
            }catch(InterruptedException ex){
                // Do nothing.
            }
            
            // Always do the following
            finally
            {
                try
                {
                    socket.close();
                }
                catch (IOException ex)
                {
                    System.err.println("Unable to close an open socket.");
                    System.err.println(ex.toString());
                    System.exit(1);
                }
            }
            
        }
    }
}
