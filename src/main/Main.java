package main;

import javax.swing.*;
import java.util.Objects;

public class Main {

    public static JFrame window;
    
    public static void main(String[] args){

        window = new JFrame();
        window.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Blue Boy Adventure");
        new Main().setIcon();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn){
           window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
        
    }
    public void setIcon(){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("Player/Walking_sprites/boy_down_1.png")));
        window.setIconImage(icon.getImage());
    }

}
