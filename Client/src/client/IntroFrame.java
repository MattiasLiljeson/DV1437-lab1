/*
 *  Copyright Mattias Liljeson Sep 12, 2011
 */
package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Intro frame shown when starting the game
 * @author Mattias Liljeson <mattiasliljeson.gmail.com>
 */
public class IntroFrame extends JFrame{
    
    private boolean readyToPlay;
    private String playerName;
    private String hostname;
    private String carColor;
    
    // GUI elements
    private JPanel startPanel;
    private JTextField playerNameField;
    private JTextField hostnameField;
    private JComboBox carColorBox;
    private JButton startGameBtn;
    private JLabel[] labels;
    
    public IntroFrame(){
		super("bilkul");
		
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
        
		//init and show the frame ----------------------------------------------
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = getContentPane();
        BorderLayout frameLayout = new BorderLayout(5, 5);
        setLayout(frameLayout);
        pane.add(startPanel,BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
    
    public String getCarColor(){
        return carColor;
    }
    
    public String getHostname(){
        return hostname;
    }
    
    public String getPlayerName(){
        return playerName;
    }
    
//    public boolean isReadyToPlay(){
//        return readyToPlay;
//    }
    
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
