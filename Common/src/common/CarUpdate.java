/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Robinerd
 */
public class CarUpdate implements Serializable{
	public Point.Double position; //relative to container gui component's origin
	public double rotation; //angle in radians
	public Color color;

	public CarUpdate(Point.Double position, double rotation, Color color) {
            this.position = position;
            this.rotation = rotation;
            this.color = color;
	}
}
