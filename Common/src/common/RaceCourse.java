/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package common;

import java.awt.geom.Line2D;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Used as a struct, a data carrier. No sensitive data, therefore public is used
 * on all variables.
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class RaceCourse implements Serializable{
    public ImageIcon raceCourseImg;
    public ImageIcon frictionMaskImg;
    public Line2D[] checkpoints;
    public double spawnX;
    public double spawnY;
    public double spawnDir;
    
    
    public RaceCourse(ImageIcon raceCourseImg, ImageIcon frictionMaskImg,
            Line2D[] checkpoints, double spawnX, double spawnY, double spawnDir){
        this.raceCourseImg = raceCourseImg;
        this.frictionMaskImg = frictionMaskImg;
        this.checkpoints = checkpoints;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnDir = spawnDir;
    }
    
    // Easteregg
    @Override
    public String toString() {
        String msg = "The RaceCourse object says hi!";
        return msg;
    }
}
