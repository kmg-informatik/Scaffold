package dev.elk.scaffold.renderer;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

/**
 * Wrapper for an OpenGL Texture. This takes in an image file, loads it into a bytebuffer
 * and then gives this bytebuffer to opengl to handle.
 *
 * Adapted for Java from: https://learnopengl.com/Getting-started/Textures
 *
 * @author Felix Kunze
 */
public class Texture {
    private int texID;
    private int width, height;

    public Texture(Path filepath) throws IOException {

        //Handles the binding of the texture
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        //Defines what happens outside the texture bounds. i.e. coordinates outside of the (0,0) to (1,1) range.
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T, GL_REPEAT);

        // Defines how pixel colors are defined. GL_NEAREST uses the color of the actual pixel,
        // GL_LINEAR interpolates adjacent pixels. https://learnopengl.com/img/getting-started/texture_filtering.png
        // We use GL_NEAREST because it makes it look blocky and this is a pixel art game.
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        //Use arrays to have specific places in memory to save data to.
        int[] width = new int[1];
        int[] height = new int[1];
        int[] channels = new int[1];

        stbi_set_flip_vertically_on_load(true);


        // Loads an image using stbi
        ByteBuffer image = stbi_load(filepath.toString(),width, height, channels, 0);

        if (image == null) throw new IOException();

        this.width = width[0];
        this.height = height[0];
        glTexImage2D(GL_TEXTURE_2D, 0,GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        stbi_image_free(image);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
