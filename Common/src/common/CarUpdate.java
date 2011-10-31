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
public class CarUpdate implements Serializable{
	public Point.Double position; //relative to container gui component's origin
	public double rotation; //angle in radians
	public Color color;

	public CarUpdate(Double position, double rotation, Color color) {
		this.position = position;
		this.rotation = rotation;
		this.color = color;
	}
}
