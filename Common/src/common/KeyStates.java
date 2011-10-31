/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package common;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class KeyStates implements Serializable{
    
    Map<Integer, Boolean> keyMap;
    static enum Key {
        UP(KeyEvent.VK_UP), DOWN(KeyEvent.VK_DOWN), LEFT(KeyEvent.VK_LEFT), RIGHT(KeyEvent.VK_RIGHT);

        int keyCode;
        Key(int keyCode) {
            this.keyCode = keyCode;
        }
    }
    
    //boolean[] keyDown;    
    
    public KeyStates() {
        //keyDown = new boolean[4];
        keyMap = new HashMap<Integer, Boolean>();
    }
    
    public boolean getKey(int keyCode) {
        boolean result = false;
        if(keyMap.get(keyCode) != null)
            result = true;
        return result;
    }
    
    public boolean getKeyUp(){
        return getKey(Key.UP.keyCode);
    }
    
    public boolean getKeyDown(){
        return getKey(Key.DOWN.keyCode);
    }
    
    public boolean getKeyLeft(){
        return getKey(Key.LEFT.keyCode);
    }
    
    public boolean getKeyRight(){
        return getKey(Key.RIGHT.keyCode);
    }
    
    public void setKey(int keyCode, boolean state){
        keyMap.put(keyCode, state);
    }
    
    public void setKeyUp(boolean state){
        setKey(Key.UP.keyCode, state);
    }
    
    public void setKeyDown(boolean state){
        setKey(Key.DOWN.keyCode, state);
    }
    
    public void setKeyLeft(boolean state){
        setKey(Key.LEFT.keyCode, state);
    }
    
    public void setKeyRight(boolean state){
        setKey(Key.RIGHT.keyCode, state);
    }  
}
