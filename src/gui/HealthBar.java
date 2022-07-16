package gui;

import game.AudioPlayer;
import game.ScoreData;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import utils.Resources;

public class HealthBar{
    
    private BufferedImage fullHeart;
    private BufferedImage emptyHeart;
    private int xHeart;
    private int yHeart;
    private int distHeart;
    private boolean isUp;    
    private int lives;
    
    public HealthBar(){
        init();
    }

    private void init() {
        fullHeart = Resources.loadImage("images/heart.png");
        emptyHeart = Resources.loadImage("images/emptyHeart.png");
        xHeart = 1150;
        yHeart = 20;
        distHeart = 50;
        isUp = true;
        lives = 3;
    }
    
   
    public void draw(Graphics g, ImageObserver imageObserver){
        Graphics2D g2d = (Graphics2D)g;       
        for(int i=0; i<lives; i++){
            g2d.drawImage(fullHeart, xHeart+distHeart*i, yHeart, imageObserver);
        }
        for(int i=lives; i<3; i++){
            g2d.drawImage(emptyHeart, xHeart+distHeart*i, 30, imageObserver);
        }           
    }
    
   
    public void animate(){           
        if(isUp){
            yHeart += 10;
            isUp = false;
        }
        else{
            yHeart -= 10;
            isUp = true;
        }           
    }

    
    public void addLife() {
        if(lives < 3) {
            lives++;
        } else {
            ScoreData.getInstance().addScore(500);
        }
    }

    
    public void loseLife(boolean loseLife) {
        if(lives > 0 && loseLife){
            lives--;
            AudioPlayer.getInstance().play("hitSound", 0);
        }
    }
    
    public int getLives(){
        return lives;
    }
    
    
    public void reset(){
        lives = 3;
    }
    
}
