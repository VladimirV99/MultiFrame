package net.vladimir.multiframe.entity;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.event.EventType;
import net.vladimir.multiframe.frame.IFrame;

public class EntityHorizontalObstacle extends EntityObstacle {

    private Rectangle left;
    private Rectangle right;

    private IFrame frame;

    private int obstacleSpeed;
    private int obstacleGap;
    private int obstacleHeight;
    private int wallWidth;
    private int playerY;

    private NinePatchDrawable textureLeft;
    private NinePatchDrawable textureRight;

    public EntityHorizontalObstacle(TextureRegion left, TextureRegion right, int obstacleSpeed, int obstacleGap, int obstacleHeight, int wallWidth, int playerY) {
        super(null);
        this.textureLeft = new NinePatchDrawable(new NinePatch(left, 4, 4, 4, 4));
        this.textureRight = new NinePatchDrawable(new NinePatch(right, 4, 4, 4, 4));
        this.obstacleSpeed = obstacleSpeed;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.wallWidth = wallWidth;
        this.playerY = playerY;
    }

    public void init(IFrame frame, int y) {
        this.frame = frame;
        this.passed = false;
        setPosition(y);
    }

    private void setPosition(int y) {
        int mid = MathUtils.random(obstacleGap/2, frame.getWidth()-2*wallWidth-obstacleGap/2);

        left = new Rectangle(wallWidth, y, mid-obstacleGap/2, obstacleHeight);
        right = new Rectangle(wallWidth+mid+obstacleGap/2, y, frame.getWidth()-mid-2*wallWidth-obstacleGap/2, obstacleHeight);

        setPosition(new Vector2(wallWidth, y));

        passed = false;
    }

    @Override
    public void update(float delta) {
        addY((int)(obstacleSpeed * delta));
        left.y = getY();
        right.y = getY();

        if(getY() >= frame.getHeight()) {
            setDead();
            return;
        }

        if(getY() > playerY && !passed) {
            passed = true;
            frame.getOrchestrator().getHandler().handle(new Event(EventType.OBSTACLE_PASS, 0));
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        textureLeft.draw(batch, offsetX+left.x, offsetY+left.y, left.width, left.height);
        textureRight.draw(batch, offsetX+right.x, offsetY+right.y, right.width, right.height);
    }

    @Override
    public boolean checkCollision(EntityPlayer player) {
        if(player.getBounds().overlaps(left) || player.getBounds().overlaps(right)) {
            int clip = 5;
            int multiplier = frame.isInFocus() ? 1 : -1;
            int move = (int) left.x + (int) left.width - player.getX();
            if (multiplier * player.dirX >= 0 && move > 0 && move < clip) {
                frame.getOrchestrator().raiseEvent(new Event(EventType.CLIP_PLAYER, multiplier * move));
                return false;
            }
            move = player.getX() + player.getWidth() - (int) right.x;
            if (multiplier * player.dirX <= 0 && move > 0 && move < clip) {
                frame.getOrchestrator().raiseEvent(new Event(EventType.CLIP_PLAYER, -(multiplier * move)));
                return false;
            }
            return true;
        }
        return false;
    }

}