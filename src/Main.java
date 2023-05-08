import Model.Ball;
import Model.Block;
import Model.Game;
import Model.Paddle;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import util.Helpers;

import static util.Helpers.*;

public class Main {


    // Store the window handle
    private long window;

    private Game game;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        System.out.println("LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the key callback and destroy the window
        GLFW.glfwSetKeyCallback(window, null).free();
        GLFW.glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }


    private void init() {
        // Setup an error callback to print GLFW errors to System.err
        GLFWErrorCallback.createPrint(System.err).set();


        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // The window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // The window will be resizable

        // Create the window
        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Breakout Game", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        // Center the window on the screen
        GLFW.glfwSetWindowSizeCallback(window, (window, width, height) -> {
            GLFW.glfwSetWindowPos(window, (WIDTH - width) / 2, (HEIGHT - height) / 2);
        });

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);
        // Enable v-sync
        GLFW.glfwSwapInterval(1);

        // Make the window visible
        GLFW.glfwShowWindow(window);

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        Helpers.init();
        setupOrthoProjection();

        //=* Initialize game here *=//

        game = new Game(window, WIDTH, HEIGHT);

    }

    private void loop() {
        // Set the clear color
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close the window or has pressed the ESCAPE key
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear the framebuffer

            float delta = Helpers.getTimeDelta();

            // Update and render the game objects
            game.update(delta);
            game.render();

            GLFW.glfwSwapBuffers(window); // Swap the color buffers
            GLFW.glfwPollEvents(); // Poll for window events (e.g., key input and window close events)
        }
    }
    private void setupOrthoProjection() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, WIDTH, HEIGHT, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

}
