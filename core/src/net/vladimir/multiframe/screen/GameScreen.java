package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.assets.RegionNames;
import net.vladimir.multiframe.frame.FrameOrchestrator;
import net.vladimir.multiframe.frame.IFrameHandler;
import net.vladimir.multiframe.frame.IGameListener;
import net.vladimir.multiframe.references.References;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.utils.ScrollingBackground;
import net.vladimir.multiframe.utils.RenderUtils;

public class GameScreen implements Screen, IGameListener {

    private MultiFrame game;
    private AssetManager assetManager;
    private SpriteBatch batch;

    private Skin skin;
    private BitmapFont fontLarge;

    private Viewport viewport;
    private Viewport uiViewport;

    private Stage stage;
    private Table gameOverPanel;
    private Table pausePanel;

    private GlyphLayout glyphLayout;
    private Label lHighScoreGameOver;
    private Label lScoreGameOver;
    private Label lScorePause;

    private ScrollingBackground background;

    private boolean ready;

    private int score;

    private FrameOrchestrator frameOrchestrator;
    private IFrameHandler frameHandler;

    private Sound playerExplode;
    private Sound playerScore;

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

        fontLarge = assetManager.get(AssetDescriptors.UI_SKIN).getFont(RegionNames.FONT_LARGE);
        fontLarge.setColor(Color.BLACK);

        playerExplode = assetManager.get(AssetDescriptors.SOUND_PLAYER_EXPLODE);
        playerScore = assetManager.get(AssetDescriptors.SOUND_PLAYER_SCORE);

        viewport = new FitViewport(References.SCREEN_WIDTH, References.SCREEN_HEIGHT);
        uiViewport = new FitViewport(References.MENU_WIDTH, References.MENU_HEIGHT);

        stage = new Stage(uiViewport);

        glyphLayout = new GlyphLayout();

        initOrchestrator();
        initGameOverPanel();
        initPausePanel();

        stage.addActor(pausePanel);
        stage.addActor(gameOverPanel);

        Gdx.input.setInputProcessor(stage);

        this.background = new ScrollingBackground(
                assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS).findRegion(RegionNames.BACKGROUND_LINES),
                ScrollingBackground.Direction.UP,
                200
        );

        startGame();
    }

    private void initOrchestrator() {
        frameOrchestrator = new FrameOrchestrator(batch, viewport.getCamera(), assetManager, this, frameHandler);
        frameOrchestrator.init();
    }

    private void initGameOverPanel() {
        gameOverPanel = new Table();

        final Table gameOverMenu = new Table();
        gameOverMenu.background(new NinePatchDrawable(assetManager.get(AssetDescriptors.UI_SKIN).getAtlas().createPatch(RegionNames.PANEL)));

        lHighScoreGameOver = new Label("", skin, "default");

        lScoreGameOver = new Label("0", skin, "large");

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

        gameOverMenu.defaults().pad(5, 20, 5, 20);

        gameOverMenu.add(lHighScoreGameOver).row();
        gameOverMenu.add(lScoreGameOver).pad(20).row();
        gameOverMenu.add(bRetryGameOver).width(300).row();
        gameOverMenu.add(bMenuGameOver).width(300).row();
        gameOverMenu.add(bExitGameOver).width(300);

        gameOverMenu.pack();

        gameOverPanel.add(gameOverMenu).center();
        gameOverPanel.setFillParent(true);
        gameOverPanel.pack();

        gameOverPanel.setVisible(false);
    }

    private void initPausePanel() {
        pausePanel = new Table();

        final Table pauseMenu = new Table();
        pauseMenu.background(new NinePatchDrawable(assetManager.get(AssetDescriptors.UI_SKIN).getAtlas().createPatch(RegionNames.PANEL)));

        lScorePause = new Label("", skin, "large");

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

        pauseMenu.defaults().pad(5, 20, 5, 20);

        pauseMenu.add(lScorePause).row();
        pauseMenu.add(bResumePause).width(300).row();
        pauseMenu.add(bRetryPause).width(300).row();
        pauseMenu.add(bMenuPause).width(300).row();
        pauseMenu.add(bExitPause).width(300);

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

        background.render(batch, delta);

        frameOrchestrator.render(delta);

        glyphLayout.setText(fontLarge, score+"");
        fontLarge.draw(batch, score+"", -References.SCREEN_WIDTH/4-glyphLayout.width/2, References.SCREEN_HEIGHT/2-glyphLayout.height-10);

        batch.end();

        uiViewport.apply();
        stage.draw();
    }

    private void update(float delta) {
        stage.act(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            pauseGame();
        }

        if(frameOrchestrator.isRunning())
            background.update(delta);

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
        playerExplode.play(Settings.getVolume());
        lHighScoreGameOver.setText("high: " + Settings.getHighScore(frameHandler.getId()));
        if(score > Settings.getHighScore(frameHandler.getId()))
            Settings.setHighScore(frameHandler.getId(), score);
        lScoreGameOver.setText(Integer.toString(score));
        gameOverPanel.addAction(Actions.sequence(Actions.delay(0.4f, Actions.show())));
    }

    @Override
    public void incrementScore() {
        playerScore.play(Settings.getVolume());
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