package Model;

import Enum.GameState;
import Interface.ILevelLayout;
import LevelLayouts.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import util.SoundManager;
import util.Texture;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static util.Helpers.renderScore;

public class Game {
    private Level currentLevel;
    private int currentLevelNumber;
    private final Map<Integer, ILevelLayout> levelLayouts;
    private int health = 100;
    private int score = 0;
    private GameState gameState;
    private Credits credits;
    private final Menu menu;
    private final long window;
    private final int width;
    private final int height;

    public Game(long window, int width, int height) {
        this.width = width;
        this.height = height;
        this.window = window;
        this.gameState = GameState.MENU;
        this.menu = new Menu(window, this);
        levelLayouts = new HashMap<>();
        initLevelLayouts();
        currentLevelNumber = 1;
        loadLevel(width, height, currentLevelNumber);
        SoundManager.loadSound("/sound/beep.ogg");
        SoundManager.loadSound("/sound/boop.ogg");
        this.credits = new Credits(this, width, height, window);
    }

    private void initLevelLayouts() {
        levelLayouts.put(1, new Level1());
        levelLayouts.put(2, new Level2());
        levelLayouts.put(3, new Level3());
        levelLayouts.put(4, new Level4());
        levelLayouts.put(5, new Level5());
    }

    private void loadLevel(int width, int height, int levelNumber) {
        ILevelLayout levelLayout = levelLayouts.get(levelNumber);
        if (levelLayout != null) {
            currentLevel = new Level(window, width, height, levelLayout, this);
        }
    }

    public void update(float delta) {
        if (gameState == GameState.MENU) {
            menu.update(delta);
        } else if (gameState == GameState.PAUSE) {
            currentLevel.render();
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) {
                gameState = GameState.GAME; // Transition to GAME state when player presses either left or right key
            }
        } else if (gameState == GameState.GAME) {
            currentLevel.update(delta);
            if (currentLevel.allBlocksDestroyed()) {
                nextLevel(width, height);
            }
        }
        else if (gameState == GameState.CREDITS){
            credits.update();
            credits.render();

        }
    }


    public void render() {
        if (gameState == GameState.MENU) {
            menu.render();
        } else if (gameState == GameState.GAME) {
            currentLevel.render();
            renderUI();
        }
        int error = GL11.glGetError();
        if (error != GL11.GL_NO_ERROR) {
            System.out.println("OpenGL Error: " + error);
        }
    }

    public void nextLevel(int width, int height) {
        currentLevelNumber++;
        loadLevel(width, height, currentLevelNumber);
    }

//    public void resetLevel(int width, int height) {
//        loadLevel(width, height, currentLevelNumber);
//    }

    public void incrementScore(int score) {
        this.score += score;
        if(this.score<0)
            this.score = 0;
    }

    public void incrementHealth(int health) {
        this.health += health;
        if(this.health<=0)
            gameOver();
    }

    public void renderUI() {
        // Render health
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetWindowSize(window, w, h);
        //int width = w.get(0);
        int height = h.get(0);

        glColor3f(0.0f, 1.0f, 0.0f); // Red color
        glBegin(GL_QUADS);
        glVertex2f(10, height - 30); // Bottom left
        glVertex2f( (health*8)- 10, height - 30); // Bottom right
        glVertex2f( (health*8)- 10, height - 10); // Top right
        glVertex2f(10, height - 10); // Top left
        glEnd();

        // Render score
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        renderScore(score, 0, 0, 0.5f);
    }


//    public GameState getGameState() {
//        return gameState;
//    }

    private void gameOver() {
        gameState = GameState.MENU;
        health = 100;
        score = 0;
        currentLevelNumber = 1;
        loadLevel(width, height, currentLevelNumber);
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Block[][] getBlocks() {
        return currentLevel.getBlocks();
    }


    // Add any other methods to manage the game state, e.g. check for level completion
}