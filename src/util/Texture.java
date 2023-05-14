package util;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Texture {
    private final int id;
    private final int width;
    private final int height;

    public Texture(String resourceName) {
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer channelsBuffer = BufferUtils.createIntBuffer(1);

        ByteBuffer imageInfoBuffer, imageLoadBuffer;
        try {
            InputStream stream = Texture.class.getResourceAsStream(resourceName);
            if (stream == null) {
                throw new IOException("Resource not found: " + resourceName);
            }

            byte[] byteArray = stream.readAllBytes();
            imageInfoBuffer = ByteBuffer.allocateDirect(byteArray.length);
            imageLoadBuffer = ByteBuffer.allocateDirect(byteArray.length);

            // Transfer byteArray to the buffers
            imageInfoBuffer.put(byteArray).flip();
            imageLoadBuffer.put(byteArray).flip();

        } catch (IOException e) {
            throw new RuntimeException("Failed to load a texture file: " + resourceName, e);
        }

        if (!STBImage.stbi_info_from_memory(imageInfoBuffer, widthBuffer, heightBuffer, channelsBuffer)) {
            throw new RuntimeException("Failed to read image information: " + STBImage.stbi_failure_reason());
        }

        // Get width and height of image
        width = widthBuffer.get();
        height = heightBuffer.get();

        // Create new IntBuffers for the stbi_load_from_memory function
        IntBuffer widthBufferForLoad = BufferUtils.createIntBuffer(1);
        IntBuffer heightBufferForLoad = BufferUtils.createIntBuffer(1);
        IntBuffer channelsBufferForLoad = BufferUtils.createIntBuffer(1);

        // Load image
        ByteBuffer image = STBImage.stbi_load_from_memory(imageLoadBuffer, widthBufferForLoad, heightBufferForLoad, channelsBufferForLoad, 0);
        if (image == null) {
            throw new RuntimeException("Failed to load image: " + STBImage.stbi_failure_reason());
        }

        // Generate texture
        id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

        // Set texture parameters
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        // Upload texture to GPU
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

        // Generate Mip Map
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        STBImage.stbi_image_free(image);
    }




    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
