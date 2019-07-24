package net.vladimir.multiframe.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.references.Settings;

public class EntityPlayerRight extends Entity{

    private int width;
    private int height;
    private int mult = 1;
    private Camera camera;

    public EntityPlayerRight(AssetManager assetManager, Vector2 pos, int width, int height, Vector2 direction, Camera camera) {
        super(assetManager.get(AssetDescriptors.PLAYER), pos, direction);
        this.width = width;
        this.height = height;
        this.camera = camera;
    }

    @Override
    public void update() {
        pos.add(direction);

        int dir = 0;

        int i = 0;
        while(true){
            if(Gdx.input.isTouched(i)){
                Vector3 projected = camera.unproject(new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0));
                Vector2 touch = new Vector2(projected.x, projected.y);

                if (touch.x > 0)
                    dir++;
                else
                    dir--;

                if(dir>1)
                    dir=1;
                else if(dir<-1)
                    dir = -1;

                i++;
            }else{
                break;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A))
            if(dir>-1)
                dir--;
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            if(dir<1)
                dir++;

        if (dir*mult == -1) {
            if(getPosition().x!=Settings.SCREEN_WIDTH/2-width-Settings.WALL_WIDTH)
                setDirection(Settings.PLAYER_SPEED, 0);
        }else if (dir*mult == 1) {
            if(getPosition().x!=Settings.WALL_WIDTH)
                setDirection(-Settings.PLAYER_SPEED, 0);
        }else {
            setDirection(0, 0);
        }

        if(pos.x<Settings.WALL_WIDTH)
            pos.x = Settings.WALL_WIDTH;
        else if(pos.x>Settings.SCREEN_WIDTH/2-width-Settings.WALL_WIDTH)
            pos.x = Settings.SCREEN_WIDTH/2-width-Settings.WALL_WIDTH;
    }

    public void switchDirection(){
        mult = mult*-1;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

}