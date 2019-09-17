package net.vladimir.multiframe.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IBackground {

    void update(float delta);

    void render(SpriteBatch batch, float delta);

    void resize(int width, int height);

}
