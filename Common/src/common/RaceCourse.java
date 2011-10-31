/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package common;

import java.awt.Rectangle;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Used as a struct, a data carrier. No sensitive data, therefore public is used
 * on all variables.
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class RaceCourse implements Serializable{
    public ImageIcon raceCourseImg;
    public ImageIcon speedMaskImg;
    public Rectangle[] checkpoints;
    
    
    public RaceCourse(ImageIcon raceCourseImg, ImageIcon speedMaskImg, Rectangle[] checkpoints){
        this.raceCourseImg = raceCourseImg;
        this.speedMaskImg = speedMaskImg;
        this.checkpoints = checkpoints;
    }
    
    // Easteregg
    @Override
    public String toString() {
        String msg = "The RaceCourse object says hi!";
        return msg;
    }
}
