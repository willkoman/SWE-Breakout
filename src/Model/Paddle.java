package Model;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import static util.Helpers.WIDTH;

public class Paddle {
    private float x, y; // Position
    private float width, height; // Size
    private float speed; // Horizontal speed

    private long window;

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
            System.out.println("left");
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) {
            moveRight(delta);
            System.out.println("right");
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
        GL11.glTranslatef(x, y, 0);
        GL11.glColor3f(1.0f, 1.0f, 1.0f); // Set color to white

        // Draw the paddle using immediate mode rendering
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2f(0, 0);
            GL11.glVertex2f(width, 0);
            GL11.glVertex2f(width, height);
            GL11.glVertex2f(0, height);
        }
        GL11.glEnd();

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

}
