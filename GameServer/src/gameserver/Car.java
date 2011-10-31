/*
 *  Copyright Mattias Liljeson Sep 14, 2011
 */
package gameserver;

import common.CarUpdate;
import common.KeyStates;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class Car {
    // Force constants. All units are per second
    public static final double FACTOR_BREAK = 10;
    public static final double FACTOR_ACC = 10;
    public static final double FACTOR_TURN = 10;
    public static final double FACTOR_BASE_FRICTION = 2;
    
    private KeyStates keyStates;
    private Point.Double pos;
    private double direction;
    private double speed;
    private Color color;
    private CarUpdate carUpdate;
    
    public Car(double posX, double posY, double direction){
        keyStates = new KeyStates();
        pos = new Point.Double(posX, posY);
        this.direction = direction;
        
        //TODO, fix color
        color = Color.BLUE;
        carUpdate = new CarUpdate(pos, direction, color);
    }
    
    public CarUpdate getCarUpdate() {
        carUpdate.position = pos;
        carUpdate.rotation = direction;
        return carUpdate;
    }
    
    public Color getColor(){
        return color;
    }
    
    public Point.Double getPosition(){
        return pos;
    }
    
    public void setKeyStates(KeyStates keyStates){
        this.keyStates = keyStates;
    }
    
    public void update(int fps, BufferedImage frictionMaskBuffImg){
        // Friction
        // Sample friction from friction mask in the raceCourse
        Color frictionColor = new Color(frictionMaskBuffImg.getRGB((int)pos.x, (int)pos.y));
        double friction = FACTOR_BASE_FRICTION + (frictionColor.getBlue()/50.0);
        speed -= friction;
        
        // Steering
        // Counterclockwise, as the unit circle
        if(keyStates.getKeyLeft()){
            direction -= FACTOR_TURN;
        }
        if(keyStates.getKeyRight()){
            direction += FACTOR_TURN;
        }
        
        // Acceleration / deacceleration
        if(keyStates.getKeyUp()){
            speed += FACTOR_ACC;
        }
        if(keyStates.getKeyDown()){
            speed -= FACTOR_BREAK;
        }
        
        double dx = Math.cos(direction)*speed / fps;
        double dy = Math.sin(direction)*speed / fps;
        pos.setLocation(pos.getX()+dx, pos.getY()+dy);
    }
}
