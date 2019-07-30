package net.vladimir.multiframe.frame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

import net.vladimir.multiframe.entity.EntityObstacle;
import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.event.EventType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FrameOrchestrator {

    private SpriteBatch batch;
    private Camera camera;
    private AssetManager assetManager;

    private Map<Integer, IFrame> frames;
    private Pool<EntityObstacle> obstaclePool;

    private IGameListener gameListener;
    private IFrameHandler frameHandler;

    private boolean running;

    public FrameOrchestrator(SpriteBatch batch, Camera camera, final AssetManager assetManager, IGameListener gameListener, IFrameHandler frameHandler) {
        this.batch = batch;
        this.camera = camera;
        this.assetManager = assetManager;

        this.frames = new HashMap<Integer, IFrame>();
        this.obstaclePool = new Pool<EntityObstacle>() {
            @Override
            protected EntityObstacle newObject() {
                return new EntityObstacle(assetManager);
            }
        };

        this.gameListener = gameListener;
        this.frameHandler = frameHandler;

        this.running = false;
    }

    public void init() {
        frameHandler.init(this);
    }

    public void start() {
        frameHandler.reset();
        running = true;
    }

    public void update(float delta) {
        if(!running)
            return;

        int i = 0;
        while(true){
            if(Gdx.input.isTouched(i)){
                Vector3 projected = camera.unproject(new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0));
                Vector2 touch = new Vector2(projected.x, projected.y);

                for(IFrame frame : frames.values()) {
                    if(touch.x-frame.getX()>=0 && touch.x-frame.getX()<=frame.getWidth() && touch.y-frame.getY()>=0 && touch.y-frame.getY()<=frame.getHeight()) {
                        frameHandler.handle(new Event(EventType.TOUCH, frame.getId()));
                    }
                }

                i++;
            }else{
                break;
            }
        }

        frameHandler.update();

        for(IFrame frame : frames.values()) {
            frame.update(delta);
        }
    }

    public void render(float delta) {
        for(IFrame frame : frames.values()) {
            frame.render(batch, delta);
        }
    }

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }

    public void reset() {
        frameHandler.reset();
        for(IFrame frame : frames.values()) {
            frame.reset();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void addFrame(IFrame frame) {
        frame.init(this);
        this.frames.put(frame.getId(), frame);
    }

    public IFrame getFrame(int key) {
        return this.frames.get(key);
    }

    public Collection<IFrame> getFrames() {
        return frames.values();
    }

    public void raiseEvent(Event event) {
        for(IFrame frame : frames.values()) {
            frame.onEvent(event);
        }
    }

    public void addObstacle(int frame, EntityObstacle obstacle) {
        this.getFrame(frame).addObstacle(obstacle);
    }

    public Pool<EntityObstacle> getObstaclePool() {
        return obstaclePool;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public IFrameHandler getHandler() {
        return frameHandler;
    }

    public IGameListener getGameListener() {
        return gameListener;
    }
}
