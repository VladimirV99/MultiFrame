package net.vladimir.multiframe.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureUtils {

    private static Pixmap pixmap;

    public static TextureRegion makeTextureRegion(int width, int height, Color col){
        return new TextureRegion(makeTexture(width,height,col));
    }

    public static Texture makeTexture(int width, int height, Color col){
        Texture texture = new Texture(makePixMap(width,height,col));
        disposePixmap();
        return texture;
    }

    private static Pixmap makePixMap(int width, int height, Color fill){
        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(fill);
        pixmap.fill();
        return pixmap;
    }

    private static void disposePixmap(){
        pixmap.dispose();
    }

}
