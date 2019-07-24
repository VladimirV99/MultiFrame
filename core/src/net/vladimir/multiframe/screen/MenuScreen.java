package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.utils.RenderUtils;

public class MenuScreen implements Screen {

    private MultiFrame game;
    private AssetManager assetManager;
    private SpriteBatch batch;

    private Stage stage;
    private Skin skin;
    private Label lVersion;
    private TextButton bPlay;
    private TextButton bOptions;
    private TextButton bExit;

    public MenuScreen(MultiFrame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Settings.SCREEN_WIDTH,  Settings.SCREEN_HEIGHT), batch);
        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        lVersion = new Label(Settings.VERSION, skin, "default");
        lVersion.setFontScale(1.1F);
        lVersion.setBounds(Settings.SCREEN_WIDTH - 50 - lVersion.getPrefWidth(), 50, lVersion.getPrefWidth(), lVersion.getPrefHeight());

        bPlay = new TextButton("Play", skin, "default");
        bPlay.setWidth(800);
        bPlay.setHeight(70);
        bPlay.setPosition(Settings.SCREEN_WIDTH/2-400, Settings.SCREEN_HEIGHT/2-35);
        bPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });

        bOptions = new TextButton("Options", skin, "default");
        bOptions.setWidth(800);
        bOptions.setHeight(70);
        bOptions.setPosition(Settings.SCREEN_WIDTH/2-400, Settings.SCREEN_HEIGHT/2-115);
        bOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new OptionsScreen(game));
            }
        });

        bExit = new TextButton("Exit", skin, "default");
        bExit.setWidth(800);
        bExit.setHeight(70);
        bExit.setPosition(Settings.SCREEN_WIDTH/2-400, Settings.SCREEN_HEIGHT/2-195);
        bExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });


        stage.addActor(bPlay);
        stage.addActor(bOptions);
        stage.addActor(bExit);

        stage.addActor(lVersion);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update();

        RenderUtils.clearScreen();
        stage.draw();
    }

    private void update() {
        stage.act(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
