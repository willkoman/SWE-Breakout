package LevelLayouts;

import Interface.ILevelLayout;
import Model.Block;

public class Level1Layout implements ILevelLayout {
    private static final int BLOCK_WIDTH = 60;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BLOCK_PADDING = 10;

    @Override
    public Block[][] createLayout() {
        Block[][] blocks = new Block[5][11];

        /* outline of the layout
         0  1  2  3  4  5  6  7  8  9  10
     0  [ ][ ][ ][X][ ][ ][ ][X][ ][ ][ ] 0
     1  [X][ ][ ][X][ ][ ][ ][X][ ][ ][X] 1
     2  [ ][X][ ][ ][ ][X][ ][ ][ ][X][ ] 2
     3  [ ][ ][X][ ][ ][ ][ ][ ][X][ ][ ] 3
     4  [ ][ ][ ][X][X][X][X][X][ ][ ][ ] 4
         */

        blocks[0][3] = new Block((3*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(0*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[0][7] = new Block((7*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(0*30), BLOCK_WIDTH, BLOCK_HEIGHT);

        blocks[1][0] = new Block((0*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(1*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[1][3] = new Block((3*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(1*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[1][7] = new Block((7*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(1*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[1][10] = new Block((10*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(1*30), BLOCK_WIDTH, BLOCK_HEIGHT);

        blocks[2][1] = new Block((1*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(2*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[2][5] = new Block((5*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(2*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[2][9] = new Block((9*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(2*30), BLOCK_WIDTH, BLOCK_HEIGHT);

        blocks[3][2] = new Block((2*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(3*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[3][8] = new Block((8*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(3*30), BLOCK_WIDTH, BLOCK_HEIGHT);

        blocks[4][4] = new Block((4*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(4*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[4][5] = new Block((5*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(4*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[4][6] = new Block((6*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(4*30), BLOCK_WIDTH, BLOCK_HEIGHT);
        blocks[4][7] = new Block((7*(BLOCK_WIDTH+BLOCK_PADDING))+BLOCK_PADDING, 60+(4*30), BLOCK_WIDTH, BLOCK_HEIGHT);

        return blocks;
    }
}

