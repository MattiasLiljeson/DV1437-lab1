/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

/**
 *
 * @author Robinerd
 */
public class Car implements Serializable{
	public Point.Double position; //relative to container gui component's origin
	public Point.Double velocity; // Represents velocity as a 2D vector
	public double rotation; //angle in radians
	public Color color;

	public Car(Double position, double rotation, Color color) {
		this.position = position;
		this.rotation = rotation;
		this.color = color;
		velocity = new Point.Double(0,0);
	}
}
