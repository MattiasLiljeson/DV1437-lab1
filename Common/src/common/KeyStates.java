/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package common;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class KeyStates {
    
    static final int kUp = 0, kDown = 1, kLeft = 2, kRight = 3;
    
    boolean[] keyDown;    
    
    public KeyStates() {
        keyDown = new boolean[4];
    }
    
    public void setKeyDown(int index, boolean state){
        keyDown[index] = state;
    }
    
    public void setKeyUp(boolean state){
        setKeyDown(kUp, state);
    }
    
    public void setKeyDown(boolean state){
        setKeyDown(kDown, state);
    }
    
    public void setKeyLeft(boolean state){
        setKeyDown(kLeft, state);
    }
    
    public void setKeyRight(boolean state){
        setKeyDown(kRight, state);
    }  
}
