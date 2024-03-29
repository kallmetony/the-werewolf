package com.aaronr92.game;

import com.aaronr92.engine.IGameLogic;
import com.aaronr92.engine.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class WerewolfGameLogic implements IGameLogic {

    private int direction = 0;

    private float color = 0.0f;

    private final Renderer renderer;

    public WerewolfGameLogic() {
        this.renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
        renderer.init();
    }

    @Override
    public void input(Window window) {
        if ( window.isKeyPressed(GLFW.GLFW_KEY_UP) ) {
            direction = 1;
        } else if ( window.isKeyPressed(GLFW.GLFW_KEY_DOWN) ) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void update(float interval) {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if ( color < 0 ) {
            color = 0.0f;
        }
    }

    @Override
    public void render(Window window) {
        window.setClearColor(color, color, color, 0.0f);
        renderer.render(window);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
    }
}
