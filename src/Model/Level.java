package Model;

public class Level {
    private Block[][] blocks;
    private Paddle paddle;
    private Ball ball;
    private static final int BLOCK_ROWS = 5;
    private static final int BLOCK_COLUMNS = 11;
    private static final int BLOCK_WIDTH = 60;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BLOCK_PADDING = 10;

    public Level(long window, int width, int height) {
        paddle = new Paddle(window, width / 2 - 50, height - 30, 100, 10, 500);
        ball = new Ball(window, width / 2, height / 2, 10, 300, -300);
        initBlocks();
    }

    private void initBlocks() {
        blocks = new Block[BLOCK_ROWS][BLOCK_COLUMNS];

        for (int row = 0; row < BLOCK_ROWS; row++) {
            for (int col = 0; col < BLOCK_COLUMNS; col++) {
                float x = col * (BLOCK_WIDTH + BLOCK_PADDING) + BLOCK_PADDING;
                float y = row * (BLOCK_HEIGHT + BLOCK_PADDING) + 100;
                blocks[row][col] = new Block(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
            }
        }
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
                block.render();
            }
        }
    }
}
