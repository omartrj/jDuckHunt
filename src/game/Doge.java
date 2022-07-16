package game;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import utils.Resources;

public class Doge extends Entity {
    
        private BufferedImage doge0;
        private BufferedImage doge1;
        private BufferedImage dogeAngry;
        private Rectangle2D.Double dogeHeadHitbox;
        private Rectangle2D.Double dogeBodyHitbox;
        private boolean isFirstFrameOn = true;
        private boolean showAngryDoge = false;
        
        

        public Doge(){                                        
            init();       
        }

        @Override
        protected void init(){
            dogeHeadHitbox = new Rectangle2D.Double(1100, 600, 120, 70);
            dogeBodyHitbox = new Rectangle2D.Double(1120, 650, 150, 70);
            loadFrames();
        }

        @Override
        protected void loadFrames(){
            doge0 = Resources.loadImage("images/doge00.png");
            doge1 = Resources.loadImage("images/doge01.png");
            dogeAngry = Resources.loadImage("images/doge02.png");
            frameToDraw = doge0;
        }



        @Override
        protected void changeFrame() {
            if(isFirstFrameOn == false && showAngryDoge == false){
                frameToDraw = doge0;
                isFirstFrameOn = true;
            }
            else if(isFirstFrameOn == true && showAngryDoge == false){
                frameToDraw = doge1;
                isFirstFrameOn = false;
            }  
        }


        @Override
        public void mousePressed(MouseEvent e) {
            if(dogeHeadHitbox.contains(e.getX(), e.getY()) || dogeBodyHitbox.contains(e.getX(), e.getY())){            
                AudioPlayer.getInstance().play("bark", 0);
                showAngryDoge = true;
                frameToDraw = dogeAngry;            
                Timer timer = new Timer(800, (ActionEvent arg0) -> {
                        showAngryDoge = false;
                });
                timer.setRepeats(false); 
                timer.start();
            }
        }

    
}
