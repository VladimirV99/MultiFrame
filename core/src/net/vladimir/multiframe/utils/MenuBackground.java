package net.vladimir.multiframe.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.vladimir.multiframe.references.References;

public class MenuBackground {

    private TextureRegion texture;

    private float lightness;
    private final float maxLightness;
    private final float minLightness;
    private float lightnessStep;

    public MenuBackground(TextureRegion texture, float lightness, float minLightness, float maxLightness, float lightnessStep) {
        this.texture = texture;
        this.lightness = lightness;
        this.minLightness = minLightness;
        this.maxLightness = maxLightness;
        this.lightnessStep = lightnessStep;
    }

    public void render(SpriteBatch batch, float delta) {
        Color currentColor = batch.getColor();
        lightness += lightnessStep * delta;
        if(lightness >= maxLightness || lightness <= minLightness)
            lightnessStep = -lightnessStep;
        batch.setColor(lightness, lightness ,1f, 1f);
        batch.draw(texture, 0, 0, References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
        batch.setColor(currentColor);
    }

}
