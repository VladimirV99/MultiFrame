package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.entity.EntityObstaclePair;
import net.vladimir.multiframe.entity.EntityPlayer;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.utils.RenderUtils;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private MultiFrame game;
    private AssetManager assetManager;
    private SpriteBatch batch;

    private Skin skin;
    private BitmapFont font;
    private Texture wallTexture;
    private Texture selectorTexture;
    private Texture playerTexture;

    private Viewport viewport;
    private Viewport uiViewport;

    private Stage stage;
    private Table gameOverPanel;
    private Table pausePanel;

    private Label lHighScoreGameOver;
    private Label lScoreGameOver;
    private Label lScorePause;

    private boolean run = false;
    private boolean ready = false;

    private int score = 0;

    private EntityPlayer playerLeft;
    private EntityPlayer playerRight;

    private float selectorX = -(Settings.SCREEN_WIDTH/2);

    private ArrayList<EntityObstaclePair> obstacles = new ArrayList<EntityObstaclePair>();

    private int nX = MathUtils.random(0, 1)==1 ? -640 : 0;

    public GameScreen(MultiFrame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
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
        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        font = assetManager.get(AssetDescriptors.UI_FONT);
        font.setColor(Color.BLACK);
        font.getData().setScale(2);

        wallTexture = assetManager.get(AssetDescriptors.WALL);
        selectorTexture = assetManager.get(AssetDescriptors.SELECTOR);
        playerTexture = assetManager.get(AssetDescriptors.PLAYER);

        viewport = new FitViewport(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        uiViewport = new FitViewport(Settings.MENU_WIDTH, Settings.MENU_HEIGHT);

        stage = new Stage(uiViewport);

        initObjects();

        gameOverPanel = new Table();

        final Table gameOverMenu = new Table();
        gameOverMenu.background(new TextureRegionDrawable(playerTexture));

        lHighScoreGameOver = new Label("high: " + Settings.HIGH_SCORE, skin, "white");

        lScoreGameOver = new Label("0", skin, "white");
        lScoreGameOver.setFontScale(2);

        TextButton bRetryGameOver = new TextButton("Retry", skin, "default");
        bRetryGameOver.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                initObjects();
                ready = false;
                run = true;
                gameOverPanel.setVisible(false);
            }
        });

        TextButton bMenuGameOver = new TextButton("Menu", skin, "default");
        bMenuGameOver.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                run = false;
                gameOverPanel.setVisible(false);
                game.setScreen(new MenuScreen(game));
            }
        });

        TextButton bExitGameOver = new TextButton("Exit", skin, "default");
        bExitGameOver.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                run = false;
                gameOverPanel.setVisible(false);
                Gdx.app.exit();
            }
        });

        gameOverMenu.defaults().pad(10, 20, 10, 20);

        gameOverMenu.add(lHighScoreGameOver).row();
        gameOverMenu.add(lScoreGameOver).row();
        gameOverMenu.add(bRetryGameOver).size(180, 50).row();
        gameOverMenu.add(bMenuGameOver).size(180, 50).row();
        gameOverMenu.add(bExitGameOver).size(180, 50);

        gameOverMenu.pack();

        gameOverPanel.add(gameOverMenu).center();
        gameOverPanel.setFillParent(true);
        gameOverPanel.pack();



        pausePanel = new Table();

        final Table pauseMenu = new Table();
        pauseMenu.background(new TextureRegionDrawable(playerTexture));

        lScorePause = new Label("0", skin, "white");
        lScorePause.setFontScale(2);

        TextButton bResumePause = new TextButton("Resume", skin, "default");
        bResumePause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resumeGame();
            }
        });

        TextButton bRetryPause = new TextButton("Retry", skin, "default");
        bRetryPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                initObjects();
                ready = false;
                run = true;
                pausePanel.setVisible(false);
            }
        });

        TextButton bMenuPause = new TextButton("Menu", skin, "default");
        bMenuPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                run = false;
                pausePanel.setVisible(false);
                game.setScreen(new MenuScreen(game));
            }
        });

        TextButton bExitPause = new TextButton("Exit", skin, "default");
        bExitPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                run = false;
                pausePanel.setVisible(false);
                Gdx.app.exit();
            }
        });

        pauseMenu.defaults().pad(10, 20, 10, 20);

        pauseMenu.add(lScorePause).row();
        pauseMenu.add(bResumePause).size(180, 50).row();
        pauseMenu.add(bRetryPause).size(180, 50).row();
        pauseMenu.add(bMenuPause).size(180, 50).row();
        pauseMenu.add(bExitPause).size(180, 50);

        pauseMenu.pack();

        pausePanel.add(pauseMenu).center();
        pausePanel.setFillParent(true);
        pausePanel.pack();

        stage.addActor(pausePanel);
        stage.addActor(gameOverPanel);

        pausePanel.setVisible(false);
        gameOverPanel.setVisible(false);

        Gdx.input.setInputProcessor(stage);

        run = true;
    }

    private void pauseGame(){
        if(run) {
            lScorePause.setText(String.valueOf(score));
            run = false;
            pausePanel.setVisible(true);
        }
    }

    private void resumeGame(){
        pausePanel.setVisible(false);
        run = true;
    }

    @Override
    public void render(float delta) {
        update(delta);

        RenderUtils.clearScreen();

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        batch.draw(wallTexture, -(Settings.SCREEN_WIDTH/2), -Settings.SCREEN_HEIGHT/2, Settings.WALL_WIDTH, Settings.SCREEN_HEIGHT);
        batch.draw(wallTexture, -Settings.WALL_WIDTH, -Settings.SCREEN_HEIGHT/2, Settings.WALL_WIDTH, Settings.SCREEN_HEIGHT);
        playerLeft.render(batch, delta);

        batch.draw(wallTexture, 0, -Settings.SCREEN_HEIGHT/2, Settings.WALL_WIDTH, Settings.SCREEN_HEIGHT);
        batch.draw(wallTexture, (Settings.SCREEN_WIDTH/2)-Settings.WALL_WIDTH, -Settings.SCREEN_HEIGHT/2, Settings.WALL_WIDTH, Settings.SCREEN_HEIGHT);
        playerRight.render(batch, delta);

        for(EntityObstaclePair obstacle : obstacles){
            obstacle.render(batch, delta);
        }

        font.draw(batch, score+"", -320, 280);

        batch.draw(selectorTexture, selectorX, Settings.SCREEN_HEIGHT/2-20);

        batch.end();

        uiViewport.apply();
        stage.draw();
    }

    private void update(float delta) {

        stage.act(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            System.out.println("Back");
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            pauseGame();
        }

        if(run) {

            playerLeft.update(delta);
            playerRight.update(delta);

            for(EntityObstaclePair obstacle : obstacles)
                obstacle.update(delta);

            for(EntityObstaclePair obstacle : obstacles)
                if(obstacle.intersects(playerLeft.getBounds()) || obstacle.intersects(playerRight.getBounds()))
                    gameOver();

        }else{

            if(!ready && (Gdx.input.isTouched(0) && Gdx.input.isTouched(1) || Gdx.input.isKeyJustPressed(Input.Keys.W))){
                initObjects();
                gameOverPanel.setVisible(false);
                ready = true;
            }

            if((!Gdx.input.isTouched() && !Gdx.input.isKeyPressed(Input.Keys.W)) && ready){
                ready = false;
                run = true;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
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
        lHighScoreGameOver.setText("high: " + Settings.HIGH_SCORE);
        Settings.setLastScore(score);
        if(score > Settings.HIGH_SCORE)
            Settings.setHighScore(score);
        lScoreGameOver.setText(Integer.toString(score));
        gameOverPanel.setVisible(true);
        nX = MathUtils.random(0, 1)==1 ? -640 : 0;
        run = false;
    }

    private void initObjects(){
        obstacles.clear();
        nX = MathUtils.random(0, 1)==1 ? -640 : 0;
        selectorX = -(Settings.SCREEN_WIDTH/2);
        score = 0;
        playerLeft = new EntityPlayer(assetManager, -345, Settings.PLAYER_Y, 50, 50, 1, -(int)Settings.SCREEN_WIDTH/2+Settings.WALL_WIDTH, -50-Settings.WALL_WIDTH, stage.getCamera());
        playerRight = new EntityPlayer(assetManager, 295, Settings.PLAYER_Y, 50, 50, -1, Settings.WALL_WIDTH, (int)Settings.SCREEN_WIDTH/2-50-Settings.WALL_WIDTH, stage.getCamera());

        for(int i = 0; i<Settings.OBSTACLE_COUNT; i++){
            obstacles.add(new EntityObstaclePair(assetManager, i, i, this));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}