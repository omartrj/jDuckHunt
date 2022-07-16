package gui;

import game.AudioPlayer;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import utils.Resources;


public class GameFrame extends JFrame {
    
        public static HashMap<String, GuiPanel> panels;
        public static Container contPane;
        public static String currentPanel;
    
        public GameFrame(){
            super("jDuckHunt");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            
            BufferedImage icon = Resources.loadImage("images/heart.png");                       
            setIconImage(icon);

            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");
            getContentPane().setCursor(blankCursor);
            
            loadPanels();
            loadSounds();

            pack();
            setLocationRelativeTo(null);
        }
   

        private void loadPanels() {
            contPane = getContentPane();
            BackgroundPanel backgroundPanel = new BackgroundPanel();
            PlayPanel playPanel = new PlayPanel();
            MenuPanel menuPanel = new MenuPanel();
            InfoPanel infoPanel = new InfoPanel();
            LeaderboardPanel leadPanel = new LeaderboardPanel();
            OptionPanel optsPanel = new OptionPanel();

            panels = new HashMap<>();        
            panels.put("menuPanel", menuPanel);
            panels.put("backgroundPanel", backgroundPanel);
            panels.put("playPanel", playPanel);
            panels.put("infoPanel", infoPanel);
            panels.put("leadPanel", leadPanel);
            panels.put("optsPanel", optsPanel);           

            currentPanel = "menuPanel";
            panels.get("backgroundPanel").add(panels.get(currentPanel), BorderLayout.CENTER);
            contPane.add(panels.get("backgroundPanel"), BorderLayout.CENTER);
        }

        public static void changeToPanel(String nextPanel){
            panels.get(currentPanel).close();
            panels.get("backgroundPanel").removeAll();
            contPane.removeAll();
            panels.get("backgroundPanel").add(panels.get(nextPanel), BorderLayout.CENTER);
            contPane.add(panels.get("backgroundPanel"), BorderLayout.CENTER);
            
            panels.get(nextPanel).start();
            currentPanel = nextPanel;
            contPane.revalidate();
            contPane.repaint();       

        }

        private void loadSounds() {
            AudioPlayer.getInstance().loadSFX();
            AudioPlayer.getInstance().loadMusic();           
            AudioPlayer.getInstance().play("mainTheme", Clip.LOOP_CONTINUOUSLY);
            
        } 
    
}
