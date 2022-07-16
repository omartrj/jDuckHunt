package gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import utils.Resources;


public class InfoPanel extends GuiPanel {
    
    private final BufferedImage[] infoPanels = new BufferedImage[2];
    private BufferedImage frameToDraw;
    private int indexAnimation;   
    private Timer timer = null;   
    private final GuiButton menuButton = new GuiButton("menu", 20, 678);
    
    public InfoPanel(){
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        
        infoPanels[0] = Resources.loadImage("images/infoPanel0.png");
        infoPanels[1] = Resources.loadImage("images/infoPanel1.png");
        frameToDraw = infoPanels[0];
        
        buttons.add(menuButton);
        
        startAnimation(500);

    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);       
        g2d.drawImage(frameToDraw, 0, 0, this);
        menuButton.draw(g2d, this);
        g2d.drawImage(cursorToDraw, xCursor, yCursor, this);
        repaint();
    }
    
    private void startAnimation(int delay){
        int delayTimer = delay;
        timer = new Timer(delayTimer, (ActionEvent arg0) -> {           
            changeFrame();
        });
        timer.setRepeats(true); 
    }
    
    public void changeFrame() {
        if(indexAnimation < infoPanels.length - 1) {
            indexAnimation++;
        } else {
            indexAnimation = 0;
        }
            
        frameToDraw = infoPanels[indexAnimation];
    }
    
    @Override
    public void start(){  
        requestFocus();
        timer.start();
        xCursor = 1300;
        yCursor = 40;
    }
    
    @Override
    public void close(){
        if(timer != null) {
            timer.stop();
        }
    }


}
