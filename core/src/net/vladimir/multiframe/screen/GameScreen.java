package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.entity.EntityObstacle;
import net.vladimir.multiframe.entity.EntityObstaclePair;
import net.vladimir.multiframe.entity.EntityPlayerLeft;
import net.vladimir.multiframe.entity.EntityPlayerRight;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.references.TextureManager;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private Skin skin;
    private TextureAtlas atlas;
    private Stage stage;
    private Group gameOverMenu;
    private Group pauseMenu;

    private Label lHighScoreGameOver;
    private Label lScoreGameOver;
    private Label lScorePause;

    private boolean run = false;
    private boolean ready = false;

    private int score = 0;

    private BitmapFont font;

    private EntityPlayerLeft playerLeft;
    private EntityPlayerRight playerRight;

    private int selectorX = -(Settings.SCREEN_WIDTH/2);

    private ArrayList<EntityObstaclePair> obstacles = new ArrayList<EntityObstaclePair>();

    private int nX = MathUtils.random(0, 1)==1 ? -640 : 0;

    private MultiFrame game;
    private SpriteBatch batch;

    public GameScreen(MultiFrame game) {
        this.game = game;
        this.batch = game.getBatch();
    }

    public int nextX(int pass){
        if(Settings.OBSTACLE_SWITCH==-1){
            if(MathUtils.random(0, 1)==1){
                switchObstacle();
            }
        }else{
            if(Settings.OBSTACLE_SWITCH!=0 && (score+pass)%Settings.OBSTACLE_SWITCH==0){
                switchObstacle();
            }
        }
        return nX;
    }

    @Override
    public void show() {
        atlas = new TextureAtlas("ui/menuskin.pack");
        skin = new Skin(Gdx.files.internal("ui/menuskin.json"), atlas);

        font = new BitmapFont(Gdx.files.internal("Arial.fnt"));
        font.setColor(Color.BLACK);
        font.getData().setScale(2);

        stage = new Stage(new FitViewport(1280, 720));
        stage.getCamera().translate(-640, -360, 0);

        initObjects();

        gameOverMenu = new Group();

        Image backgroundGameOver = new Image(TextureManager.PLAYER);
        backgroundGameOver.setBounds(-150, -200, 300, 400);

        lHighScoreGameOver = new Label("high: " + String.valueOf(Settings.HIGH_SCORE), skin, "white");
        lHighScoreGameOver.setPosition(-lHighScoreGameOver.getPrefWidth()/2, 150);

        lScoreGameOver = new Label("0", skin, "white");
        lScoreGameOver.setFontScale(2);
        lScoreGameOver.setPosition(-lScoreGameOver.getPrefWidth()/2, 100);

        TextButton bRetryGameOver = new TextButton("Retry", skin, "default");
        bRetryGameOver.setWidth(150);
        bRetryGameOver.setHeight(70);
        bRetryGameOver.setPosition(-bRetryGameOver.getWidth()/2, 0);
        bRetryGameOver.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                initObjects();
                ready = false;
                run = true;
                gameOverMenu.setVisible(false);
            }
        });

        TextButton bMenuGameOver = new TextButton("Menu", skin, "default");
        bMenuGameOver.setWidth(150);
        bMenuGameOver.setHeight(70);
        bMenuGameOver.setPosition(-bMenuGameOver.getWidth()/2, -20-bMenuGameOver.getHeight());
        bMenuGameOver.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                run = false;
                gameOverMenu.setVisible(false);
                game.setScreen(new MenuScreen(game));
            }
        });

        TextButton bExitGameOver = new TextButton("Exit", skin, "default");
        bExitGameOver.setWidth(150);
        bExitGameOver.setHeight(70);
        bExitGameOver.setPosition(-bExitGameOver.getWidth()/2, -40-bMenuGameOver.getHeight()-bExitGameOver.getHeight());
        bExitGameOver.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                run = false;
                gameOverMenu.setVisible(false);
                Gdx.app.exit();
            }
        });

        gameOverMenu.addActor(backgroundGameOver);
        gameOverMenu.addActor(lHighScoreGameOver);
        gameOverMenu.addActor(lScoreGameOver);
        gameOverMenu.addActor(bRetryGameOver);
        gameOverMenu.addActor(bMenuGameOver);
        gameOverMenu.addActor(bExitGameOver);

        pauseMenu = new Group();

        Image backgroundPause = new Image(TextureManager.PLAYER);
        backgroundPause.setBounds(-150, -300, 300, 500);

        lScorePause = new Label("0", skin, "white");
        lScorePause.setFontScale(2);
        lScorePause.setPosition(-lScorePause.getPrefWidth()/2, 100);

        TextButton bResumePause = new TextButton("Resume", skin, "default");
        bResumePause.setWidth(150);
        bResumePause.setHeight(70);
        bResumePause.setPosition(-bResumePause.getWidth()/2, 0);
        bResumePause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resumeGame();
            }
        });

        TextButton bRetryPause = new TextButton("Retry", skin, "default");
        bRetryPause.setWidth(150);
        bRetryPause.setHeight(70);
        bRetryPause.setPosition(-bRetryPause.getWidth()/2, -20-bResumePause.getHeight());
        bRetryPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                initObjects();
                ready = false;
                run = true;
                pauseMenu.setVisible(false);
            }
        });

        TextButton bMenuPause = new TextButton("Menu", skin, "default");
        bMenuPause.setWidth(150);
        bMenuPause.setHeight(70);
        bMenuPause.setPosition(-bMenuPause.getWidth()/2, -40-bResumePause.getHeight()-bMenuPause.getHeight());
        bMenuPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                run = false;
                pauseMenu.setVisible(false);
                game.setScreen(new MenuScreen(game));
            }
        });

        TextButton bExitPause = new TextButton("Exit", skin, "default");
        bExitPause.setWidth(150);
        bExitPause.setHeight(70);
        bExitPause.setPosition(-bExitPause.getWidth()/2, -60-bResumePause.getHeight()-bMenuPause.getHeight()-bExitPause.getHeight());
        bExitPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                run = false;
                pauseMenu.setVisible(false);
                Gdx.app.exit();
            }
        });

        pauseMenu.addActor(backgroundPause);
        pauseMenu.addActor(lScorePause);
        pauseMenu.addActor(bResumePause);
        pauseMenu.addActor(bRetryPause);
        pauseMenu.addActor(bMenuPause);
        pauseMenu.addActor(bExitPause);

        stage.addActor(pauseMenu);
        stage.addActor(gameOverMenu);

        pauseMenu.setVisible(false);
        gameOverMenu.setVisible(false);

        Gdx.input.setInputProcessor(stage);

        run = true;
    }

    private void pauseGame(){
        if(run) {
            lScorePause.setText(String.valueOf(score));
            lScorePause.setPosition(-lScorePause.getPrefWidth() / 2, 100);
            run = false;
            pauseMenu.setVisible(true);
        }
    }

    private void resumeGame(){
        pauseMenu.setVisible(false);
        run = true;
    }

    private void update() {

        stage.act(Gdx.graphics.getDeltaTime());

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            System.out.println("Back");
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            pauseGame();
        }

        if(run) {

            playerLeft.update();
            playerRight.update();

            for(EntityObstaclePair obstacle : obstacles)
                obstacle.update();

            for(EntityObstaclePair obstacle : obstacles)
                if(obstacle.intersects(playerLeft.getBounds()) || obstacle.intersects(playerRight.getBounds()))
                    gameOver();

        }else{

            if(!ready && (Gdx.input.isTouched(0) && Gdx.input.isTouched(1) || Gdx.input.isKeyJustPressed(Input.Keys.W))){
                initObjects();
                gameOverMenu.setVisible(false);
                ready = true;
            }

            if((!Gdx.input.isTouched() && !Gdx.input.isKeyPressed(Input.Keys.W)) && ready){
                ready = false;
                run = true;
            }
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(stage.getCamera().combined);

        batch.begin();

        batch.draw(TextureManager.WALL, -(Settings.SCREEN_WIDTH/2), -Settings.SCREEN_HEIGHT/2, Settings.WALL_WIDTH, Settings.SCREEN_HEIGHT);
        batch.draw(TextureManager.WALL, -Settings.WALL_WIDTH, -Settings.SCREEN_HEIGHT/2, Settings.WALL_WIDTH, Settings.SCREEN_HEIGHT);
        playerLeft.render(batch);

        batch.draw(TextureManager.WALL, 0, -Settings.SCREEN_HEIGHT/2, Settings.WALL_WIDTH, Settings.SCREEN_HEIGHT);
        batch.draw(TextureManager.WALL, (Settings.SCREEN_WIDTH/2)-Settings.WALL_WIDTH, -Settings.SCREEN_HEIGHT/2, Settings.WALL_WIDTH, Settings.SCREEN_HEIGHT);
        playerRight.render(batch);


        for(EntityObstaclePair obstacle : obstacles){
            obstacle.render(batch);
        }

        font.draw(batch, score+"", -320, 280);

        batch.draw(TextureManager.SELECTOR, selectorX, Settings.SCREEN_HEIGHT/2-20);

        batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        font.dispose();
    }

    @Override
    public void pause() {
        pauseGame();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void onPass() {
        score++;
        if(Settings.PLAYER_SWITCH==-1){
            if(MathUtils.random(0, 1)==1){
                switchControls();
            }
        }else{
            if(Settings.PLAYER_SWITCH!=0) {
                if (score % Settings.PLAYER_SWITCH == 0) {
                    switchControls();
                }
            }
        }
    }

    private void switchControls(){
        playerLeft.switchDirection();
        playerRight.switchDirection();
        if(selectorX == -(Settings.SCREEN_WIDTH/2))
            selectorX = 0;
        else
            selectorX = -(Settings.SCREEN_WIDTH/2);
    }

    private void switchObstacle(){
        if(nX==-640)
            nX=0;
        else
            nX=-640;
    }

    private void gameOver(){
        lHighScoreGameOver.setText("high: " + String.valueOf(Settings.HIGH_SCORE));
        lHighScoreGameOver.setPosition(-lHighScoreGameOver.getPrefWidth()/2, 150);
        Settings.setLastScore(score);
        if(score > Settings.HIGH_SCORE)
            Settings.setHighScore(score);
        lScoreGameOver.setText(Integer.toString(score));
        lScoreGameOver.setPosition(-lScoreGameOver.getPrefWidth()/2, 100);
        gameOverMenu.setVisible(true);
        nX = MathUtils.random(0, 1)==1 ? -640 : 0;
        run = false;
    }

    private void initObjects(){
        obstacles.clear();
        nX = MathUtils.random(0, 1)==1 ? -640 : 0;
        selectorX = -(Settings.SCREEN_WIDTH/2);
        score = 0;
        playerLeft = new EntityPlayerLeft(new Vector2(-345, Settings.PLAYER_Y), 50, 50, new Vector2(0, 0), stage.getCamera());
        playerRight = new EntityPlayerRight(new Vector2(295, Settings.PLAYER_Y), 50, 50, new Vector2(0, 0), stage.getCamera());

        for(int i = 0; i<Settings.OBSTACLE_COUNT; i++){
            obstacles.add(new EntityObstaclePair(i, i, this));
        }
    }

}