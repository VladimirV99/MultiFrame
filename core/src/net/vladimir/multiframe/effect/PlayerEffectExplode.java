package net.vladimir.multiframe.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import net.vladimir.multiframe.entity.EntityPlayer;

import java.util.Iterator;

public class PlayerEffectExplode extends PlayerEffect {

    private Pool<Particle> particlePool;
    private Array<Particle> particles;
    private final int particleCount;
    private final int particleSize;
    private final float particleLifespan;

    public PlayerEffectExplode(EntityPlayer player, final int particleCount, final int particleSize, final float particleLifespan) {
        super(player);
        this.particles = new Array<Particle>();
        this.particleCount = particleCount;
        this.particleSize = particleSize;
        this.particleLifespan = particleLifespan;

        final TextureRegion playerTexture = player.getTexture();
        this.particlePool = new Pool<Particle>() {
            @Override
            protected Particle newObject() {
                return new Particle(playerTexture, particleSize, particleLifespan);
            }
        };
    }

    private void initParticles(int count, int size, float lifespan) {
        for(int i = 0; i < count; i++) {
            Particle p = particlePool.obtain();
            p.setPosition(MathUtils.random(player.getX(), player.getX()+player.getWidth()-size), MathUtils.random(player.getY(), player.getY()+player.getHeight()-size));
            p.setVelocity(getParticleVelocity());
            particles.add(p);
        }
    }

    protected Vector2 getParticleVelocity() {
        return new Vector2(MathUtils.random(-80, 80), MathUtils.random(-80, 80));
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        Color currentColor = batch.getColor().cpy();

        Iterator<Particle> it = particles.iterator();
        Particle particle;
        while(it.hasNext()) {
            particle = it.next();
            if(particle.isDead()) {
                particlePool.free(particle);
                it.remove();
            } else {
                particle.update(delta);
                particle.render(batch, delta, offsetX, offsetY, currentColor);
            }
        }

        batch.setColor(currentColor);
    }

    @Override
    public void reset() {
        particles.clear();
        particlePool.freeAll(particles);
        initParticles(particleCount, particleSize, particleLifespan);
    }

    private final class Particle implements Pool.Poolable {

        private TextureRegion textureRegion;
        private Vector2 position;
        private Vector2 velocity;
        private int size;
        private float lifespan;

        private float lifetime;

        public Particle(TextureRegion textureRegion, Vector2 position, Vector2 velocity, int size, float lifespan) {
            this.textureRegion = textureRegion;
            this.position = position;
            this.velocity = velocity;
            this.size = size;
            this.lifespan = lifespan;

            this.lifetime = 0;
        }

        public Particle(TextureRegion textureRegion, int size, float lifespan) {
            this(textureRegion, Vector2.Zero.cpy(), Vector2.Zero.cpy(), size, lifespan);
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
            batch.draw(textureRegion, offsetX + position.x, offsetY + position.y, size, size);
        }

        public void setPosition(int x, int y) {
            position.set(x, y);
        }

        public void setVelocity(Vector2 velocity) {
            this.velocity = velocity;
        }

        @Override
        public void reset() {
            this.lifetime = 0;
        }

    }

}
