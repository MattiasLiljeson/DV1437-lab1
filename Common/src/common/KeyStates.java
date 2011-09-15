/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package common;

import java.io.Serializable;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class KeyStates implements Serializable{
    
    static final int kUp = 0, kDown = 1, kLeft = 2, kRight = 3;
    
    boolean[] keyDown;    
    
    public KeyStates() {
        keyDown = new boolean[4];
    }
    
    public boolean getKey(int index){
        return keyDown[index];
    }
    
    public boolean getKeyUp(){
        return keyDown[kUp];
    }
    
    public boolean getKeyDown(){
        return keyDown[kDown];
    }
    
    public boolean getKeyLeft(){
        return keyDown[kLeft];
    }
    
    public boolean getKeyRight(){
        return keyDown[kRight];
    }
    
    public void setKey(int index, boolean state){
        keyDown[index] = state;
    }
    
    public void setKeyUp(boolean state){
        setKey(kUp, state);
    }
    
    public void setKeyDown(boolean state){
        setKey(kDown, state);
    }
    
    public void setKeyLeft(boolean state){
        setKey(kLeft, state);
    }
    
    public void setKeyRight(boolean state){
        setKey(kRight, state);
    }  
}
