package Model;
import org.lwjgl.glfw.GLFW;
import util.Helpers;
import util.Texture;
import Enum.GameState;
import Enum.SpriteType;
public class Credits {

    private Game game;
    private int width;
    private int height;

    private long window;

    public Credits(Game game, int width, int height, long window){
        this.game = game;
        this.width = width;
        this.height = height;
        this.window = window;
    }

    public void update() {
        // If any key is pressed, switch back to the menu
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS ||
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS ||
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
            game.setGameState(GameState.MENU);

        }
    }

    public void render() {
        // Render the credits texture
        Helpers.renderSprite(SpriteType.CREDITS, 0, 0,1);
    }
}
