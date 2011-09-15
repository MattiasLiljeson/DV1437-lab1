/*
 *  Copyright Mattias Liljeson Sep 12, 2011
 */
package client;

import common.KeyStates;
import javax.swing.JPanel;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class GamePanel extends JPanel{
    private KeyStates keyStates;
    
    GamePanel(){
        keyStates = new KeyStates();
    }
    
    public KeyStates getKeyStates(){
        return keyStates;
    }
}
