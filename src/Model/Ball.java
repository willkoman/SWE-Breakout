package Model;

import org.lwjgl.opengl.GL11;
import util.SoundManager;

import static util.Helpers.HEIGHT;
import static util.Helpers.WIDTH;

public class Ball {
    protected float x, y; // Position
    protected Game game;
    protected float radius; // Radius
    protected float vx, vy; // Speed in both X and Y directions


    public Ball(float x, float y, float radius, float vx, float vy, Game game) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.game = game;
    }


    public void update(float delta, Paddle paddle, Block[][] blocks) {
        x += vx * delta;
        y += vy * delta;


        checkCollisionWithScreenBounds();
        checkCollisionWithPaddle(paddle);
        checkCollisionWithBlocks(blocks);
    }


    private void checkCollisionWithBlocks(Block[][] blocks) {
        for (Block[] value : blocks) {
            for (Block block : value) {
                if (block != null && block.isActive() && collidesWith(block.getX(), block.getY(), block.getWidth(), block.getHeight())) {
                    float dx = x - (block.getX() + block.getWidth() / 2);
                    float dy = y - (block.getY() + block.getHeight() / 2);

                    if (Math.abs(dx) > Math.abs(dy)) {
                        vx = -vx;
                    } else {
                        vy = -vy;
                    }
                    SoundManager.playSound("/sound/boop.ogg", 1.0f, false);
                    //if block is of class Enemy, get all bullets and set them to inactive
                    if (block instanceof Enemy) {
                        for (Bullet bullet : ((Enemy) block).getBullets()) {
                            bullet.setActive(false);
                        }
                    }
                    block.setActive(false);
                    game.incrementScore(50);
                }
            }
        }
    }


    private void checkCollisionWithScreenBounds() {
        if (x - radius < 0) {
            SoundManager.playSound("/sound/beep.ogg", 1.0f, false);
            x = radius;
            vx = Math.abs(vx);
        } else if (x + radius > WIDTH) {
            SoundManager.playSound("/sound/beep.ogg", 1.0f, false);
            x = WIDTH - radius;
            vx = -Math.abs(vx);
        }

        if (y - radius < 0) {
            SoundManager.playSound("/sound/beep.ogg", 1.0f, false);
            y = radius;
            vy = Math.abs(vy);
        } else if (y + radius > HEIGHT) {
            //Reset the ball
            //==TODO: Decrement Player Health==//
            game.incrementScore(-100);
            game.incrementHealth(-20);
            x = WIDTH / 2f;
            y = HEIGHT / 2f;
            vx = 300;
            vy = 300;

        }
    }

    private void checkCollisionWithPaddle(Paddle paddle) {
        if (collidesWith(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight())) {
            float paddleCenter = paddle.getX() + paddle.getWidth() / 2;
            float ballCenter = x + radius;
            float relativeIntersection = (ballCenter - paddleCenter) / (paddle.getWidth() / 2);

            // Normalize the relative intersection to the range [-1, 1]
            relativeIntersection = Math.max(-1, Math.min(1, relativeIntersection));

            // Calculate the new angle based on the relative intersection
            // The angle varies from -60 degrees to 60 degrees on the Y-axis
            // and from -15 degrees to 15 degrees on the X-axis
            float newAngle = (float) (relativeIntersection * 30 * Math.PI / 180);

            // Update the ball's velocity based on the new angle
            float speed = (float) Math.sqrt(vx * vx + vy * vy);
            vy = -(float) (speed * Math.cos(newAngle));
            vx = (float) (speed * Math.sin(newAngle));  // The minus sign is because y-coordinates decrease upwards

            // Ensure the ball is above the paddle to avoid getting stuck
            y = paddle.getY() - radius;
            SoundManager.playSound("/sound/boop.ogg", 1.0f, false);
        }
    }




    boolean collidesWith(float rectX, float rectY, float rectWidth, float rectHeight) {
        float closestX = Math.max(rectX, Math.min(x, rectX + rectWidth));
        float closestY = Math.max(rectY, Math.min(y, rectY + rectHeight));

        float dx = x - closestX;
        float dy = y - closestY;

        return dx * dx + dy * dy <= radius * radius;
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

//    public float getY() {
//        return y;
//    }
//
//    public float getRadius() {
//        return radius;
//    }
//
//    public float getVx() {
//        return vx;
//    }
//
//    public float getVy() {
//        return vy;
//    }

}
