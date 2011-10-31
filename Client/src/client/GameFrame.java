/*
 *  Copyright Mattias Liljeson Sep 12, 2011
 */
package client;

import common.KeyStates;
import java.awt.event.KeyEvent;
import common.RaceUpdate;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 * Class used to paint a frame containing the actual game.
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class GameFrame extends JPanel{
    private KeyStates keyStates;
    
    GameFrame(){
        keyStates = new KeyStates();
    }
    
    public void update(RaceUpdate raceUpdate){
        //TODO: Update panel. Draw latest info.
    }
	
	//TODO: implement or gtfo
    public KeyStates getKeyStates(){
        return keyStates;
    }
    
    /**
     * Inner class which listens to key events and saves them to the local 
     * KeyStates instance.
     */
    private class keyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            keyStates.setKey(e.getKeyCode(), true);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keyStates.setKey(e.getKeyCode(), false);
        }
    }
}
