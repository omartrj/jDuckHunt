package gui;

import game.AudioPlayer;
import game.Leaderboard;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import utils.Config;
import utils.Resources;

public class GuiButton {
    
    private Rectangle2D.Double button;
    private final String nameButton;
    private BufferedImage buttonUp;
    private BufferedImage buttonDown;
    private BufferedImage buttonToDraw;
    private final int x;
    private final int y;
    private boolean bool = true;
    
    
    public GuiButton(String nameButton, int x, int y){
        this.nameButton = nameButton;
        this.x = x;
        this.y = y;
        buttonUp = Resources.loadImage("images/" + nameButton + "Button.png");
        buttonDown = Resources.loadImage("images/" + nameButton + "ButtonPressed.png");
        buttonToDraw = buttonUp;       
        button = new Rectangle2D.Double();  
    }      
    
    public void draw(Graphics2D g2d, ImageObserver imgObs){
        g2d.drawImage(buttonToDraw, x, y, imgObs);
        button.setRect(x, y, buttonUp.getWidth(), buttonUp.getHeight());
    }
    
    public void updateSoundButtons(){
        if(nameButton.equals("music")){
            if(!AudioPlayer.getInstance().isMusicRunning()){
                buttonUp = Resources.loadImage("images/" + nameButton + "MuteButton.png");
                buttonDown = Resources.loadImage("images/" + nameButton + "MuteButtonPressed.png");
            }
            else{
                buttonUp = Resources.loadImage("images/" + nameButton + "Button.png");
                buttonDown = Resources.loadImage("images/" + nameButton + "ButtonPressed.png");
            }
        }
        else if(nameButton.equals("sfx")){
            if(!AudioPlayer.getInstance().isSFXRunning()){
                buttonUp = Resources.loadImage("images/" + nameButton + "MuteButton.png");
                buttonDown = Resources.loadImage("images/" + nameButton + "MuteButtonPressed.png");
            }
            else{
                buttonUp = Resources.loadImage("images/" + nameButton + "Button.png");
                buttonDown = Resources.loadImage("images/" + nameButton + "ButtonPressed.png");
            }
        }
    }
    
    
    public void remove(){
        button = new Rectangle2D.Double();
    }
    
    public void mousePressed(MouseEvent e){
        if(button.contains(e.getX(), e.getY())){
            switch (nameButton) {
                case "exit" -> {
                    try {
                        JFrame frame = new JFrame("Exit");
                        BufferedImage image = Resources.loadImage("images/doge02.png");
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 82, Image.SCALE_DEFAULT));
                        JLabel label = new JLabel("<html>Sei sicuro di voler<br/>chiudere il gioco?</html>");
                        label.setFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/res/fonts/zig.ttf")).deriveFont(20f));
                        if (JOptionPane.showConfirmDialog( frame ,label,">:(",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon)==JOptionPane.YES_OPTION) {
                            System.exit(0);
                        } else {
                            frame.dispose();
                        }
                    } catch (FontFormatException | IOException ex) {
                        ex.getStackTrace();
                    }
                }
                case "level" -> Leaderboard.getInstance().sortByLevel();
                case "score" -> Leaderboard.getInstance().sortByScore();
                case "music" -> {
                    if(AudioPlayer.getInstance().isMusicRunning()){
                        buttonUp = Resources.loadImage("images/" + nameButton + "MuteButton.png");
                        buttonDown = Resources.loadImage("images/" + nameButton + "MuteButtonPressed.png");
                        AudioPlayer.getInstance().muteMusic();
                    }
                    else{
                        buttonUp = Resources.loadImage("images/" + nameButton + "Button.png");
                        buttonDown = Resources.loadImage("images/" + nameButton + "ButtonPressed.png");
                        AudioPlayer.getInstance().loadMusic();
                        AudioPlayer.getInstance().play("mainTheme", Clip.LOOP_CONTINUOUSLY);
                    }
                }
                case "sfx" -> {
                    if(AudioPlayer.getInstance().isSFXRunning()){
                        buttonUp = Resources.loadImage("images/" + nameButton + "MuteButton.png");
                        buttonDown = Resources.loadImage("images/" + nameButton + "MuteButtonPressed.png");
                        AudioPlayer.getInstance().muteSFX();
                    }
                    else{
                        buttonUp = Resources.loadImage("images/" + nameButton + "Button.png");
                        buttonDown = Resources.loadImage("images/" + nameButton + "ButtonPressed.png");
                        AudioPlayer.getInstance().loadSFX();
                    }
                }
                case "sun" -> {
                    Config.getInstance().setBackground("giorno");
                    GameFrame.panels.get("backgroundPanel").start();
                }
                case "moon" -> {
                    Config.getInstance().setBackground("notte");
                    GameFrame.panels.get("backgroundPanel").start();
                }
                default -> {
                    String namePanel = nameButton.substring(0, 4) + "Panel";
                    GameFrame.changeToPanel(namePanel);
                }
            }
        }
        button = new Rectangle2D.Double();
    }
    
    public void mouseMoved(MouseEvent e){
        if(button.contains(e.getX(), e.getY())){
            if(bool){
                AudioPlayer.getInstance().play("select", 0);
                bool = false;
            }
            buttonToDraw = buttonDown; 
        }
        else{
            bool = true;
            buttonToDraw = buttonUp;
        }
    }
        
    
}
