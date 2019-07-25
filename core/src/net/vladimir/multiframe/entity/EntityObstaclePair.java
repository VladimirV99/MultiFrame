package net.vladimir.multiframe.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.screen.GameScreen;

public class EntityObstaclePair {

    private boolean passed = false;

    private int x; //-640 or 0
    private EntityObstacle left;
    private EntityObstacle right;

    private GameScreen gameScreen;

    public EntityObstaclePair(AssetManager assetManager, int count, int delay, GameScreen screen){
        this.gameScreen = screen;
        left = new EntityObstacle(assetManager, new Vector2(0,0));
        right = new EntityObstacle(assetManager, new Vector2(0,0));
        setPos(count, delay);
    }

    private void setPos(int count, int delay){
        this.x = gameScreen.nextX(count);

        float mid = MathUtils.random(Settings.OBSTACLE_GAP/2, Settings.SCREEN_WIDTH/2-Settings.WALL_WIDTH*2-Settings.OBSTACLE_GAP/2);

        left.setPos(new Vector2(Settings.WALL_WIDTH+x, Settings.SCREEN_HEIGHT/2-Settings.OBSTACLE_COUNT*Settings.OBSTACLE_DISTANCE-(Settings.OBSTACLE_COUNT)*Settings.OBSTACLE_HEIGHT-(Settings.OBSTACLE_DISTANCE+Settings.OBSTACLE_HEIGHT)*delay));
        left.setDest(new Vector2(mid-Settings.OBSTACLE_GAP/2, Settings.OBSTACLE_HEIGHT));

        right.setPos(new Vector2(Settings.WALL_WIDTH+mid+Settings.OBSTACLE_GAP/2+x, Settings.SCREEN_HEIGHT/2-Settings.OBSTACLE_COUNT*Settings.OBSTACLE_DISTANCE-(Settings.OBSTACLE_COUNT)*Settings.OBSTACLE_HEIGHT-(Settings.OBSTACLE_DISTANCE+Settings.OBSTACLE_HEIGHT)*delay));
        right.setDest(new Vector2(Settings.SCREEN_WIDTH/2-Settings.WALL_WIDTH-mid-Settings.OBSTACLE_GAP/2, Settings.OBSTACLE_HEIGHT));

        passed = false;
    }

    public void update(){
        left.update();
        right.update();

        if(right.getPosition().y>= Settings.SCREEN_HEIGHT/2){
            setPos(0, 0);
        }

        if(right.getPosition().y>Settings.PLAYER_Y-5 && !passed){
            passed = true;
            gameScreen.onPass();
        }
    }

    public void render(SpriteBatch sb){
        left.render(sb);
        right.render(sb);
    }

    public boolean intersects(Rectangle rect){
        return rect.overlaps(left.getBounds()) || rect.overlaps(right.getBounds());
    }

}