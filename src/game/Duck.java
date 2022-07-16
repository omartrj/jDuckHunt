package game;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import utils.Resources;


public class Duck extends Enemy {
          
        private final String[] duckColorArray = {"black", "blue", "red"};
        private String duckColor;

        public Duck(){     
            init();                                   
        }

        @Override
        protected void init(){
            duckColor = duckColorArray[new Random().nextInt(duckColorArray.length)];
            super.init();
            enemySize = ThreadLocalRandom.current().nextDouble(0.9, 1.5);
            widthEnemy = (int)(frameToDraw.getWidth()*enemySize);
            heightEnemy = (int)(frameToDraw.getHeight()*enemySize);
        }
        
        @Override
        public void updateLocation(boolean loseLife){
            super.updateLocation(true);
        }

        @Override
        protected void loadFrames(){
            enemySprites = new BufferedImage[2];
            for(int i = 0; i<enemySprites.length; i++){
                enemySprites[i] = Resources.loadImage("images/" + duckColor + "Duck" + i + ".png");
            }

            explosionSprites = new BufferedImage[3];
            for(int i = 0; i<explosionSprites.length; i++){
                explosionSprites[i] = Resources.loadImage("images/explosionFrame" + i + ".png");
            }

            frameToDraw = enemySprites[indexAnimation];
            explosionToDraw = explosionSprites[indexExplAnimation];

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(enemyHitbox.contains(e.getX(), e.getY())){             
                AudioPlayer.getInstance().play("quack", 0);           
                deathSequence();
                ScoreData.getInstance().addScore(100);
            }
        }
     
    
}
