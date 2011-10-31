/*
 *  Copyright Mattias Liljeson Sep 12, 2011
 */
package client;

import common.Car;
import common.KeyStates;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import common.RaceUpdate;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class used to paint a frame containing the actual game.
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class GameFrame extends JFrame{
    private KeyStates keyStates;
	private Car[] cars;
	private JPanel gamePanel;
	private int mapW, mapH;
	ImageIcon colors;
	
    GameFrame(ImageIcon colors){
        keyStates = new KeyStates();
		cars = new Car[0]; //create with size zero to avoid needing to check for null everywhere. (cars is inited with each message from server)
		this.colors = colors;
		
		//init the GUI components
		gamePanel = new JPanel();
		add(gamePanel);
		mapW = colors.getIconWidth();
		mapH = colors.getIconHeight();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(mapW, mapH));
		setSize(mapW, mapH);
		setLocationRelativeTo(null);
		setVisible(true);
    }
    
    public void update(RaceUpdate raceUpdate){
        cars = raceUpdate.cars;
    }

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		colors.paintIcon(gamePanel, g, 0, 0);
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
