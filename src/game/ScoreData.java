package game;

public class ScoreData {
    
        public static ScoreData scoreData = null;
        private int score;

        private ScoreData(){
            score = 0;
        }

        public static ScoreData getInstance(){
            if(scoreData == null) {
                scoreData = new ScoreData();
            }
            return scoreData;
        }

        public void addScore(int punteggio){
            score += punteggio;
        }

        public void resetScore(){
            score = 0;
        }
        
        public int getScore(){
            return score;
        }

        public String getScoreAsString(){
            String finalScore = "";       
            for(int i=5; i>String.valueOf(score).length(); i--) {
                finalScore += "0";
            }
            finalScore += Integer.toString(score);
            return finalScore;
        }
}
