/*
 *  Copyright Mattias Liljeson Nov 1, 2011
 */
package common;

import java.io.Serializable;

/**
 * Simple struct type class used to shovel data about the player to the server
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class ClientData implements Serializable{
    public String playerName;
    public String carColor;
    
    public ClientData(String playerName, String carColor){
        this.playerName = playerName;
        this.carColor = carColor;
    }
}
