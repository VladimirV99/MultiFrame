package net.vladimir.multiframe.frame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.entity.EntityObstacle;
import net.vladimir.multiframe.entity.EntityPlayer;
import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.event.EventType;
import net.vladimir.multiframe.references.Settings;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Frame implements IFrame{

    private FrameOrchestrator orchestrator;

    private int id;
    private int x;
    private int y;
    private int width;
    private int height;

    private boolean inFocus;

    private List<EntityPlayer> players;
    private List<EntityObstacle> obstacles;

    private Texture selectorTexture;
    private Texture wallTexture;

    public Frame(int id, int x, int y, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.inFocus = false;

        this.players = new ArrayList<EntityPlayer>();
        this.obstacles = new LinkedList<EntityObstacle>();
    }

    public void init(FrameOrchestrator orchestrator) {
        this.orchestrator = orchestrator;

        this.selectorTexture = orchestrator.getAssetManager().get(AssetDescriptors.SELECTOR);
        this.wallTexture = orchestrator.getAssetManager().get(AssetDescriptors.WALL);
    }

    public void update(float delta) {
        for(EntityPlayer player : players) {
            player.update(delta);
        }
        for(EntityObstacle obstacle : obstacles) {
            obstacle.update(delta);
        }

        ListIterator<EntityObstacle> it = obstacles.listIterator();
        EntityObstacle o;
        while(it.hasNext()){
            o = it.next();
            if(o.isDead()) {
                removeObstacle(o);
                it.remove();
            }
        }

        for(EntityObstacle obstacle : obstacles)
            for(EntityPlayer player : players)
                if(obstacle.intersects(player.getBounds()))
                    getOrchestrator().getHandler().handle(new Event(EventType.GAME_OVER, 0));
    }

    public void render(SpriteBatch batch, float delta) {
        batch.draw(wallTexture, x, y, Settings.WALL_WIDTH, height);
        batch.draw(wallTexture, x+width-Settings.WALL_WIDTH, y, Settings.WALL_WIDTH, height);

        for(EntityPlayer player : players) {
            player.render(batch, delta, x, y);
        }
        for(EntityObstacle obstacle : obstacles) {
            obstacle.render(batch, delta, x, y);
        }

        if(isInFocus()) {
            batch.draw(selectorTexture, x, y+height-20, width, 20);
        }
    }

    public void reset() {
        for(EntityPlayer player : players) {
            player.reset();
        }
        removeObstacles();
        setFocus(false);
    }

    public void setFocus(boolean flag) {
        this.inFocus = flag;
    }

    public boolean isInFocus() {
        return inFocus;
    }

    public void addPlayer(EntityPlayer player) {
        this.players.add(player);
    }

    public void addObstacle(EntityObstacle obstacle) {
        this.obstacles.add(obstacle);
    }

    public void removeObstacle(EntityObstacle obstacle) {
        this.getOrchestrator().getObstaclePool().free(obstacle);
    }

    public void removeObstacles() {
        for(EntityObstacle obstacle : obstacles) {
            this.getOrchestrator().getObstaclePool().free(obstacle);
        }
        obstacles.clear();
    }

    public void onEvent(Event event) {
        switch (event.getType()) {
            case MOVE_PLAYER:
                for(EntityPlayer player : players) {
                    player.setDirection(event.getData());
                }
                break;
            case SWITCH_CONTROLS:
                setFocus(!isInFocus());
                for (EntityPlayer player : players) {
                    player.switchDirection();
                }
                break;
        }
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public FrameOrchestrator getOrchestrator() {
        return orchestrator;
    }

}
