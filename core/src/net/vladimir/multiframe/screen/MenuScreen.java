package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.references.References;
import net.vladimir.multiframe.utils.RenderUtils;

public class MenuScreen extends ScreenAdapter {

    private MultiFrame game;
    private AssetManager assetManager;
    private SpriteBatch batch;

    private Stage stage;
    private Skin skin;

    public MenuScreen(MultiFrame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        stage = new Stage(new FitViewport(References.MENU_WIDTH, References.MENU_HEIGHT), batch);

        Table table = new Table();

        Table buttonTable = new Table();

//        table.setDebug(true);
//        buttonTable.setDebug(true);

        TextButton bPlay = new TextButton("Play", skin, "large");
        bPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameSelectScreen(game));
            }
        });

//        TextButton bOptions = new TextButton("Options", skin, "large");
//        bOptions.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new OptionsScreen(game));
//            }
//        });

        TextButton bExit = new TextButton("Exit", skin, "large");
        bExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        buttonTable.defaults().pad(15, 100, 15, 100);
        buttonTable.add(bPlay).expandX().fill().row();
//        buttonTable.add(bOptions).fill().row();
        buttonTable.add(bExit).fill().row();

        Label lVersion = new Label(References.VERSION, skin, "default_white");

        table.add(buttonTable).grow().row();
        table.add(lVersion).pad(30).align(Align.bottomRight);

        table.setFillParent(true);
        table.pack();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update(delta);

        RenderUtils.clearScreen();

        batch.begin();
        game.getBackground().render(batch, delta);
        batch.end();

        stage.draw();
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
