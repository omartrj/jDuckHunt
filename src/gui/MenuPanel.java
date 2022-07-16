package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import utils.Resources;


public class MenuPanel extends GuiPanel {
    
    private final GuiButton playButton = new GuiButton("play", 500,310);
    private final GuiButton infoButton = new GuiButton("info", 1275, 20);
    private final GuiButton exitButton = new GuiButton("exit", 20,678);
    private final GuiButton leaderButton = new GuiButton("lead", 582, 75);  
    private final GuiButton optionsButton = new GuiButton("opts", 40, 20);
    private final BufferedImage playChain;
    private final BufferedImage leaderChain;
    
    
    public MenuPanel(){
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);

        buttons.add(playButton);
        buttons.add(leaderButton);
        buttons.add(exitButton);
        buttons.add(infoButton);  
        buttons.add(optionsButton);
        
        playChain = Resources.loadImage("images/playChain.png");
        leaderChain = Resources.loadImage("images/leaderChain.png");
    }
    
    @Override
    public void start(){
        requestFocus();
        xCursor = 50;
        yCursor = 670;
    }
    
    @Override
    public void close(){
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.drawImage(playChain, 520, 0, this);
        g2d.drawImage(playChain, 852, 0, this);
        g2d.drawImage(leaderChain, 602, 0, this);
        g2d.drawImage(leaderChain, 774, 0, this);
        buttons.forEach((GuiButton button) -> {               
                button.draw(g2d, this);
            });
        g2d.drawImage(cursorToDraw, xCursor, yCursor, this);
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE){
            GameFrame.changeToPanel("optsPanel");
        }
    }

}
