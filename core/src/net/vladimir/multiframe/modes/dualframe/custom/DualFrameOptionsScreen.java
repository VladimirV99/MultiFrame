package net.vladimir.multiframe.modes.dualframe.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.references.References;
import net.vladimir.multiframe.screen.GameSelectScreen;
import net.vladimir.multiframe.utils.RenderUtils;

import java.util.HashMap;
import java.util.Map;

public class DualFrameOptionsScreen implements Screen {

    private final MultiFrame game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private Stage stage;
    private Skin skin;

    private HashMap<EnumDualFrameSettings, TextField> options = new HashMap<EnumDualFrameSettings, TextField>();

    public DualFrameOptionsScreen(MultiFrame game) {
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

        options.clear();

        Table table = new Table();
        Table optionsTable = new Table();
        ScrollPane optionsScroll = new ScrollPane(optionsTable, skin);

        addNumberOption(optionsTable, EnumDualFrameSettings.PLAYER_SWITCH, DualFrameSettings.get(EnumDualFrameSettings.PLAYER_SWITCH));
        addNumberOption(optionsTable, EnumDualFrameSettings.PLAYER_SPEED, DualFrameSettings.get(EnumDualFrameSettings.PLAYER_SPEED));
        addNumberOption(optionsTable, EnumDualFrameSettings.PLAYER_Y, DualFrameSettings.get(EnumDualFrameSettings.PLAYER_Y));
        addNumberOption(optionsTable, EnumDualFrameSettings.OBSTACLE_SWITCH, DualFrameSettings.get(EnumDualFrameSettings.OBSTACLE_SWITCH));
        addNumberOption(optionsTable, EnumDualFrameSettings.OBSTACLE_HEIGHT, DualFrameSettings.get(EnumDualFrameSettings.OBSTACLE_HEIGHT));
        addNumberOption(optionsTable, EnumDualFrameSettings.OBSTACLE_GAP, DualFrameSettings.get(EnumDualFrameSettings.OBSTACLE_GAP));
        addNumberOption(optionsTable, EnumDualFrameSettings.OBSTACLE_DISTANCE, DualFrameSettings.get(EnumDualFrameSettings.OBSTACLE_DISTANCE));
        addNumberOption(optionsTable, EnumDualFrameSettings.OBSTACLE_SPEED, DualFrameSettings.get(EnumDualFrameSettings.OBSTACLE_SPEED));
        addNumberOption(optionsTable, EnumDualFrameSettings.WALL_WIDTH, DualFrameSettings.get(EnumDualFrameSettings.WALL_WIDTH));

        TextButton bBack = new TextButton("Back", skin, "large");
        bBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                DualFrameSettings.discard();
                game.setScreen(new GameSelectScreen(game));
            }
        });

        TextButton bSave = new TextButton("Save", skin, "large");
        bSave.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                DualFrameSettings.save();
                game.setScreen(new GameSelectScreen(game));
            }
        });

        TextButton bReset = new TextButton("Reset All", skin, "large");
        bReset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for(Map.Entry<EnumDualFrameSettings, TextField> entry : options.entrySet()){
                    setOption(entry.getKey(), entry.getKey().getDefaultValue());
                }
            }
        });

        optionsTable.pack();

        table.defaults().pad(10);
        table.add(optionsScroll).pad(20, 20, 0, 20).expand().fill().colspan(3).row();
        table.add(bBack).width(360);
        table.add(bSave).width(360);
        table.add(bReset).width(360);

        table.setFillParent(true);
        table.pack();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update();

        RenderUtils.clearScreen();

        batch.begin();
        game.getBackground().render(batch, delta);
        batch.end();

        stage.draw();
    }

    private void update() {
        stage.act(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        game.getBackground().resize((int)stage.getViewport().getWorldWidth(), (int)stage.getViewport().getWorldHeight());
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

    private void addNumberOption(Table table, final EnumDualFrameSettings setting, final int value){
        final Label lText = new Label(setting.getName(), skin, "default_white");

        final TextField fValue = new TextField(String.valueOf(value), skin);
        fValue.setAlignment(Align.center);
        fValue.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return Character.isDigit(c) || c=='-';
            }
        });

        Button bDecrease = new Button(skin, "minus");
        bDecrease.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setOption(setting, decreaseValue(DualFrameSettings.get(setting), setting.getStep(), setting.getMin()));
            }
        });

        Button bIncrease = new Button(skin, "plus");
        bIncrease.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setOption(setting, increaseValue(DualFrameSettings.get(setting), setting.getStep(), setting.getMax()));
            }
        });

        TextButton bReset = new TextButton("Reset", skin, "default");
        bReset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setOption(setting, setting.getDefaultValue());
            }
        });

        table.pad(10);
        table.add(lText).align(Align.left).expandX();
        table.add(bDecrease).size(50, 50).pad(5, 10, 5, 10);
        table.add(fValue).size(120, 60).pad(5, 0, 5, 0);
        table.add(bIncrease).size(50, 50).pad(5, 10, 5, 10);
        table.add(bReset).width(180).pad(5);
        table.row();

        options.put(setting, fValue);
    }

    public void setOption(EnumDualFrameSettings setting, int value){
        if(options.containsKey(setting)){
            options.get(setting).setText(String.valueOf(value));
            DualFrameSettings.set(setting, value);
        }
    }

    private int decreaseValue(int value, int step, int min){
        int newValue = value;
        newValue -= step;
        if(newValue < min)
            newValue = min;
        return newValue;
    }

    private int increaseValue(int value, int step, int max){
        int newValue = value;
        newValue += step;
        if(newValue > max)
            newValue = max;
        return newValue;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
