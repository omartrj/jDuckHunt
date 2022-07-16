package gui;

import java.awt.Color;
import java.awt.Graphics;

public class OptionPanel extends GuiPanel{
    
    private final GuiButton musicButton = new GuiButton("music", 507, 200);
    private final GuiButton sfxButton = new GuiButton("sfx", 725, 200);
    private final GuiButton sunButton = new GuiButton("sun", 507, 420);
    private final GuiButton moonButton = new GuiButton("moon", 763, 420);
    private final GuiButton menuButton = new GuiButton("menu", 20, 678);
    
    public OptionPanel(){
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);       
        
        buttons.add(musicButton);
        buttons.add(sfxButton);
        buttons.add(sunButton);
        buttons.add(moonButton);
        buttons.add(menuButton);
                   
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        buttons.forEach((GuiButton button) -> {               
                button.draw(g2d, this);
            });
        g2d.setFont(zigFont.deriveFont(30f));
        g2d.setColor(Color.BLACK);
        g2d.drawString("GIORNO", 500, 600);
        g2d.drawString("NOTTE", 770, 600);
        g2d.setFont(zigFont.deriveFont(80f));
        g2d.setColor(Color.WHITE);
        g2d.drawString("OPZIONI", 480, 130);
        g2d.drawImage(cursorToDraw, xCursor, yCursor, this);       
        repaint();
        
    }
    
    @Override
    public void start() {
        requestFocus();
        buttons.forEach(button -> {
            button.updateSoundButtons();
        });
        xCursor = 50;
        yCursor = 30;
    }

    @Override
    public void close() {
        
    }
 
}
