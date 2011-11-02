/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Container for data regarding the race. Such as the cars positions etc.
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class RaceUpdate implements Serializable{
    public HashMap<Integer, CarUpdate> carUpdates;
	public int clientID;
    public String winner;
	
	public RaceUpdate(HashMap<Integer, CarUpdate> carUpdates) {
		this.carUpdates = carUpdates;
	}
	
}
