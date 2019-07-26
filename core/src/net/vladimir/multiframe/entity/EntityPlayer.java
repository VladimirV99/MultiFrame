package net.vladimir.multiframe.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.references.Settings;

public class EntityPlayer extends Entity {

    protected int left;
    protected int right;
    protected int mult;
    protected Camera camera;

    public EntityPlayer(AssetManager assetManager, int x, int y, int width, int height, int mult, int left, int right, Camera camera) {
        super(assetManager.get(AssetDescriptors.PLAYER), x, y, width, height);
        this.left = left;
        this.right = right;
        this.mult = mult;
        this.camera = camera;
    }

    @Override
    public void update(float delta) {
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

        if(dir>1)
            dir=1;
        else if(dir<-1)
            dir = -1;

        x += mult * dir * Settings.PLAYER_SPEED * delta;
        x = MathUtils.clamp(x, left, right);
    }

    public void switchDirection(){
        mult = mult*-1;
    }

}
