package gui;

import game.Doge;
import game.GameLogic;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Config;
import utils.Resources;

public class BackgroundPanel extends GuiPanel {
        
        private BufferedImage background = null;

        public BackgroundPanel(){
            super();
            background = Resources.loadImage("images/" + Config.getInstance().getBackground() +"Background.png");
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g2d.drawImage(background, 0, 0, this);
            GameLogic.entities.stream().filter(entity -> (entity instanceof Doge)).forEachOrdered(entity -> {
                g2d.drawImage(entity.getImage(), 1100, 580, this);               
            });
            repaint();
        }

    @Override
    public void start() {
        background = Resources.loadImage("images/" + Config.getInstance().getBackground() +"Background.png");
    }

    @Override
    public void close() {
    }
    
}
