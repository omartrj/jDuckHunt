package game;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioPlayer {     
        
        private static AudioPlayer audioPlayer = null;
        private final HashMap<String, Clip> sounds;
        
        private AudioPlayer(){
                sounds = new HashMap<>();
        }
        
        public static AudioPlayer getInstance(){
            if(audioPlayer == null) {
                audioPlayer = new AudioPlayer();
            }
            return audioPlayer;
        }
        
        public void loadSound(String soundPath, String name){            
            AudioInputStream audioInput = null;
            URL resource = getClass().getResource("/res/" + soundPath);
            try {
                audioInput = AudioSystem.getAudioInputStream(resource);                              
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);                
                sounds.put(name, clip);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.getStackTrace();
            } finally {
                try {
                    audioInput.close();
                } 
                catch (IOException ex) {
                    ex.getStackTrace();
                }
            }
        }
              
        
        public void play(String name, int loopCount){  
            if(sounds.containsKey(name)){
                sounds.get(name).loop(loopCount);
                sounds.get(name).setFramePosition(0);
            }
            
        }
        
        public void stop(String name){
            if(sounds.containsKey(name)) {
                sounds.get(name).stop();
            }
        }
        
        public boolean isMusicRunning(){
            return sounds.containsKey("mainTheme");
        }
        
        public boolean isSFXRunning(){
            return sounds.size() > 1;
        }
        
        public void loadMusic(){
            loadSound("sounds/mainTheme.wav", "mainTheme");
            changeVolume("mainTheme", -20);          
        }
        
        public void muteMusic(){
            sounds.get("mainTheme").stop();
            sounds.remove("mainTheme");           
        }
        
        public void muteSFX(){
            sounds.keySet().removeIf(k -> !k.equals("mainTheme"));
        }
        public void loadSFX(){           
            loadSound("sounds/levelClear.wav", "levelClear");
            loadSound("sounds/gunshot.wav", "gunshot");
            loadSound("sounds/bark.wav", "bark");
            loadSound("sounds/quack.wav", "quack");
            loadSound("sounds/reload.wav", "reload"); 
            loadSound("sounds/hitSound.wav", "hitSound");
            loadSound("sounds/1upSound.wav", "oneUpSound");
            loadSound("sounds/coinSound.wav", "coin");
            loadSound("sounds/select.wav", "select");
            loadSound("sounds/gameOver.wav", "gameOver");
            changeVolume("bark", -15);
            changeVolume("quack", -17);
            changeVolume("hitSound", -15);
            changeVolume("gunshot", -20);
            changeVolume("reload", -20);
            changeVolume("oneUpSound", -22);
            changeVolume("coin", -22);
            changeVolume("levelClear", -20);
            changeVolume("select", -5);
            changeVolume("gameOver", -15);
        }
               
        public void changeVolume(String name, int value){
            FloatControl control = (FloatControl)sounds.get(name).getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(value);
        }
                               
}
