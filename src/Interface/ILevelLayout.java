package Interface;

import Model.Block;
import Model.Game;

public interface ILevelLayout {
    Block[][] createLayout(Game game);
}
