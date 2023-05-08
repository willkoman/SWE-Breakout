package Model;

import org.lwjgl.opengl.GL11;

import static util.Helpers.HEIGHT;
import static util.Helpers.WIDTH;

public class Ball {
    private float x, y; // Position
    private float radius; // Radius

    private float speedX, speedY; // Speed in both X and Y directions
    private long window;

    public Ball(long window, float x, float y, float radius, float speedX, float speedY) {
        this.window = window;

        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
    }


    public void update(float delta, Paddle paddle, Block[][] blocks){
        // Move the ball
        x += speedX * delta;
        y += speedY * delta;

        // Check collision with the screen boundaries
        if (x - radius <= 0) {
            x = radius;
            speedX = -speedX;
        }
        if (x + radius >= WIDTH) {
            x = WIDTH - radius;
            speedX = -speedX;
        }
        if (y - radius <= 0) {
            y = radius;
            speedY = -speedY;
        }

        // Check collision with the paddle
        if (y + radius >= paddle.getY() && y <= paddle.getY() + paddle.getHeight() &&
                x + radius >= paddle.getX() && x - radius <= paddle.getX() + paddle.getWidth()) {
            y = paddle.getY() - (radius * 2);
            speedY = -speedY;
        }

        // Check if the ball falls off the screen
        if (y + radius * 2 >= HEIGHT) {
            // Reset the ball to the center of the screen
            //==TODO==: Decrement the player's health :)


            x = WIDTH / 2;
            y = HEIGHT / 2;


        }
        // Check collision with the blocks
        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks[row].length; col++) {
                Block block = blocks[row][col];
                if (block.isActive() && checkBallCollisionWithBlock(block)) {
                    // Toggle the active state of the block, and change the ball's vertical speed
                    block.setActive(false);
                    speedY = -speedY;
                    break;
                }
            }
        }
    }

    private boolean checkBallCollisionWithBlock (Block block){
        float ballLeft = x - radius;
        float ballRight = x + radius;
        float ballTop = y - radius;
        float ballBottom = y + radius;

        float blockLeft = block.getX();
        float blockRight = block.getX() + block.getWidth();
        float blockTop = block.getY();
        float blockBottom = block.getY() + block.getHeight();

        return ballLeft < blockRight && ballRight > blockLeft &&
                ballTop < blockBottom && ballBottom > blockTop;
    }


    public void render() {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glColor3f(1.0f, 1.0f, 1.0f); // Set color to white

        // Draw the ball using immediate mode rendering
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
    }

    // Getters and setters for the attributes

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

}
