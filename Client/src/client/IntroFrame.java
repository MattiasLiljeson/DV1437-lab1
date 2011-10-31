/*
 *  Copyright Mattias Liljeson Sep 12, 2011
 */
package client;

import common.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Intro frame shown when starting the game
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class IntroFrame {
    
    private boolean readyToPlay;
    private String playerName;
    private String hostname;
    private String carColor;
    
    // GUI elements
    private JFrame frame;
    private JPanel startPanel;
    private JTextField playerNameField;
    private JTextField hostnameField;
    private JComboBox carColorBox;
    private JButton startGameBtn;
    private JLabel[] labels;
    
    public IntroFrame(){
        // Setup and init frame components -------------------------------------
        labels = new JLabel[3];
        GridLayout layout = new GridLayout(4,2);
        layout.setHgap(5);
        layout.setVgap(5);
        startPanel = new JPanel(layout);
        
        labels[0] = new JLabel("Player name:");
        startPanel.add(labels[0]);
        playerNameField = new JTextField("Pelle");
        startPanel.add(playerNameField);
        
        labels[1] = new JLabel("hostname:");
        startPanel.add(labels[1]);
        hostnameField = new JTextField("localhost");
        startPanel.add(hostnameField);
        
        labels[2] = new JLabel("Car color:");
        startPanel.add(labels[2]);
        String[] colors = {"Red","Blue","Yellow", "Green"};
        carColorBox = new JComboBox(colors);
        startPanel.add(carColorBox);
        
        startGameBtn = new JButton("Play!");
        startGameBtn.addActionListener(new buttonListener());
        startPanel.add(startGameBtn);
        
        frame = new JFrame("bilkul");
        Container pane = frame.getContentPane();
        BorderLayout frameLayout = new BorderLayout(5, 5);
        frame.setLayout(frameLayout);
        frame.add(startPanel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
        //frame.add(gamePanel, BorderLayout.NORTH);
    }
    
    public String getCarColor(){
        return carColor;
    }
    
    public String getHostname(){
        return hostname;
    }
    
    public KeyStates getKeyStates(){
        //TODO: update keystates
        return gamePanel.getKeyStates();
    }
    
    public String getPlayerName(){
        return playerName;
    }
    
//    public boolean isReadyToPlay(){
//        return readyToPlay;
//    }
    
    public void update(RaceUpdate raceUpdate){
        //TODO: Update panel. Draw latest info
    }
    
    // !WARNING!
    // ---! EVIL HACK BELOW !---
    // !WARNING!
    //
    // The two following methods are a hack were wait/notify are misued to cause
    // the Client to wait for the "Play!" button is pressed.
    public synchronized void doWait(){
        if(!readyToPlay){
            try{
                wait();
            }catch(InterruptedException ignore){}
        }
    }
    
    private synchronized void doNotify(){
        notify();
    }
    
    private class buttonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            playerName = playerNameField.getText();
            hostname = hostnameField.getText();
            carColor = (String)carColorBox.getSelectedItem();
            readyToPlay = true;
            doNotify();
        }
        
    }
}
