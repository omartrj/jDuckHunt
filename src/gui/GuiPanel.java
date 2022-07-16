package gui;

import game.AudioPlayer;
import game.Entity;
import game.GameLogic;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;
import utils.Constants;
import utils.Resources;


public abstract class GuiPanel extends JPanel implements MouseInputListener, KeyListener{
    
    private final int width = Constants.WIDTH;
    private final int height = Constants.HEIGHT;
    protected Graphics2D g2d;
    protected List<GuiButton> buttons;
    protected int xCursor, yCursor;
    protected BufferedImage customCursor;
    protected BufferedImage customCursorReload;
    protected BufferedImage cursorToDraw;
    protected boolean isReloading = false;
    protected Font zigFont;

    public GuiPanel() {
        super();
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);
        
        this.buttons = new ArrayList<>();
        customCursor = Resources.loadImage("images/cursor0.png");
        customCursorReload = Resources.loadImage("images/cursor1.png");
        cursorToDraw = customCursor;
        
        try {
            zigFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/res/fonts/zig.ttf"));
        } catch (FontFormatException | IOException ex) {
            ex.getStackTrace();
        }
    }
    
    public abstract void start();
    
    public abstract void close();
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2d = (Graphics2D) g;
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE){
            GameFrame.changeToPanel("menuPanel");
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(isReloading == false){
            AudioPlayer.getInstance().play("gunshot", 0);
            AudioPlayer.getInstance().play("reload", 0);
            reloadTime();                    
            GameLogic.entities.forEach((Entity entity) -> {
                entity.mousePressed(e);
            });
            buttons.forEach(button -> {
                button.mousePressed(e);
            });           
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        xCursor = e.getX()-32;
        yCursor = e.getY()-32;
        buttons.forEach(button -> {
            button.mouseMoved(e);
        });
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xCursor = e.getX()-32;
        yCursor = e.getY()-32;
        buttons.forEach(button -> {
            button.mouseMoved(e);
        });
    }

    protected void reloadTime() {
        isReloading = true;
        cursorToDraw = customCursorReload;       
        Timer timerReload = new Timer(1000, (ActionEvent arg0) -> {
            cursorToDraw = customCursor;
            isReloading = false;
        });
        timerReload.setRepeats(false);
        timerReload.start();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {} 
    @Override
    public void mouseEntered(MouseEvent e) {}   
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
   
}
