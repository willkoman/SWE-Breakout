package Model;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL11;
import util.Texture;
import Enum.GameState;

import java.nio.IntBuffer;

public class Menu {

    private int selectedOption;
    private final Texture[] options;
    private final long window;
    private final Game game;
    private static final float SELECTION_DELAY = 0.2f; // Delay in seconds
    private float selectionTimer = 0;

    public Menu(long window, Game game) {
        this.window = window;
        this.selectedOption = 0;
        this.game = game;
        this.options = new Texture[] {
                new Texture("src/resources/text/play.png"),
                new Texture("src/resources/text/highscore.png"),
                new Texture("src/resources/text/quit.png"),
        };
    }

    public void render() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetWindowSize(window, w, h);
        int width = w.get(0);
        int height = h.get(0);

        for (int i = 0; i < options.length; i++) {
            Texture optionTexture = options[i];
            boolean isSelected = i == selectedOption;
            float scale = isSelected ? 1.2f : 1.0f;

            GL11.glPushMatrix();

            // Calculate x and y positions to center the option
            float x = (width - optionTexture.getWidth() * scale) / 2;
            float y = (height - options.length * optionTexture.getHeight() * scale) / 2 + i * optionTexture.getHeight() * scale;

            GL11.glTranslatef(x, y, 0); // Centered position
            GL11.glScalef(scale, scale, 1f);

            GL11.glColor3f(1.0f, 1.0f, 1.0f); // Set color to white
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, optionTexture.getId());
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            // Render quad with texture
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glTexCoord2f(0, 0); GL11.glVertex2f(0, 0);
                GL11.glTexCoord2f(1, 0); GL11.glVertex2f(optionTexture.getWidth(), 0);
                GL11.glTexCoord2f(1, 1); GL11.glVertex2f(optionTexture.getWidth(), optionTexture.getHeight());
                GL11.glTexCoord2f(0, 1); GL11.glVertex2f(0, optionTexture.getHeight());
            }
            GL11.glEnd();
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            GL11.glPopMatrix();
        }
    }


    public void update(float delta) {
        selectionTimer -= delta;
        if (selectionTimer <= 0) {
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS) {
                selectedOption = (selectedOption - 1 + options.length) % options.length;
                selectionTimer = SELECTION_DELAY;
            } else if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS) {
                selectedOption = (selectedOption + 1) % options.length;
                selectionTimer = SELECTION_DELAY;
            } else if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS) {
                selectOption();
            }
        }
    }

    private void selectOption() {
        switch (selectedOption) {
            case 0: // Play
                game.setGameState(GameState.PAUSE);
                break;
            case 1: // High Score
                // TODO: Show high scores.
                break;
            case 2: // Quit
                System.exit(0);
                break;
        }
    }
}