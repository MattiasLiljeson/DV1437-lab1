/*
 *  Copyright Mattias Liljeson Sep 14, 2011
 */
package gameserver;

import common.KeyStates;
import java.awt.*;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class Car {
    // Force constants. All units are per second
    public static final double FACTOR_BREAK = 10;
    public static final double FACTOR_ACC = 10;
    public static final double FACTOR_TURN = 10;
    
    private KeyStates keyStates;
    private Point pos;
    private double direction;
    private double speed;
    private Color color;
    
    public Car(){
        keyStates = new KeyStates();
        
        //TODO, fix color
        color = Color.BLUE;
    }
    
    public Color getColor(){
        return color;
    }
    
    public Point getPosition(){
        return pos;
    }
    
    public void setKeyStates(KeyStates keyStates){
        this.keyStates = keyStates;
    }
    
    public void update(int fps){
        // Steering
        // Counterclockwise, as the unit circle
        if(keyStates.getKeyLeft()){
            direction -= FACTOR_TURN;
        }
        if(keyStates.getKeyRight()){
            direction += FACTOR_TURN;
        }
        
        // Acceleration / deaceleration
        if(keyStates.getKeyUp()){
            speed += FACTOR_ACC;
        }
        if(keyStates.getKeyDown()){
            speed -= FACTOR_BREAK;
        }
        
        double x = Math.cos(direction)*speed / fps;
        double y = Math.cos(direction)*speed / fps;
        pos.translate((int)x, (int)y);
    }
}
