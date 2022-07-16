package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Config {

    private static final String CONFIG_FILE_CHARSET = "ISO-8859-1";
    private static Config config = null;
    private Properties properties;
    private final String configPath;
    
    private Config(){
        
        configPath = new File("").getAbsolutePath();
        BufferedReader buffRead = null;
        try{
            buffRead = new BufferedReader(
                            new InputStreamReader(
                            new FileInputStream(getConfigFile()),CONFIG_FILE_CHARSET));
            this.properties = new Properties();
            this.properties.load(buffRead);
        }
        catch(FileNotFoundException fnfe){
            JOptionPane.showMessageDialog(null,
                                        "Configuration file not found!", 
                                        "Warning",
                                        JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(null,
                                        "Unable to read config file", 
                                        "Warning",
                                        JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        finally{
            try{
            if(buffRead != null) {
                buffRead.close();
            }
            }
            catch(IOException ioe){
            }
        }
    }
    
    public String[] getPercentEnemies(){  //Non serve avere la percentuale dei cuori in quanto sarà la differenza per arrivare a 1
        String duckPercent = this.properties.getProperty("duck%");
        String goldDuckPercent = this.properties.getProperty("goldDuck%");       
        String[] percentEnemies = {duckPercent, goldDuckPercent};
        return percentEnemies;
    }
    
    public String getBackground(){
        return this.properties.getProperty("sfondo");
    }
    
    public void setBackground(String background){
        this.properties.setProperty("sfondo", background);
    }
    
    public String getConfigFile(){
        File file = new File(configPath, "config.txt");
        if(!file.exists()) {
            createNewConfigFile();
        }        
        return file.getAbsolutePath();
        
    }
    
    private void createNewConfigFile(){
        try {
            File file = new File(configPath, "config.txt");
            file.createNewFile();
            FileWriter output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            
            writer.write("# Lista delle probabilità di generazione dei vari nemici (la somma deve essere 1)");
            writer.newLine();
            writer.write("duck% = 0.85");
            writer.newLine();
            writer.write("goldDuck% = 0.10");
            writer.newLine();
            writer.write("heart% = 0.05");
            writer.newLine();
            writer.newLine();
            writer.write("# Il parametro \"sfondo\" può essere uno dei seguenti: {\"giorno\", \"notte\"}");
            writer.newLine();
            writer.write("sfondo = giorno");
            writer.close();
            
        } catch (IOException ex) {
            ex.getStackTrace();
        } 
    }
    
    
    public static Config getInstance(){
        if (config == null) {
            config = new Config();
        }
        return config;
    }
    

       

}
