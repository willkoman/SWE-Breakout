package Model;

import org.lwjgl.opengl.GL11;

public class Block {
    private float x, y, width, height;
    private boolean active;

    public Block(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.active = true;
    }

    public void render() {
        if (active) {
            GL11.glPushMatrix();
            GL11.glTranslatef(x, y, 0);
            GL11.glColor3f(1.0f, 1.0f, 1.0f); // Set color to white

            // Draw the block
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
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
