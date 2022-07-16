package gui;

import game.AudioPlayer;
import game.Enemy;
import game.Entity;
import game.GameLogic;
import game.Level;
import game.ScoreData;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayPanel extends GuiPanel {
       
        private final GuiButton menuButton = new GuiButton("menu", 510, 400);
        private final GuiButton musicButton = new GuiButton("music", 507, 350);
        private final GuiButton sfxButton = new GuiButton("sfx", 725, 350);
        private final GuiButton pauseMenuButton = new GuiButton("menu", 607, 660);
        private final GuiButton retryButton = new GuiButton("playRetry", 710, 400);
        private final List<GuiButton> pauseButtons = new ArrayList<>();
        private final GameLogic game = new GameLogic(); 
        private boolean isPaused = false;
        
        public PlayPanel(){
                super();                
                addMouseListener(this);
                addMouseMotionListener(this);
                addKeyListener(this);
                
                buttons.add(menuButton);
                buttons.add(retryButton);  
                pauseButtons.add(musicButton);
                pauseButtons.add(sfxButton);
                pauseButtons.add(pauseMenuButton);
	}

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g); 
            g2d.setColor(Color.WHITE);                           
            GameLogic.entities.stream().filter(entity -> (entity instanceof Enemy)).forEachOrdered(entity -> {
                g2d.drawImage(entity.getImage(), 
                            entity.getX(), 
                            entity.getY(), 
                            entity.getWidth(), 
                            entity.getHeight(), 
                            this);
            });
            GameLogic.healthBar.draw(g, this);          
            GameLogic.bulletBar.draw(g, this);               
            g2d.setFont(zigFont.deriveFont(30f));            
            g2d.drawString("LIVELLO "+Level.getInstance().getCurrentLevel(), 40, 45);
            g2d.drawString("PUNTEGGIO: "+ScoreData.getInstance().getScoreAsString(), 40, 45 +g2d.getFontMetrics().getHeight());            

            if(game.checkGameOver()){
                game.saveScoreData();
                close();
                g2d.setFont(zigFont.deriveFont(60f));
                g2d.drawString("GAME OVER", 485, 300);
                g2d.setFont(zigFont.deriveFont(30f));
                g2d.drawString("PUNTEGGIO: " + ScoreData.getInstance().getScoreAsString(), 510, 350);
                buttons.forEach(button -> {
                    button.draw(g2d, this);
                });
                if(GameLogic.bulletBar.getBullets()==0) {
                    GameLogic.bulletBar.setBullets(1);
                }   
            }
            if(game.checkLevelEnd()) {
                game.nextLevel();
            }
            
            if(isPaused){
                g2d.setFont(zigFont.deriveFont(70f));
                g2d.drawString("PAUSA", 520, 300);
                g2d.setFont(zigFont.deriveFont(30f));
                g2d.drawString("premi ESC per", 550, 150);
                g2d.drawString("riprendere la partita", 450, 150 + g2d.getFontMetrics().getHeight());
                g2d.setFont(new Font(Font.SERIF, Font.BOLD, 90));
                g2d.drawString("\u23ef", 820, 300);
                pauseButtons.forEach(button -> {
                    button.draw(g2d, this);
                });
            }
            else{
                pauseButtons.forEach(button -> {
                    button.remove();
                });
            }
            
            g2d.drawImage(cursorToDraw, xCursor, yCursor, this);       
            repaint();                                      
        }
       
        @Override
        public void start(){
            requestFocus();
            game.setNewGame();
            isPaused = false;
            pauseButtons.forEach(button -> {
                button.updateSoundButtons();
            });
            if(game.askPlayerName()) {
                game.startNewGame();
            }           
        }
        
        @Override
        public void close(){
            game.closeGame();
        }
                
        @Override
        public void mousePressed(MouseEvent e) {
            if(isReloading == false && GameLogic.bulletBar.getBullets()>0){                 
                AudioPlayer.getInstance().play("gunshot", 0);
                AudioPlayer.getInstance().play("reload", 0);
                reloadTime();
                buttons.forEach(button -> {
                        button.mousePressed(e);
                }); 
                pauseButtons.forEach(button -> {
                    button.mousePressed(e);
                });
                if(!isPaused){
                    GameLogic.bulletBar.loseBullet();           
                    GameLogic.entities.forEach((Entity entity) -> {
                        entity.mousePressed(e);
                    }); 
                }                          
            }
        }
        @Override
        public void mouseDragged(MouseEvent e){
            super.mouseDragged(e);
            pauseButtons.forEach(button -> {
                button.mouseMoved(e);
            });
        }
        
        @Override
        public void mouseMoved(MouseEvent e){
            super.mouseMoved(e);
            pauseButtons.forEach(button -> {
                button.mouseMoved(e);
            });
        }
        
        @Override
        public void keyPressed(KeyEvent e){
            if(e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE){
                isPaused = game.pauseUpdateLocation();
            }
        }
}
