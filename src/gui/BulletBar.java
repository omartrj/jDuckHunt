package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import utils.Resources;

public class BulletBar{
    
        private BufferedImage bullet;
        private int xBullet;
        private int yBullet;
        private boolean isUp;
        private int numberBullet;
        private Font zigFont;

        public BulletBar(){
            init();
        }


        private void init() {
            bullet = Resources.loadImage("images/bullet.png");
            xBullet = 40;
            yBullet = 670;
            isUp = true;
            numberBullet = 100;
            
            try {
                zigFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/res/fonts/zig.ttf"));
            } catch (FontFormatException | IOException ex) {
            }
        }

        public void draw(Graphics g, ImageObserver imgObs){
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(bullet, xBullet, yBullet, imgObs);
            g2d.setColor(Color.WHITE);
            g2d.setFont(zigFont.deriveFont(30f));       
            g2d.drawString("x"+numberBullet, xBullet + 50, 730);
        }


        public void animate(){           
            if(isUp){
                yBullet += 10;
                isUp = false;
            }
            else{
                yBullet -= 10;
                isUp = true;
            }           
        }


        public void loseBullet() {
            if(numberBullet>0) {
                numberBullet--;
            }
        }

        public int getBullets(){
            return numberBullet;
        }

        public void setBullets(int numBullet){
            numberBullet = numBullet;
        }

}
