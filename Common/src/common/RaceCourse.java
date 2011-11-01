/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package common;

import java.awt.Rectangle;
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
    
    
    public RaceCourse(ImageIcon raceCourseImg, ImageIcon frictionMaskImg, Line2D[] checkpoints){
        this.raceCourseImg = raceCourseImg;
        this.frictionMaskImg = frictionMaskImg;
        this.checkpoints = checkpoints;
    }
    
    // Easteregg
    @Override
    public String toString() {
        String msg = "The RaceCourse object says hi!";
        return msg;
    }
}
