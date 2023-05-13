package LevelLayouts;

import Interface.ILevelLayout;
import Model.Block;
import Model.Enemy;
import Model.Game;

public class Level3 implements ILevelLayout {
    private static final int BLOCK_WIDTH = 60;

    private static final int BLOCK_HEIGHT = 20;

    private static final int BLOCK_PADDING = 10;

    @Override
    public Block[][] createLayout(Game game) {

        Block[][] blocks = new Block[5][11];

        /* outline of the layout
         0  1  2  3  4  5  6  7  8  9  10
     0  [X][ ][X][ ][X][ ][X][ ][X][ ][X] 0
     1  [ ][X][ ][X][ ][X][ ][X][ ][X][ ] 1
     2  [ ][ ][ ][X][ ][ ][ ][X][ ][ ][ ] 2
     3  [ ][ ][ ][X][ ][ ][ ][X][ ][ ][ ] 3
     4  [ ][ ][ ][X][ ][ ][ ][X][ ][ ][ ] 4
         */

        blocks[0][0] = new Enemy(BLOCK_PADDING, 60, BLOCK_WIDTH, BLOCK_HEIGHT,game);
        blocks[0][2] = new Block((2*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60, BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[0][4] = new Block((4*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60, BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[0][8] = new Block((8*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60, BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[0][10] = new Enemy((10*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60, BLOCK_WIDTH, BLOCK_HEIGHT, game);

        blocks[1][1] = new Block(((BLOCK_WIDTH + BLOCK_PADDING))+BLOCK_PADDING, 60+(30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[1][3] = new Enemy((3*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(30), BLOCK_WIDTH, BLOCK_HEIGHT,game);
        blocks[1][5] = new Block((5*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[1][7] = new Enemy((7*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(30), BLOCK_WIDTH, BLOCK_HEIGHT, game);
        blocks[1][9] = new Block((9*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(30), BLOCK_WIDTH, BLOCK_HEIGHT);

        blocks[2][3] = new Block((3*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(2*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[2][7] = new Block((7*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(2*30), BLOCK_WIDTH, BLOCK_HEIGHT);

        blocks[3][3] = new Block((3*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(3*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[3][7] = new Block((7*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(3*30), BLOCK_WIDTH, BLOCK_HEIGHT);

        blocks[4][3] = new Block((3*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(4*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[4][5] = new Block((7*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(4*30), BLOCK_WIDTH, BLOCK_HEIGHT);

        return blocks;
    }
}