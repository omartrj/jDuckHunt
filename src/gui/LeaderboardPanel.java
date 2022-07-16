package gui;

import game.Leaderboard;
import java.awt.Color;
import java.awt.Graphics;


public class LeaderboardPanel extends GuiPanel {
    
    private final GuiButton menuButton = new GuiButton("menu", 20, 678);
    private final GuiButton showLevelButton = new GuiButton("level", 900, 250);
    private final GuiButton showScoreButton = new GuiButton("score", 900, 350);
    private String[] topNames, topScores, topLevels;
    
    public LeaderboardPanel(){
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        buttons.add(menuButton);
        buttons.add(showLevelButton);
        buttons.add(showScoreButton);
        
        Leaderboard.getInstance().loadTopScores();        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2d.setColor(Color.WHITE);
        g2d.setFont(zigFont.deriveFont(80f));
        g2d.drawString("HIGHSCOREs", 390, 200);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(zigFont.deriveFont(30f));
        
        for(int i=0; i<topNames.length; i++){
            g2d.drawString(i+1 + ". " + topNames[i], 350, 300 + i*g2d.getFontMetrics().getHeight());
        }
        
        if(Leaderboard.showHighScore){
            topNames = Leaderboard.getInstance().getTopNames();
            topScores = Leaderboard.getInstance().getTopScores();
            topLevels = Leaderboard.getInstance().getTopLevels();
            for(int i=0; i<topScores.length; i++){
                g2d.drawString(topScores[i], 750, 300+i*g2d.getFontMetrics().getHeight());
            }
        }
        else{
            topNames = Leaderboard.getInstance().getTopNames();
            topScores = Leaderboard.getInstance().getTopScores();
            topLevels = Leaderboard.getInstance().getTopLevels();
            for(int i=0; i<topLevels.length; i++){
                g2d.drawString(topLevels[i], 750, 300+i*g2d.getFontMetrics().getHeight());
            }
        }
        
        buttons.forEach(button -> {
            button.draw(g2d, this);
        });
        g2d.drawImage(cursorToDraw, xCursor, yCursor, this);
        repaint();
        
    }
    
    @Override
    public void start() {
        requestFocus();
        Leaderboard.getInstance().loadTopScores();
        Leaderboard.getInstance().sortByScore();
        topNames = Leaderboard.getInstance().getTopNames();
        topScores = Leaderboard.getInstance().getTopScores();
        topLevels = Leaderboard.getInstance().getTopLevels();
        xCursor = 650;
        yCursor = 100;
    }

    @Override
    public void close() {
    }

}
