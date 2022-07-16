package game;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;

public abstract class Enemy extends Entity{
           
        protected double enemySize;
        protected BufferedImage[] enemySprites;
        protected int indexAnimation = 0;
        protected BufferedImage[] explosionSprites;
        protected int indexExplAnimation = 0;      

        @Override
        protected void init(){
            xStep = ThreadLocalRandom.current().nextInt(15, 22 + 1);
            yStep = ThreadLocalRandom.current().nextInt(5, 10 + 1);
            yMedianLine = ThreadLocalRandom.current().nextInt(120, 350 + 1);
            yBufferZone = ThreadLocalRandom.current().nextInt(30, 150 + 1);
            enemySize = 1;
            if(Math.random()<=0.50) {
                upDownDirection = 1;
            } else {
                upDownDirection = -1;
            }
            if(Math.random()<=0.50) {
                leftRightDirection = 1;
            } else {
                leftRightDirection = -1;
            }
            loadFrames();
            widthEnemy = (int)(frameToDraw.getWidth()*enemySize);
            heightEnemy = (int)(frameToDraw.getHeight()*enemySize);                
            if(leftRightDirection == 1) {
                xEnemy = width;
            } else {
                xEnemy = -widthEnemy;
            }                 
            yEnemy = yMedianLine; 
            enemyHitbox = new Rectangle2D.Double();
        }

        @Override
        protected abstract void loadFrames() ;

        @Override
        protected void changeFrame(){
            if(indexAnimation < enemySprites.length - 1) {
                indexAnimation++;
            } else {
                indexAnimation = 0;
            }

            frameToDraw = enemySprites[indexAnimation];
        }

        @Override
        public abstract void mousePressed(MouseEvent e);

        protected void deathSequence(){
            showDeadEnemy = true;
            xStep = 0;
            yStep = 0;
            startDeathAnimation(200);
            Timer timer = new Timer(600, (ActionEvent arg0) -> {
                removeEnemy();
                timerDeathAnimation.stop();
            });
            timer.setRepeats(false);
            timer.start();
        }

        protected void startDeathAnimation(int delay){
            timerDeathAnimation = new Timer(delay, (ActionEvent arg0) -> {
                changeDeathFrame();
            });
            timerDeathAnimation.setRepeats(true);
            timerDeathAnimation.start();
        }

        protected void changeDeathFrame(){
            if(indexExplAnimation < explosionSprites.length - 1) {
                indexExplAnimation++;
            } else {
                indexExplAnimation = 0;
            }

            explosionToDraw = explosionSprites[indexExplAnimation];
        }
    
    
}
