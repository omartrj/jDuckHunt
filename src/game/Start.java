package game;

import gui.GameFrame;

public class Start {

        public static void main(String[] args) {
            javax.swing.SwingUtilities.invokeLater(() -> {                                                         
               new GameFrame().setVisible(true);                    
            });
              
        }
  
}
