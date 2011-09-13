/*
 *  Copyright Mattias Liljeson Sep 12, 2011
 */
package server;

import java.io.*;
import java.util.Map;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO: fix relative path
        String[] commands = new String[3];
        commands[0] = "java";
        commands[1] = "-jar";
        commands[2] = "C:\\Users\\Mattias\\Dropbox\\Skola\\BTH\\3-1-2011ht\\"
                + "lab1\\lab1 egen kodbas\\RaceCourseServer\\dist\\"
                + "RaceCourseServer.jar";
        
        ProcessBuilder rcsProcBuilder;
//        rcsProcBuilder = new ProcessBuilder("notepad");
        rcsProcBuilder = new ProcessBuilder(commands); 
        
        rcsProcBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        rcsProcBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        //raceCourseServer.
        System.out.println("raceCourseServer starting");
        
        Process rcsProc = null;
        try{
            rcsProc = rcsProcBuilder.start();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        System.out.println("raceCourseServer started");
        
        // DEBUG: everlasting loop
        //Enter main loop which listens to the 'exit' signal
        boolean done = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while(!done){
            try{
                input = br.readLine();
            }catch(IOException ignore){}
            
            if(input.equals("exit")){
                OutputStream out = rcsProc.getOutputStream();
                PrintWriter writer = new PrintWriter(out, true);
                writer.println("exit");
                
                System.out.println("Exiting from server");
                
                done = true;
            }
        }
        System.exit(0);
    }
}
