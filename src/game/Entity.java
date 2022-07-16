package game;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import utils.Constants;

public abstract class Entity {
    
        protected Timer timerLocation, timerDeathAnimation; 
        
        protected final int width = Constants.WIDTH;
        protected BufferedImage frameToDraw;
        protected BufferedImage explosionToDraw = null;
        protected boolean showDeadEnemy = false;
        protected int xEnemy;
        protected int yEnemy;
        protected int widthEnemy;
        protected int heightEnemy;    
        protected int xStep;             
        protected int yStep;              
        protected int yMedianLine;      
        protected int yBufferZone;       
        protected int upDownDirection;  
        protected int leftRightDirection;  
        protected Rectangle2D.Double enemyHitbox = new Rectangle2D.Double();
        
        public BufferedImage getImage(){
            if(showDeadEnemy == false) {
                return frameToDraw;
            } else {
                return explosionToDraw;
            }
        }
        public int getX(){
            if(leftRightDirection == 1) {
                return xEnemy;
            } else {
                return xEnemy + widthEnemy;
            }
        }
        public int getY(){
            return yEnemy;
        }
        public int getWidth(){
            if(leftRightDirection == 1) {
                return widthEnemy;
            } else {
                return -widthEnemy;
            }
        }
        public int getHeight(){
            return heightEnemy;
        }            
            
        public void removeEnemy(){
            GameLogic.numDucksDied++;
            GameLogic.entities.remove(this);            
        }
        
        public void updateLocation(boolean loseLife){
            if(leftRightDirection == 1){
                if(xEnemy + widthEnemy> 0) {
                    xEnemy -= xStep;
                } else{                           
                    GameLogic.healthBar.loseLife(loseLife);
                    removeEnemy();
                }                       
            }
            else{
                if(xEnemy<width) {
                    xEnemy += xStep;
                } else{                           
                    GameLogic.healthBar.loseLife(loseLife);
                    removeEnemy();
                }                       
            }
            if(upDownDirection == 1) {
                yEnemy -= yStep;
            } else {
                yEnemy += yStep;
            }           
            if(Math.abs(yEnemy-yMedianLine)>yBufferZone) {
                upDownDirection = -upDownDirection;
            } 
            enemyHitbox.setRect(xEnemy, yEnemy, widthEnemy, heightEnemy);               

        }               
       
        protected abstract void init();

        protected abstract void loadFrames();

        protected abstract void changeFrame();    

        public abstract void mousePressed(MouseEvent e);



    
    
}
