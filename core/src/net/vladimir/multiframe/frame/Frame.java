package net.vladimir.multiframe.frame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.vladimir.multiframe.entity.EntityObstacle;
import net.vladimir.multiframe.entity.EntityPlayer;
import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.event.EventType;
import net.vladimir.multiframe.utils.Padding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class Frame implements IFrame{

    private FrameOrchestrator orchestrator;

    private int id;
    private int x;
    private int y;
    private int width;
    private int height;

    protected Padding padding;

    private boolean inFocus;

    private List<EntityPlayer> players;
    private List<EntityObstacle> obstacles;

    public Frame(int id, int x, int y, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.padding = new Padding();

        this.inFocus = false;

        this.players = new ArrayList<EntityPlayer>();
        this.obstacles = new LinkedList<EntityObstacle>();
    }

    @Override
    public void init(FrameOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @Override
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

        for(EntityObstacle obstacle : obstacles) {
            for (EntityPlayer player : players) {
                if (obstacle.checkCollision(player)) {
                    getOrchestrator().getHandler().handle(new Event(EventType.GAME_OVER));
                    player.onEvent(new Event(EventType.DESTROY_PLAYER));
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        for(EntityPlayer player : players) {
            player.render(batch, delta, x, y);
        }
        for(EntityObstacle obstacle : obstacles) {
            obstacle.render(batch, delta, x, y);
        }
    }

    @Override
    public void reset() {
        for(EntityPlayer player : players) {
            player.reset();
        }
        removeObstacles();
        setFocus(false);
    }

    @Override
    public void setFocus(boolean flag) {
        this.inFocus = flag;
    }

    @Override
    public boolean isInFocus() {
        return inFocus;
    }

    @Override
    public void addPlayer(EntityPlayer player) {
        this.players.add(player);
    }

    @Override
    public void addObstacle(EntityObstacle obstacle) {
        this.obstacles.add(obstacle);
    }

    @Override
    public void removeObstacle(EntityObstacle obstacle) {
        this.getOrchestrator().getObstaclePool().free(obstacle);
    }

    @Override
    public void removeObstacles() {
        for(EntityObstacle obstacle : obstacles) {
            this.getOrchestrator().getObstaclePool().free(obstacle);
        }
        obstacles.clear();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        this.padding.set(left, top, right, bottom);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public FrameOrchestrator getOrchestrator() {
        return orchestrator;
    }

    public List<EntityPlayer> getPlayers() {
        return players;
    }

    public List<EntityObstacle> getObstacles() {
        return obstacles;
    }

}
