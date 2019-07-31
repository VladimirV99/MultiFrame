package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.frame.FrameOrchestrator;
import net.vladimir.multiframe.frame.IFrameHandler;
import net.vladimir.multiframe.frame.IGameListener;
import net.vladimir.multiframe.references.References;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.utils.RenderUtils;

public class GameScreen implements Screen, IGameListener {

    private MultiFrame game;
    private AssetManager assetManager;
    private SpriteBatch batch;

    private Skin skin;
    private BitmapFont font;
    private Texture playerTexture;

    private Viewport viewport;
    private Viewport uiViewport;

    private Stage stage;
    private Table gameOverPanel;
    private Table pausePanel;

    private Label lHighScoreGameOver;
    private Label lScoreGameOver;
    private Label lScorePause;

    private boolean ready;

    private int score;

    private FrameOrchestrator frameOrchestrator;
    private IFrameHandler frameHandler;

    public GameScreen(MultiFrame game, IFrameHandler frameHandler) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
        this.frameHandler = frameHandler;

        this.ready = false;

        this.score = 0;
    }

    @Override
    public void show() {
        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        font = assetManager.get(AssetDescriptors.UI_FONT);
        font.setColor(Color.BLACK);
        font.getData().setScale(2);

        playerTexture = assetManager.get(AssetDescriptors.PLAYER);

        viewport = new FitViewport(References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
        uiViewport = new FitViewport(References.MENU_WIDTH, References.MENU_HEIGHT);

        stage = new Stage(uiViewport);

        initOrchestrator();
        initGameOverPanel();
        initPausePanel();

        stage.addActor(pausePanel);
        stage.addActor(gameOverPanel);

        Gdx.input.setInputProcessor(stage);

        startGame();
    }

    private void initOrchestrator() {
        frameOrchestrator = new FrameOrchestrator(batch, viewport.getCamera(), assetManager, this, frameHandler);
        frameOrchestrator.init();
    }

    private void initGameOverPanel() {
        gameOverPanel = new Table();

        final Table gameOverMenu = new Table();
        gameOverMenu.background(new TextureRegionDrawable(playerTexture));

        lHighScoreGameOver = new Label("", skin, "white");

        lScoreGameOver = new Label("0", skin, "white");
        lScoreGameOver.setFontScale(2);

        TextButton bRetryGameOver = new TextButton("Retry", skin, "default");
        bRetryGameOver.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                frameOrchestrator.reset();
                startGame();
                ready = false;
                frameOrchestrator.resume();
                gameOverPanel.setVisible(false);
            }
        });

        TextButton bMenuGameOver = new TextButton("Menu", skin, "default");
        bMenuGameOver.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                frameOrchestrator.pause();
                gameOverPanel.setVisible(false);
                game.setScreen(new MenuScreen(game));
            }
        });

        TextButton bExitGameOver = new TextButton("Exit", skin, "default");
        bExitGameOver.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                frameOrchestrator.pause();
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

        gameOverPanel.setVisible(false);
    }

    private void initPausePanel() {
        pausePanel = new Table();

        final Table pauseMenu = new Table();
        pauseMenu.background(new TextureRegionDrawable(playerTexture));

        lScorePause = new Label("", skin, "white");
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
                frameOrchestrator.reset();
                startGame();
                ready = false;
                frameOrchestrator.resume();
                pausePanel.setVisible(false);
            }
        });

        TextButton bMenuPause = new TextButton("Menu", skin, "default");
        bMenuPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                frameOrchestrator.pause();
                pausePanel.setVisible(false);
                game.setScreen(new MenuScreen(game));
            }
        });

        TextButton bExitPause = new TextButton("Exit", skin, "default");
        bExitPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                frameOrchestrator.pause();
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

        pausePanel.setVisible(false);
    }

    private void pauseGame(){
        if(frameOrchestrator.isRunning()) {
            lScorePause.setText(String.valueOf(score));
            frameOrchestrator.pause();
            pausePanel.setVisible(true);
        }
    }

    private void resumeGame(){
        pausePanel.setVisible(false);
        frameOrchestrator.resume();
    }

    @Override
    public void render(float delta) {
        update(delta);

        RenderUtils.clearScreen();

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        frameOrchestrator.render(delta);

        font.draw(batch, score+"", -320, 280);

        batch.end();

        uiViewport.apply();
        stage.draw();
    }

    private void update(float delta) {
        stage.act(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            pauseGame();
        }

        frameOrchestrator.update(delta);

        if(!frameOrchestrator.isRunning()) {
            if(!ready && (Gdx.input.isTouched(0) && Gdx.input.isTouched(1) || Gdx.input.isKeyJustPressed(Input.Keys.W))){
                startGame();
                gameOverPanel.setVisible(false);
                ready = true;
            }

            if((!Gdx.input.isTouched() && !Gdx.input.isKeyPressed(Input.Keys.W)) && ready){
                ready = false;
                frameOrchestrator.resume();
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

    @Override
    public void gameOver(){
        lHighScoreGameOver.setText("high: " + Settings.getHighScore(frameHandler.getId()));
        if(score > Settings.getHighScore(frameHandler.getId()))
            Settings.setHighScore(frameHandler.getId(), score);
        lScoreGameOver.setText(Integer.toString(score));
        gameOverPanel.setVisible(true);
    }

    @Override
    public void incrementScore() {
        score++;
    }

    private void startGame() {
        score = 0;
        frameOrchestrator.getFrame(0).setFocus(true);
        frameOrchestrator.start();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}