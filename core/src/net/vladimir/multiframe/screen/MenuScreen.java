package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.references.Settings;

public class MenuScreen extends Screen {

    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private Label lVersion;
    private TextButton bPlay;
    private TextButton bOptions;
    private TextButton bExit;

    @Override
    public void create() {

        atlas = new TextureAtlas("ui/menuskin.pack");

        stage = new Stage(new FitViewport(Settings.SCREEN_WIDTH,  Settings.SCREEN_HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/menuskin.json"), atlas);

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
                ScreenManager.setScreen(new GameScreen());
            }
        });

        bOptions = new TextButton("Options", skin, "default");
        bOptions.setWidth(800);
        bOptions.setHeight(70);
        bOptions.setPosition(Settings.SCREEN_WIDTH/2-400, Settings.SCREEN_HEIGHT/2-115);
        bOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.setScreen(new OptionsScreen());
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
    public void update() {
        stage.act(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(SpriteBatch sb) {
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        atlas.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}