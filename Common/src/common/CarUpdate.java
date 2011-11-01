/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Robinerd
 */
public class CarUpdate implements Serializable{
	public double posX; //relative to container gui component's origin
	public double posY;
	public double rotation; //angle in radians
	public Color color;
	public int nextCheckpoint;

	public CarUpdate(double posX, double posY, double rotation, Color color, int nextCheckpoint) {
		this.posX = posX;
		this.posY = posY;
		this.rotation = rotation;
		this.color = color;
		this.nextCheckpoint = nextCheckpoint;
	}
}
