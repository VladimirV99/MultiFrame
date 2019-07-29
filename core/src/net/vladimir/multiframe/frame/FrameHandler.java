package net.vladimir.multiframe.frame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;

import net.vladimir.multiframe.entity.EntityObstacle;
import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.event.EventType;
import net.vladimir.multiframe.references.Settings;

public class FrameHandler implements IFrameHandler {

    private FrameOrchestrator orchestrator;

    private int dir = 0;
    private int obstacleFrame = 0;
    private int spawnedObstacles = 0;
    private EntityObstacle lastObstacle = null;

    public void init (FrameOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
        obstacleFrame = 0;
        spawnedObstacles = 0;
        spawnObstacle();
    }

    public void update() {
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            dir--;
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            dir++;

        if(dir > 1)
            dir = 1;
        else if(dir < -1)
            dir = -1;

        orchestrator.raiseEvent(new Event(EventType.MOVE_PLAYER, dir));

        dir = 0;

        if(lastObstacle==null || lastObstacle.getY()>Settings.OBSTACLE_DISTANCE/2)
            spawnObstacle();
    }

    public void handle(Event event) {
        switch (event.getType()) {
            case TOUCH:
                if(event.getData() == 0)
                    dir--;
                else if(event.getData() == 1)
                    dir++;
                break;
            case OBSTACLE_PASS:
                orchestrator.getGameListener().incrementScore();
                if(Settings.PLAYER_SWITCH==-1){
                    if(MathUtils.random(0, 2)==1)
                        orchestrator.raiseEvent(new Event(EventType.SWITCH_CONTROLS, 0));
                }else{
                    if(Settings.PLAYER_SWITCH!=0) {
                        if (spawnedObstacles % Settings.PLAYER_SWITCH == 0)
                            orchestrator.raiseEvent(new Event(EventType.SWITCH_CONTROLS, 0));
                    }
                }
                break;
        }
    }

    private void spawnObstacle() {
        int y = -Settings.OBSTACLE_HEIGHT - 100;
        if (lastObstacle != null) {
            y = lastObstacle.getY() - Settings.OBSTACLE_DISTANCE - Settings.OBSTACLE_HEIGHT;
        }

        EntityObstacle o = orchestrator.getObstaclePool().obtain();
        int nextFrame = getNextObstacleFrame();
        o.init(orchestrator.getFrame(nextFrame), y);
        orchestrator.addObstacle(nextFrame, o);
        spawnedObstacles++;
        lastObstacle = o;
    }

    private int getNextObstacleFrame(){
        if(Settings.OBSTACLE_SWITCH==-1) {
            int rand = MathUtils.random(0, 2);
            if(rand==1)
                obstacleFrame  = (obstacleFrame+1)%2;
        } else {
            if(Settings.OBSTACLE_SWITCH!=0 && (spawnedObstacles)%Settings.OBSTACLE_SWITCH==0)
                obstacleFrame  = (obstacleFrame+1)%2;
        }
        return obstacleFrame;
    }

}