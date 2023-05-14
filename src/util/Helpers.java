package util;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import Enum.SpriteType;

public class Helpers {

    private static double lastFrameTime;
    private static List<Texture> digits;
    private static Texture playerIdleTexture;
    private static Texture playerParryTexture;
    private static Texture enemy1IdleTexture;
    private static Texture enemy1FireTexture;
    private static Texture creditsTexture;
    public static int WIDTH = 800;
    public static int HEIGHT = 600;


    public static void init() {
        lastFrameTime = getTime();
        playerIdleTexture = new Texture("/sprite/parry-player.png");
        playerParryTexture = new Texture("/sprite/parry-playerattack.png");
        enemy1IdleTexture = new Texture("/sprite/parry-fire-final.png");
        enemy1FireTexture = new Texture("/sprite/parry-fireattack-final.png");
        creditsTexture = new Texture("/text/credits_full.png");
        digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            digits.add(new Texture("/text/" + i + ".png"));
        }
    }

    public static float getTimeDelta() {
        double currentTime = getTime();
        double timeDelta = currentTime - lastFrameTime;
        lastFrameTime = currentTime;
        return (float) timeDelta;
    }

    private static double getTime() {
        return GLFW.glfwGetTime();
    }

    public static void renderScore(int score, float x, float y, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glScalef(scale, scale, 1f);
        String scoreStr = String.valueOf(score);

        for (char c : scoreStr.toCharArray()) {
            int digit = Character.getNumericValue(c);
            Texture texture = digits.get(digit);

            // Bind the texture before rendering
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());

            GL11.glColor3f(1.0f, 1.0f, 1.0f); // Set color to white
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            // Render quad with texture
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glTexCoord2f(0, 0); GL11.glVertex2f(0, 0);                    // Top left
                GL11.glTexCoord2f(1, 0); GL11.glVertex2f(texture.getWidth(), 0);   // Top right
                GL11.glTexCoord2f(1, 1); GL11.glVertex2f(texture.getWidth(), texture.getHeight()); // Bottom right
                GL11.glTexCoord2f(0, 1); GL11.glVertex2f(0, texture.getHeight());  // Bottom left
            }
            GL11.glEnd();

            GL11.glTranslatef(texture.getWidth(), 0, 0); // Move right by the width of the digit
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }
        GL11.glPopMatrix();
    }
    public static void renderSprite(SpriteType spriteType, float x, float y, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glScalef(scale, scale, 1f);

        Texture texture = switch (spriteType) {
            case PLAYER_IDLE -> playerIdleTexture;
            case PLAYER_PARRY -> playerParryTexture;
            case ENEMY1_IDLE -> enemy1IdleTexture;
            case ENEMY1_FIRE -> enemy1FireTexture;
            case CREDITS -> creditsTexture;
            default -> throw new IllegalArgumentException("Invalid sprite type: " + spriteType);
        };

        // Bind the texture before rendering
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());

        float textureWidth = texture.getWidth();
        float textureHeight = texture.getHeight();
        float scaledWidth = textureWidth * scale;
        float scaledHeight = textureHeight * scale;

        GL11.glColor3f(1.0f, 1.0f, 1.0f); // Set color to white
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        // Render quad with texture
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(0, 0);                                       // Top left

            GL11.glTexCoord2f(textureWidth / texture.getWidth(), 0);
            GL11.glVertex2f(scaledWidth, 0);                            // Top right

            GL11.glTexCoord2f(textureWidth / texture.getWidth(), textureHeight / texture.getHeight());
            GL11.glVertex2f(scaledWidth, scaledHeight);                // Bottom right

            GL11.glTexCoord2f(0, textureHeight / texture.getHeight());
            GL11.glVertex2f(0, scaledHeight);                           // Bottom left
        }
        GL11.glEnd();

        GL11.glPopMatrix();
    }

}
