package game;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import utils.Resources;


public class FlyingHeart extends Enemy {
    
        protected BufferedImage flyingHeart;

        public FlyingHeart(){
            init();
        }

        @Override
        protected void init(){
            super.init();
            xStep = 20;
            yStep = 15;
        }

        @Override
        protected void loadFrames() {
            flyingHeart = Resources.loadImage("images/heart.png");
            frameToDraw = flyingHeart;
            explosionSprites = new BufferedImage[3];
            for(int i = 0; i<explosionSprites.length; i++){
                explosionSprites[i] = Resources.loadImage("images/heartExplosion" + i + ".png");
            }

        }

        @Override
        protected void changeFrame() {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(enemyHitbox.contains(e.getX(), e.getY())){
                GameLogic.healthBar.addLife();
                AudioPlayer.getInstance().play("oneUpSound", 0);
                deathSequence();
            }
        }
    
}
