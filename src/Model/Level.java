package Model;

import Interface.ILevelLayout;
import org.lwjgl.opengl.GL11;

public class Level {
    private final Block[][] blocks;
    private final Paddle paddle;
    private final Ball ball;
    //private long window;

    public Level(long window, int width, int height, ILevelLayout levelLayout, Game game) {
        paddle = new Paddle(window, (float) width / 2 - 50, height - 60, 64, 10, 500);
        ball = new Ball((float) width / 2, (height / 1.6f), 10, 300, 300, game);
        blocks = levelLayout.createLayout(game); // Use the level layout to create the blocks
    }

    public void update(float delta) {
        paddle.update(delta);
        ball.update(delta, paddle, blocks);

        for (Block[] row : blocks) {
            for (Block block : row) {
                if (block instanceof Enemy) { // Check if the block is an instance of Enemy
                    block.update(delta, paddle); // Update the enemy
                }
            }
        }
    }

    public void render() {
        paddle.render();
        ball.render();
        for (Block[] row : blocks) {
            for (Block block : row) {
                if(block != null) {
                    block.render();
                }
            }
        }
        int error = GL11.glGetError();
        if (error != GL11.GL_NO_ERROR) {
            System.out.println("OpenGL Error: " + error);
        }
    }

    public Block[][] getBlocks() {
        return blocks;
    }
    public boolean allBlocksDestroyed() {
        for (Block[] row : blocks) {
            for (Block block : row) {
                if(block != null && block.isActive()) {
                    return false;
                }
            }
        }
        return true;
    }
}
