/*
 *  Copyright Mattias Liljeson Sep 12, 2011
 */
package client;

import common.CarUpdate;
import common.KeyStates;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import common.RaceUpdate;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class used to paint a frame containing the actual game.
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class GameFrame extends JFrame{
    private KeyStates keyStates;
	public final Object lockKeyStates = new Object();
    private KeyStateListener keyListener;
	private CarUpdate[] carUpdates;
	private JPanel gamePanel;
	private int mapW, mapH;
	private ImageIcon colors;
	private int carWidth = 15, carLength = 25;
	
    GameFrame(ImageIcon colors){
        keyStates = new KeyStates();
        keyListener = new KeyStateListener();
		carUpdates = new CarUpdate[0]; //create with size zero to avoid needing to check for null everywhere. (cars is inited with each message from server)
		this.colors = colors;
		
		//init the GUI components
		gamePanel = new GamePanel();
		add(gamePanel);
		mapW = colors.getIconWidth();
		mapH = colors.getIconHeight();
        addKeyListener(keyListener);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(mapW, mapH));
		setSize(mapW, mapH);
		setLocationRelativeTo(null);
		setVisible(true);
    }
    
    public void update(RaceUpdate raceUpdate){
        carUpdates = raceUpdate.carUpdates;
		gamePanel.repaint();
    }
	
    public KeyStates getKeyStates(){
        return keyStates;
    }
    
	
	private class GamePanel extends JPanel {
		
		public GamePanel() {
			setDoubleBuffered(true);
		}
		
		@Override
		public void paint(Graphics g) {
			colors.paintIcon(this, g, 0, 0);

			for(CarUpdate update : carUpdates) {
				g.setColor(update.color);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setTransform(AffineTransform.getRotateInstance(update.rotation, update.posX, update.posY));
				g2d.fillRect((int) (update.posX - carLength/2.0), (int) (update.posY - carWidth/2.0), carLength, carWidth);
			}
		}
	}
	
    /**
     * Inner class which listens to key events and saves them to the local 
     * KeyStates instance.
     */
    private class KeyStateListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void keyPressed(KeyEvent e) {
			synchronized(lockKeyStates) {
				keyStates.setKey(e.getKeyCode(), true);
			}
        }

        @Override
        public void keyReleased(KeyEvent e) {
			synchronized(lockKeyStates) {
				keyStates.setKey(e.getKeyCode(), false);
			}
        }
    }
}
