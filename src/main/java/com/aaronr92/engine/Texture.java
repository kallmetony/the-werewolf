package com.aaronr92.engine;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    private int id;

    private int width;

    private int height;

    private BufferedImage bufferedImage;

    public Texture(String filename) {
        init(filename);
    }

    private void init(String filename) {
        try {
            bufferedImage = ImageIO.read(new File(filename));

            this.width = bufferedImage.getWidth();
            this.height = bufferedImage.getHeight();

            // getting pixels
            int[] pixelsRaw = new int[width * height * 4];
            pixelsRaw = bufferedImage.getRGB(0, 0, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = pixelsRaw[i * width + j];

                    pixels.put((byte) ((pixel >> 16) & 0xFF));  // RED
                    pixels.put((byte) ((pixel >> 8) & 0xFF));   // GREEN
                    pixels.put((byte) (pixel& 0xFF));           // BLUE
                    pixels.put((byte) ((pixel >> 24) & 0xFF));  // ALPHA
                }
            }

            pixels.flip();

            id = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height,
                    0, GL_RGBA, GL_BYTE, pixels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
}