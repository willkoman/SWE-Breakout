package Model;

import Interface.ILevelLayout;

public class Level {
    private Block[][] blocks;
    private Paddle paddle;
    private Ball ball;

    public Level(long window, int width, int height, ILevelLayout levelLayout) {
        paddle = new Paddle(window, width / 2 - 50, height - 30, 100, 10, 500);
        ball = new Ball(width / 2, height / 2, 10, 300, 300);
        blocks = levelLayout.createLayout(); // Use the level layout to create the blocks
    }

    public void update(float delta) {
        paddle.update(delta);
        ball.update(delta, paddle, blocks);
    }

    public void render() {
        paddle.render();
        ball.render();
        for (Block[] row : blocks) {
            for (Block block : row) {
                if(block != null)
                    block.render();
            }
        }
    }
}
