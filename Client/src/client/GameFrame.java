/*
 *  Copyright Mattias Liljeson Sep 12, 2011
 */
package client;

import common.CarUpdate;
import common.KeyStates;
import common.RaceCourse;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import common.RaceUpdate;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class used to paint a frame containing the actual game.
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class GameFrame extends JFrame{
	private int carWidth = 15, carLength = 25;
    private KeyStates keyStates;
	public final Object lockKeyStates = new Object();
    private KeyStateListener keyListener;
	private HashMap<Integer, CarUpdate> carUpdates;
	private JPanel gamePanel;
	private int mapW, mapH;
	private ImageIcon colors;
	private Line2D[] checkpoints;
	private int nextCheckpoint;
    private String winner = null;
	
    GameFrame(RaceCourse raceCourse){
        keyStates = new KeyStates();
        keyListener = new KeyStateListener();
		carUpdates = new HashMap<Integer, CarUpdate>(); //create with size zero to avoid needing to check for null everywhere. (cars is inited with each message from server)
		this.colors = raceCourse.raceCourseImg;
		this.checkpoints = raceCourse.checkpoints;
		mapW = colors.getIconWidth();
		mapH = colors.getIconHeight();
		
		//init the GUI components
		gamePanel = new GamePanel();
		add(gamePanel);
        addKeyListener(keyListener);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
    }
    
    public void update(RaceUpdate raceUpdate){
        winner = raceUpdate.winner;
        carUpdates = raceUpdate.carUpdates;
		nextCheckpoint = carUpdates.get(raceUpdate.clientID).nextCheckpoint;
		gamePanel.repaint();
    }
	
    public KeyStates getKeyStates(){
        return keyStates;
    }
	
	private class GamePanel extends JPanel {
		
        AffineTransform affineTransform = new AffineTransform();
        Font winnerFont = new Font("Garamond", Font.PLAIN, 45);
        Color winnerColor = new Color(1.0f, 1.0f, 1.0f, 0.7f);
        
		public GamePanel() {
			setDoubleBuffered(true);
			setPreferredSize(new Dimension(mapW, mapH));
			setSize(mapW, mapH);
            
		}
		
		@Override
		public void paint(Graphics g) {
			colors.paintIcon(this, g, 0, 0);

			Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			for(CarUpdate update : carUpdates.values()) {
				g.setColor(update.color);
                affineTransform.setToRotation(update.rotation, update.posX, update.posY);
				g2d.setTransform(affineTransform);
				g2d.fillRect((int) (update.posX - carLength/2.0), (int) (update.posY - carWidth/2.0), carLength, carWidth);
			}
			
            affineTransform.setToIdentity();
            g2d.setTransform(affineTransform);
            
			for(int i=0; i<checkpoints.length; i++) {
				if(i == nextCheckpoint) {
					g2d.setColor(Color.blue);
				} else {
					g2d.setColor(Color.red);
				}
				g2d.draw(checkpoints[i]);
			}
            
            if(winner != null) {
                g2d.setColor(winnerColor);
                g2d.setFont(winnerFont);
                g2d.drawString(winner+" won the race", mapW/2-200, mapH/2+10);
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
