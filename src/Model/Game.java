package Model;

public class Game {
    private Level level;

    public Game(long window, int width, int height) {
        level = new Level(window, width, height);
    }

    public void update(float delta) {
        level.update(delta);
    }

    public void render() {
        level.render();
    }
}
