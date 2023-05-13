package Model;

import org.lwjgl.opengl.GL11;
import java.util.ArrayList;
import java.util.List;
import Enum.SpriteType;

import static util.Helpers.renderSprite;

public class Enemy extends Block {
    private final float fireInterval; // Fire a bullet every second
    private float timeSinceLastFire = 0; // Time since last bullet was fired
    private SpriteType spriteType = SpriteType.ENEMY1_IDLE;
    private final List<Bullet> bullets; // List of bullets fired by this enemy
    private final Game game; // Reference to the game

    public Enemy(float x, float y, float width, float height, Game game) {
        super(x, y, width, height);
        this.game = game;
        //random number between 0.5 and 1.0
        this.fireInterval = (float) (Math.random() * 0.5 + 0.5);
        bullets = new ArrayList<>();
    }

    @Override
    public void update(float delta, Paddle player) {
        if (!active) return; // if the enemy is not active, don't update or fire bullets

        timeSinceLastFire += delta;
        if (timeSinceLastFire >= fireInterval/2 && timeSinceLastFire < fireInterval)
            spriteType = SpriteType.ENEMY1_IDLE;

        if (timeSinceLastFire >= fireInterval) {
            spriteType = SpriteType.ENEMY1_FIRE;
            fireBullet(player);
            timeSinceLastFire = 0;
        }


        List<Bullet> bulletsToRemove = new ArrayList<>(); // List to hold bullets marked for removal

        for (Bullet bullet : bullets) {
            bullet.update(delta, player, getGame().getBlocks());
            if (!bullet.isActive()) {
                bulletsToRemove.add(bullet);
            }
        }

        bullets.removeAll(bulletsToRemove); // Remove all bullets marked for removal
    }


    private void fireBullet(Paddle player) {

        // Calculate the direction from the enemy to the player
        float dx = player.getX() - getX();
        float dy = player.getY() - getY();
        float magnitude = (float) Math.sqrt(dx * dx + dy * dy);

        // Normalize the direction
        float vx = 1 * (dx / magnitude); // Assign the speed here
        float vy = 1 * (dy / magnitude);

        // Create a new bullet and add it to the list
        Bullet bullet = new Bullet(getX()+32, getY()+8, vx, vy, this); // Adjust radius to 10
        bullets.add(bullet);

    }


    @Override
    public void render() {
        if (active) {
            GL11.glPushMatrix();
            GL11.glTranslatef(x+16, y, 0);

            GL11.glColor3f(1.0f, 1.0f, 1.0f); // Set color to white
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND); // Disable blending to preserve texture colors

            renderSprite(spriteType, 0, 0, 1.0f);

            GL11.glEnable(GL11.GL_BLEND); // Re-enable blending if needed
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            GL11.glPopMatrix();
        }

        for (Bullet bullet : bullets) {
            bullet.render(); // Render the bullets
        }

    }

    public Game getGame() {
        return game;
    }
    public List<Bullet> getBullets() {
        return bullets;
    }
}
