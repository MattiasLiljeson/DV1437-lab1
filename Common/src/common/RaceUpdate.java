/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package common;

import java.awt.*;

/**
 * Container for data regarding the race. Such as the cars positions etc.
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class RaceUpdate {
    private Point[] positions;
    private Color[] colors;
    private int numCars;

    public RaceUpdate(int totCars) {
        positions = new Point[totCars];
        colors = new Color[totCars];
        
        numCars = 0;
    }
    
    public void addCar(Point pos, Color color){
        // Add 'new' objects so that noc hanges are made elsewhere
        positions[numCars] = new Point(pos);
        colors[numCars] = new Color(color.getRGB());
    }
    
}
