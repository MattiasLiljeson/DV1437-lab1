/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package common;

import java.io.Serializable;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class RaceCourse implements Serializable{
    private static RaceCourse instance = null;
    
    private RaceCourse(){
    }
    
    public static RaceCourse getInstance(){ 
        if(instance == null)
            instance = new RaceCourse();
        
        return instance;
    }

    @Override
    public String toString() {
        String msg = "The RaceCourse object says hi!";
        return msg;
    }
}
