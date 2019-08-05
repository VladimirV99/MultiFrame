package net.vladimir.multiframe.modes.dualframe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.assets.RegionNames;
import net.vladimir.multiframe.entity.EntityObstacle;
import net.vladimir.multiframe.modes.dualframe.entity.EntityDualFrameObstacle;
import net.vladimir.multiframe.modes.dualframe.entity.EntityDualFramePlayer;
import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.event.EventType;
import net.vladimir.multiframe.frame.FrameOrchestrator;
import net.vladimir.multiframe.frame.IFrameHandler;
import net.vladimir.multiframe.references.References;

public class DualFrameHandler implements IFrameHandler {

    private String id;

    private DualFrameData data;

    private FrameOrchestrator orchestrator;

    private TextureAtlas gameplayAtlas;

    private int dir = 0;
    private int obstacleFrame = 0;
    private int spawnedObstacles = 0;
    private EntityDualFrameObstacle lastObstacle = null;

    public DualFrameHandler(String id, DualFrameData data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public void init (FrameOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
        this.gameplayAtlas = orchestrator.getAssetManager().get(AssetDescriptors.GAMEPLAY_ATLAS);

        orchestrator.addFrame(new DualFrame(0, -References.SCREEN_WIDTH/2, -References.SCREEN_HEIGHT/2, References.SCREEN_WIDTH/2, References.SCREEN_HEIGHT, gameplayAtlas.findRegion(RegionNames.SELECTOR), gameplayAtlas.findRegion(RegionNames.WALL), data));
        orchestrator.addFrame(new DualFrame(1, 0, -References.SCREEN_HEIGHT/2, References.SCREEN_WIDTH/2, References.SCREEN_HEIGHT, gameplayAtlas.findRegion(RegionNames.SELECTOR), gameplayAtlas.findRegion(RegionNames.WALL), data));

        EntityDualFramePlayer playerLeft = new EntityDualFramePlayer(gameplayAtlas.findRegion(RegionNames.PLAYER), References.SCREEN_WIDTH/4-data.playerSize/2, data.playerY, data.playerSize, data.playerSize, data.playerSpeed, 1, data.wallWidth,References.SCREEN_WIDTH/2-data.playerSize-data.wallWidth);
        EntityDualFramePlayer playerRight = new EntityDualFramePlayer(gameplayAtlas.findRegion(RegionNames.PLAYER), References.SCREEN_WIDTH/4-data.playerSize/2, data.playerY, data.playerSize, data.playerSize, data.playerSpeed,-1, data.wallWidth, References.SCREEN_WIDTH/2-data.playerSize-data.wallWidth);

        orchestrator.getFrame(0).addPlayer(playerLeft);
        orchestrator.getFrame(1).addPlayer(playerRight);
    }

    @Override
    public Pool<EntityObstacle> createObstaclePool() {
        return new Pool<EntityObstacle>() {
            @Override
            protected EntityObstacle newObject() {
                return new EntityDualFrameObstacle(gameplayAtlas.findRegion(RegionNames.OBSTACLE),
                        data.obstacleSpeed, data.obstacleGap, data.obstacleHeight, data.wallWidth, data.playerY);
            }
        };
    }

    @Override
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

        if(lastObstacle==null || lastObstacle.getY()>data.obstacleDistance/2)
            spawnObstacle();
    }

    @Override
    public void reset() {
        dir = 0;
        obstacleFrame = 0;
        spawnedObstacles = 0;
        lastObstacle = null;
    }

    @Override
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
                if(data.playerSwitch==-1){
                    int rand = MathUtils.random(0, 2);
                    if(rand==1)
                        orchestrator.raiseEvent(new Event(EventType.SWITCH_CONTROLS, 0));
                }else{
                    if(data.playerSwitch!=0) {
                        if (spawnedObstacles % data.playerSwitch == 0)
                            orchestrator.raiseEvent(new Event(EventType.SWITCH_CONTROLS, 0));
                    }
                }
                break;
            case GAME_OVER:
                orchestrator.pause();
                orchestrator.getGameListener().gameOver();
                break;
        }
    }

    private void spawnObstacle() {
        int y = -data.obstacleHeight - 100;
        if (lastObstacle != null)
            y = lastObstacle.getY() - data.obstacleDistance - data.obstacleHeight;

        EntityDualFrameObstacle o = (EntityDualFrameObstacle)orchestrator.getObstaclePool().obtain();
        int nextFrame = getNextObstacleFrame();
        o.init(orchestrator.getFrame(nextFrame), y);
        orchestrator.addObstacle(nextFrame, o);
        spawnedObstacles++;
        lastObstacle = o;
    }

    private int getNextObstacleFrame(){
        if(data.obstacleSwitch==-1) {
            int rand = MathUtils.random(0, 2);
            if(rand==1)
                obstacleFrame  = (obstacleFrame+1)%2;
        } else {
            if(data.obstacleSwitch!=0 && (spawnedObstacles+1)%data.obstacleSwitch==0)
                obstacleFrame  = (obstacleFrame+1)%2;
        }
        return obstacleFrame;
    }

    public String getId() {
        return id;
    }
}
