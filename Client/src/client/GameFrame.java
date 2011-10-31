/*
 *  Copyright Mattias Liljeson Sep 12, 2011
 */
package client;

import common.KeyStates;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 *
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class GameFrame extends JPanel{
    private KeyStates keyStates;
    
    GameFrame(){
        keyStates = new KeyStates();
    }
    
    public KeyStates getKeyStates(){
        return keyStates;
    }
}
