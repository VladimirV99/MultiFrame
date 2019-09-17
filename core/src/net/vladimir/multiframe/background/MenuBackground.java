package net.vladimir.multiframe.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.vladimir.multiframe.references.References;

public class MenuBackground implements IBackground {

    private TextureRegion texture;

    private float lightness;
    private final float maxLightness;
    private final float minLightness;
    private float lightnessStep;

    private final float aspectRatio;
    private int dx;
    private int dy;
    private int width;
    private int height;

    public MenuBackground(TextureRegion texture, float lightness, float minLightness, float maxLightness, float lightnessStep) {
        this.texture = texture;
        this.lightness = lightness;
        this.minLightness = minLightness;
        this.maxLightness = maxLightness;
        this.lightnessStep = lightnessStep;

        this.aspectRatio = (float)References.MENU_WIDTH/(float)References.MENU_HEIGHT;
        this.dx = 0;
        this.dy = 0;
        this.width = References.MENU_WIDTH;
        this.height = References.MENU_HEIGHT;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        Color currentColor = batch.getColor();
        lightness += lightnessStep * delta;
        if(lightness >= maxLightness || lightness <= minLightness)
            lightnessStep = -lightnessStep;
        batch.setColor(lightness, lightness ,1f, 1f);
        batch.draw(texture, dx, dy, width, height);
        batch.setColor(currentColor);
    }

    @Override
    public void resize(int width, int height) {
        if(width == References.MENU_WIDTH) {
            this.width = (int)(aspectRatio * height);
            this.height = height;
            this.dx = -(References.MENU_WIDTH - width)/2;
            this.dy = 0;
        } else if(height == References.MENU_HEIGHT) {
            this.width = width;
            this.height = (int)(width/aspectRatio);
            this.dx = 0;
            this.dy = -(References.MENU_HEIGHT - height)/2;
        }
    }

}
