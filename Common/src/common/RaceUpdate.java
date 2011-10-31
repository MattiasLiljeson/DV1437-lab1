/*
 *  Copyright Mattias Liljeson Sep 7, 2011
 */
package common;

import java.io.Serializable;

/**
 * Container for data regarding the race. Such as the cars positions etc.
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class RaceUpdate implements Serializable{
    public CarUpdate[] carUpdates;

    public RaceUpdate(CarUpdate[] cars) {
		this.carUpdates = cars;
    }
    
}
