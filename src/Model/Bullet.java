package Model;

import org.lwjgl.opengl.GL11;

public class Bullet extends Ball {
    private static final float BULLET_RADIUS = 5f; // Radius of the bullet
    private static final float BULLET_SPEED = 500f; // Speed of the bullet
    private boolean active = true; // Whether the bullet is active or not

    public Bullet(float x, float y, float vx, float vy, Enemy parent) {
        super(x, y, BULLET_RADIUS, vx * BULLET_SPEED, vy * BULLET_SPEED, parent.getGame());
        // The enemy that fired this bullet
    }

    @Override
    public void update(float delta, Paddle paddle, Block[][] blocks) {
        // Update the bullet's position
        x += vx * delta;
        y += vy * delta;

        // Check for collision with the player
        if (collidesWith(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight())) {
            // If the bullet is within 20 pixels from the paddle and the player presses space, deflect the bullet
            if (Math.abs(y - (paddle.getY() + paddle.getHeight())) <= 20 && paddle.isSpacePressed()) {
                vy = -Math.abs(vy); // Reverse the vertical velocity to deflect the bullet
            } else {
                game.incrementHealth(-10);
                this.active = false;
            }
        }
    }
    @Override
    public void render() {
        if (!active) {
            return;
        }
        //System.out.println("Rendering bullet at position: " + x + ", " + y);

        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glColor3f(1.0f, 0.0f, 0.0f); // Set color to red

        // Draw the bullet using immediate mode rendering
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        {
            GL11.glVertex2f(0, 0);
            for (int i = 0; i <= 360; i++) {
                double angle = Math.toRadians(i);
                float xPos = (float) (radius * Math.cos(angle));
                float yPos = (float) (radius * Math.sin(angle));
                GL11.glVertex2f(xPos, yPos);
            }
        }
        GL11.glEnd();

        GL11.glPopMatrix();

        int error = GL11.glGetError();
        if (error != GL11.GL_NO_ERROR) {
            System.out.println("OpenGL Error: " + error);
        }
    }




    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
