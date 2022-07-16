package game;

public class Level {
    
        public static Level level = null;
        private int currentLevel;

        private Level(){
            currentLevel = 1;
        }

        public static Level getInstance(){
            if(level == null) {
                level = new Level();
            }
            return level;
        }

        public int getCurrentLevel(){
            return currentLevel;
        }

        public void resetLevel(){
            currentLevel = 1;
        }

        public void nextLevel(){
            currentLevel++;
            AudioPlayer.getInstance().play("levelClear", 0);
        } 
}
