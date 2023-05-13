package Model;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import Enum.SpriteType;
import static util.Helpers.WIDTH;
import static util.Helpers.renderSprite;

public class Paddle {
    private float x;
    private final float y; // Position
    private final float width;
    private final float height; // Size
    private SpriteType spriteType = SpriteType.PLAYER_IDLE;
    private final float speed; // Horizontal speed
    private final long window; // GLFW window

    public Paddle(long window, float x, float y, float width, float height, float speed) {
        this.window = window;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    // Getters and setters for the attributes

    public void moveLeft(float delta) {
        x -= speed * delta;
    }

    public void moveRight(float delta) {
        x += speed * delta;
    }

    public void update(float delta) {
        // Move the paddle left or right based on keyboard input
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS) {
            moveLeft(delta);
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) {
            moveRight(delta);
        }

        // Constrain the paddle within the screen bounds
        if (x < 0) {
            x = 0;
        } else if (x + width > WIDTH) {
            x = WIDTH - width;
        }
    }

    public void render() {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y-64, 0);

        GL11.glColor3f(1.0f, 1.0f, 1.0f); // Set color to white
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND); // Disable blending to preserve texture colors
        //write ternary operator to change spriteType to PLAYER_IDLE if space is pressed
        spriteType = isSpacePressed() ? SpriteType.PLAYER_PARRY : SpriteType.PLAYER_IDLE;
        renderSprite(spriteType, 0, 0, 1.0f);

        GL11.glEnable(GL11.GL_BLEND); // Re-enable blending if needed
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    public boolean isSpacePressed() {

        return GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS;
    }

}
