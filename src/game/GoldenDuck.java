package game;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import utils.Resources;


public class GoldenDuck extends Enemy {
     
        public GoldenDuck(){
            init();                                   
        }

        @Override
        protected void init(){
            super.init();
            xStep = 25;
            yStep = 12;
        }

        @Override
        protected void loadFrames() {
            enemySprites = new BufferedImage[2];
            for(int i = 0; i<enemySprites.length; i++){
                enemySprites[i] = Resources.loadImage("images/goldenDuck" + i + ".png");
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
                AudioPlayer.getInstance().play("coin", 0);           
                deathSequence();
                ScoreData.getInstance().addScore(1000);
            }
        }

}
