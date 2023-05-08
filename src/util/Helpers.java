package util;

import org.lwjgl.glfw.GLFW;

public class Helpers {

    private static double lastFrameTime;
    private static double timeDelta;

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    public static void init() {
        lastFrameTime = getTime();
    }

    public static float getTimeDelta() {
        double currentTime = getTime();
        timeDelta = currentTime - lastFrameTime;
        lastFrameTime = currentTime;
        return (float) timeDelta;
    }

    private static double getTime() {
        return GLFW.glfwGetTime();
    }
}
