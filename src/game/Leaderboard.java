package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Leaderboard {
    
    private static Leaderboard lBoard;
    public static boolean showHighScore;
    private List<String[]> topScores = new ArrayList<>();
    private final String filePath;
    
    public Leaderboard(){     
        
        filePath = new File("").getAbsolutePath(); 
        showHighScore = true;
    
    }
    
    public static Leaderboard getInstance(){
        if(lBoard == null) {
            lBoard = new Leaderboard();
        }
        return lBoard;
    }
    
    private void createNewSaveFile(){
        try {
            File file = new File(filePath, "scores"); 
            file.createNewFile();
            FileWriter output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output); 
            writer.write("player player player player player");
            writer.newLine();
            writer.write("0 0 0 0 0");
            writer.newLine();
            writer.write("0 0 0 0 0");
            writer.close();
            
        } catch (IOException ex) {
            ex.getStackTrace();
        }
        
    }
    
    public void saveTopScores(){
        
        FileWriter output = null;
        try {
            File file = new File(filePath, "scores");
            output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            for (String[] topScore : topScores) {
                writer.write(topScore[0] + " ");
            }
            writer.newLine();
            for (String[] topScore : topScores) {
                writer.write(topScore[1] + " ");
            }
            writer.newLine();
            for (String[] topScore : topScores) {
                writer.write(topScore[2] + " ");
            }
            writer.close();
           
            
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }
    
    public void loadTopScores(){
        try {
            File file = new File(filePath, "scores");
            if(!file.exists()) {
                createNewSaveFile();
            }
        
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                topScores.clear();
                
                String[] names = reader.readLine().split(" ");
                String[] scores = reader.readLine().split(" ");
                String[] levels = reader.readLine().split(" ");
                
                for(int i=0; i<names.length; i++){
                    String[] tupla = new String[3];
                    tupla[0] = names[i];
                    tupla[1] = scores[i];
                    tupla[2] = levels[i];
                    topScores.add(tupla);
                }
                reader.close();
            }
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
        
    }
    
    public void saveScore(String name, int score, int level){
        String scoreString = String.valueOf(score);
        String lvlString = String.valueOf(level);
        String[] tupla = {name, scoreString, lvlString};
        topScores.add(tupla);

    }
    
    public void sortByScore(){
        showHighScore = true;
        int[] arrayToSort = new int[topScores.size()];
        for(int i = 0; i<arrayToSort.length; i++) {
            arrayToSort[i] = Integer.valueOf(topScores.get(i)[1]);
        }        
        for(int i=0; i<arrayToSort.length; i++) {
            for (int j = i; j<arrayToSort.length; j++) {
                if(arrayToSort[j] > arrayToSort[i]){
                    int temp = arrayToSort[i];
                    arrayToSort[i] = arrayToSort[j];
                    arrayToSort[j] = temp;
                    String name = topScores.get(i)[0];
                    String score = topScores.get(i)[1];
                    String level = topScores.get(i)[2];
                    topScores.get(i)[0] = topScores.get(j)[0];
                    topScores.get(i)[1] = topScores.get(j)[1];
                    topScores.get(i)[2] = topScores.get(j)[2];                 
                    topScores.get(j)[0] = name;
                    topScores.get(j)[1] = score;
                    topScores.get(j)[2] = level;
                }
            }
        }                  
    }
    
    public void sortByLevel(){
        showHighScore = false;
        int[] arrayToSort = new int[topScores.size()];
        for(int i = 0; i<arrayToSort.length; i++) {
            arrayToSort[i] = Integer.valueOf(topScores.get(i)[2]);
        }        
        for(int i=0; i<arrayToSort.length; i++) {
            for (int j = i; j<arrayToSort.length; j++) {
                if(arrayToSort[j] > arrayToSort[i]){
                    int temp = arrayToSort[i];
                    arrayToSort[i] = arrayToSort[j];
                    arrayToSort[j] = temp;
                    String name = topScores.get(i)[0];
                    String score = topScores.get(i)[1];
                    String level = topScores.get(i)[2];
                    topScores.get(i)[0] = topScores.get(j)[0];
                    topScores.get(i)[1] = topScores.get(j)[1];
                    topScores.get(i)[2] = topScores.get(j)[2];
                    topScores.get(j)[0] = name;
                    topScores.get(j)[1] = score;
                    topScores.get(j)[2] = level;
                }
            }
        }                  
    }
    
    public String[] getTopNames(){
        String[] topNames = new String[5];
        for(int i=0; i<5; i++){
            topNames[i] = topScores.get(i)[0];
        }
        return topNames;
    }
    
    public String[] getTopScores(){
        String[] topPoints = new String[5];
        for(int i=0; i<5; i++){
            topPoints[i] = topScores.get(i)[1];
        }
        return topPoints;
    }
    
    public String[] getTopLevels(){
        String[] topLevels = new String[5];
        for(int i=0; i<5; i++){
            topLevels[i] = topScores.get(i)[2];
        }
        return topLevels;
    }

    
}
