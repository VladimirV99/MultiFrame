package net.vladimir.multiframe.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import net.vladimir.multiframe.entity.EntityPlayer;

import java.util.Iterator;
import java.util.LinkedList;

public class PlayerEffectCrumble extends PlayerEffect {

    private LinkedList<Particle> particles;
    private int particleSize;
    private float particleLifespan;

    public PlayerEffectCrumble(EntityPlayer player, int particleSize, float particleLifespan) {
        super(player);
        this.particles = new LinkedList<Particle>();
        this.particleSize = particleSize;
        this.particleLifespan = particleLifespan;
    }

    @Override
    public void update(float delta) {
        if(MathUtils.random(0, 5) < 4) {
            particles.add(
                    new Particle(
                            player.getTexture(),
                            new Vector2(MathUtils.random(player.getX(), player.getX()+player.getWidth()-particleSize), player.getY() + player.getHeight() - particleSize),
                            getParticleVelocity(),
                            particleLifespan
                    )
            );
        }
    }

    protected Vector2 getParticleVelocity() {
        return new Vector2(MathUtils.random(-120, 120), 150);
    }

    @Override
    public void render(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        Color currentColor = batch.getColor().cpy();

        Iterator<Particle> it = particles.iterator();
        Particle particle;
        while(it.hasNext()) {
            particle = it.next();
            if(particle.isDead())
                it.remove();
            else
                particle.update(delta);
            particle.render(batch, delta, offsetX, offsetY, currentColor);
        }

        batch.setColor(currentColor);
    }

    @Override
    public void reset() {
        particles.clear();
    }

    private final class Particle {

        private TextureRegion textureRegion;
        private Vector2 position;
        private Vector2 velocity;
        private float lifespan;

        private float lifetime;

        public Particle(TextureRegion textureRegion, Vector2 position, Vector2 velocity, float lifespan) {
            this.position = position;
            this.velocity = velocity;
            this.lifespan = lifespan;
            this.textureRegion = textureRegion;

            this.lifetime = 0;
        }

        public boolean isDead() {
            return lifetime >= lifespan;
        }

        public void update(float delta) {
            lifetime += delta;
            position.add(velocity.x * delta, velocity.y * delta);
        }

        public void render(SpriteBatch batch, float delta, int offsetX, int offsetY, Color color) {
            batch.setColor(color.r, color.g, color.b, 1f - MathUtils.map(0, lifespan, 0, 0.7f, lifetime));
            batch.draw(textureRegion, offsetX + position.x, offsetY + position.y, particleSize, particleSize);
        }

    }

}
