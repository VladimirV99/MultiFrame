package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.assets.RegionNames;
import net.vladimir.multiframe.references.References;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.utils.RenderUtils;

public class OptionsScreen extends ScreenAdapter {

    private MultiFrame game;
    private AssetManager assetManager;
    private SpriteBatch batch;

    private Stage stage;
    private Skin skin;

    public OptionsScreen(MultiFrame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        stage = new Stage(
                new ExtendViewport(References.MENU_WIDTH, References.MENU_HEIGHT, References.MENU_WIDTH_MAX, References.MENU_HEIGHT_MAX),
                batch
        );

        Table table = new Table();
        Table optionsTable = new Table(skin);
        optionsTable.background(new NinePatchDrawable(assetManager.get(AssetDescriptors.UI_SKIN).getAtlas().createPatch(RegionNames.TABLE_BACKGROUND)));

        Table volumeTable = new Table(skin);
        Label lVolume = new Label("Volume:", skin, "default_white");
        Slider sVolume = new Slider(0, 100, 10, false, skin);
        final Label lVolumePercentage = new Label("100%", skin, "default_white");
        sVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = ((Slider)actor).getValue();
                Settings.setVolume(volume);
                lVolumePercentage.setText((int)volume + "%");
            }
        });
        sVolume.setValue(Settings.getVolumePercentage());
        volumeTable.defaults().pad(0, 10, 0 , 10);
        volumeTable.add(lVolume).padRight(60);
        volumeTable.add(sVolume).growX();
        volumeTable.add(lVolumePercentage);
        volumeTable.pack();

        CheckBox cbVibrate = new CheckBox("Vibrate on control switch", skin, "default_white");
        cbVibrate.getLabelCell().padLeft(20);
        cbVibrate.setChecked(Settings.getVibrate());
        cbVibrate.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.setVibrate(((CheckBox)actor).isChecked());
            }
        });

        optionsTable.align(Align.top);
        optionsTable.pad(20);
        optionsTable.defaults().pad(20, 60, 20, 60);
        optionsTable.add(volumeTable).growX().row();
        optionsTable.add(cbVibrate).growX();

        optionsTable.pack();

        TextButton bBack = new TextButton("Back", skin, "large");
        bBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });

        table.pad(20);
        table.add(optionsTable).grow().row();
        table.add(bBack).padTop(20).align(Align.bottom).growX();

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
        stage.getViewport().update(width, height, true);
        game.getBackground().resize((int)stage.getViewport().getWorldWidth(), (int)stage.getViewport().getWorldHeight());
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
