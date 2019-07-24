package net.vladimir.multiframe.references;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {

    public static Texture PLAYER = new Texture(Gdx.files.internal("player.png"));
    public static Texture WALL = new Texture(Gdx.files.internal("wall.png"));
    public static Texture SELECTOR = new Texture(Gdx.files.internal("selector.png"));
    public static Texture OBSTACLE = new Texture(Gdx.files.internal("obstacle.png"));

}