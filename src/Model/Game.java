package Model;

import Interface.ILevelLayout;
import LevelLayouts.Level1Layout;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private Level currentLevel;
    private int currentLevelNumber;
    private Map<Integer, ILevelLayout> levelLayouts;

    public Game(long window, int width, int height) {
        levelLayouts = new HashMap<>();
        initLevelLayouts();
        currentLevelNumber = 1;
        loadLevel(window, width, height, currentLevelNumber);
    }

    private void initLevelLayouts() {
        levelLayouts.put(1, new Level1Layout());
        // Add more level layouts here
        // levelLayouts.put(2, new Level2Layout());
        // levelLayouts.put(3, new Level3Layout());
    }

    private void loadLevel(long window, int width, int height, int levelNumber) {
        ILevelLayout levelLayout = levelLayouts.get(levelNumber);
        if (levelLayout != null) {
            currentLevel = new Level(window, width, height, levelLayout);
        }
    }

    public void update(float delta) {
        currentLevel.update(delta);
    }

    public void render() {
        currentLevel.render();
    }

    public void nextLevel(long window, int width, int height) {
        currentLevelNumber++;
        loadLevel(window, width, height, currentLevelNumber);
    }

    // Add any other methods to manage the game state, e.g. check for level completion
}
