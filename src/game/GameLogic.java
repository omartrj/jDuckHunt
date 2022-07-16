package game;

import gui.BulletBar;
import gui.GameFrame;
import gui.HealthBar;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import utils.Config;

public class GameLogic {
    public static  int numDucks;
    public static int numDucksDied;
    private int numDucksSpawn;
    public static List<Entity> entities = new ArrayList<>();        
    public static HealthBar healthBar = new HealthBar(); 
    public static BulletBar bulletBar = new BulletBar();
    private Timer timerAnim, timerLoc, timerSpawn;
    private final String[] percentEnemiesStr;
    private final Double[] percentEnemies;
    private String namePlayer;
    private boolean bool = true;
    
    public GameLogic(){              
        entities.add(new Doge());        
        startAnimations();
        updateLocation();    
        
        percentEnemiesStr = Config.getInstance().getPercentEnemies();
        percentEnemies = new Double[2];  
        percentEnemies[0] = Double.parseDouble(percentEnemiesStr[0]);
        percentEnemies[1] = percentEnemies[0] + Double.parseDouble(percentEnemiesStr[1]);
    }
    
    public boolean checkGameOver(){
        return healthBar.getLives() == 0;
    }
    
    public boolean checkLevelEnd(){
        return numDucks == numDucksDied;
    }
    
    public void nextLevel(){
        numDucks++;
        numDucksDied = 0;
        numDucksSpawn = 0;
        Level.getInstance().nextLevel();               
        bulletBar.setBullets(numDucks + 2);
        timerSpawn.setInitialDelay(3000);
        timerSpawn.restart();
    }
    
    private void updateLocation(){
        timerLoc = new Timer(30, (ActionEvent arg0) -> {  
            try{
                entities.forEach(entity -> {
                    entity.updateLocation(false);
                });
            }
            catch(ConcurrentModificationException ex){}               
        });
        timerLoc.setRepeats(true); 
        timerLoc.start();
    }
    
    public boolean pauseUpdateLocation(){
        if(timerLoc.isRunning()){
            timerLoc.stop();
            timerSpawn.stop();
            return true;
        }
        else{
            timerLoc.start();
            timerSpawn.setInitialDelay(1000);
            timerSpawn.start();
            return false;
        }
    }
    
    private void startAnimations(){
        timerAnim = new Timer(500, (ActionEvent arg0) -> {           
            entities.forEach(entity -> {
                entity.changeFrame();
            });
            healthBar.animate();
            bulletBar.animate();
        });
        timerAnim.setRepeats(true); 
        timerAnim.start();
    }
    
    public void setNewGame(){
        numDucks = 3;
        numDucksDied = 0;
        numDucksSpawn = 0;
        bool = true;
        Level.getInstance().resetLevel();
        ScoreData.getInstance().resetScore();
        healthBar.reset();
        bulletBar.setBullets(GameLogic.numDucks + 2);
        if(!timerLoc.isRunning()) {
            timerLoc.start();
        }
    }
    
    public void closeGame(){
        if(timerSpawn != null)
            timerSpawn.stop();
        try{
            entities.stream().filter(entity -> (entity instanceof Enemy)).forEachOrdered(entity -> {
                entity.removeEnemy();
            });
        }
        catch(ConcurrentModificationException ex){}
        
        if(timerLoc.isRunning()) {
            timerLoc.stop();
        }
        numDucks = 3;
        numDucksDied = 0;
    }
    
    public void startNewGame(){
        timerSpawn = new Timer(1100, (ActionEvent arg0) -> {          
            addNewEnemy();                         
        });                 
        timerSpawn.setRepeats(true);
        timerSpawn.setInitialDelay(3000);
        timerSpawn.start();
        
    }
    
    private void addNewEnemy(){
        if(numDucksSpawn < numDucks){
            numDucksSpawn++;
            double ran = new Random().nextDouble();
            if(ran<=percentEnemies[0]) {
                entities.add(new Duck());
            } else if(ran<=percentEnemies[1]) {
                entities.add(new GoldenDuck());
            } else {
                entities.add(new FlyingHeart());
            }
        }                
    }
    public void saveScoreData(){
        if(bool){
            Leaderboard.getInstance().saveScore(namePlayer, ScoreData.getInstance().getScore(), Level.getInstance().getCurrentLevel());
            Leaderboard.getInstance().saveTopScores();   
            AudioPlayer.getInstance().stop("mainTheme");
            AudioPlayer.getInstance().play("gameOver", 0);
            Timer timerSound = new Timer(2000, (ActionEvent e) -> {
                AudioPlayer.getInstance().play("mainTheme", Clip.LOOP_CONTINUOUSLY);
            });
            timerSound.setRepeats(false);
            timerSound.start();
            bool = false;
        }
    }
        
    public boolean askPlayerName(){
        namePlayer = JOptionPane.showInputDialog(new JFrame(),
                                                new JLabel("Inserisci nome del giocatore"), 
                                                "jDuckHunt", 
                                                JOptionPane.QUESTION_MESSAGE);
        do{
            if(namePlayer == null){                  
                GameFrame.changeToPanel("menuPanel"); 
                break;
            }
            else if(namePlayer.isBlank()){
                namePlayer = JOptionPane.showInputDialog(new JFrame(), new JLabel("<html>Nome inserito non valido!<br/>"
                                                                              + "Inserisci nome del giocatore</html>"),
                                                                                "jDuckHunt",
                                                                                JOptionPane.WARNING_MESSAGE);                                                                                    
            }
        }
        while(namePlayer == null || namePlayer.isBlank());
        return namePlayer != null && !namePlayer.isBlank();
    }
    
    
}
