package net.vladimir.multiframe.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class RenderUtils {

    public static void clearScreen() {
        clearScreen(0f, 0f, 0f, 1f);
    }

    public static void clearScreen(Color color) {
        clearScreen(color.r, color.g, color.b, color.a);
    }

    public static void clearScreen(float r, float g, float b, float a) {
        Gdx.gl.glClearColor(r, g, b, a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
