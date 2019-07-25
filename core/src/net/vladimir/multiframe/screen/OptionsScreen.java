package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.references.EnumSettings;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.utils.RenderUtils;

import java.util.HashMap;
import java.util.Map;

public class OptionsScreen implements Screen {

    private final MultiFrame game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private Stage stage;
    private Skin skin;

    private HashMap<EnumSettings, TextField> options = new HashMap<EnumSettings, TextField>();

    public OptionsScreen(MultiFrame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Settings.MENU_WIDTH,  Settings.MENU_HEIGHT), batch);
        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        options.clear();

        Table table = new Table();
        Table optionsTable = new Table();

//        table.setDebug(true);
//        optionsTable.setDebug(true);

        addNumberOption(optionsTable, EnumSettings.PLAYER_SWITCH, Settings.PLAYER_SWITCH);
        addNumberOption(optionsTable, EnumSettings.PLAYER_SPEED, Settings.PLAYER_SPEED);
        addNumberOption(optionsTable, EnumSettings.PLAYER_Y, Settings.PLAYER_Y);
        addNumberOption(optionsTable, EnumSettings.OBSTACLE_SWITCH, Settings.OBSTACLE_SWITCH);
        addNumberOption(optionsTable, EnumSettings.OBSTACLE_HEIGHT, Settings.OBSTACLE_HEIGHT);
        addNumberOption(optionsTable, EnumSettings.OBSTACLE_GAP, Settings.OBSTACLE_GAP);
        addNumberOption(optionsTable, EnumSettings.OBSTACLE_DISTANCE, Settings.OBSTACLE_DISTANCE);
        addNumberOption(optionsTable, EnumSettings.OBSTACLE_SPEED, Settings.OBSTACLE_SPEED);
        addNumberOption(optionsTable, EnumSettings.WALL_WIDTH, Settings.WALL_WIDTH);

        ScrollPane pane = new ScrollPane(optionsTable, skin, "default");
        pane.setBounds(0, 60, Settings.MENU_WIDTH, Settings.MENU_HEIGHT-60);

        TextButton bMenu = new TextButton("Return To Menu", skin, "default");
        bMenu.setBounds(Settings.MENU_WIDTH/2-380, Settings.MENU_HEIGHT/2-340, 360, bMenu.getPrefHeight());
        bMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });

        TextButton bReset = new TextButton("Reset Defaults", skin, "default");
        bReset.setBounds(Settings.MENU_WIDTH/2+20, Settings.MENU_HEIGHT/2-340, 360, bReset.getPrefHeight());
        bReset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for(Map.Entry<EnumSettings, TextField> entry : options.entrySet()){
                    setOption(entry.getKey(), entry.getKey().getDefaultValue());
                }
            }
        });

        optionsTable.pack();

        table.add(optionsTable).expandY().fillY().colspan(2).row();
        table.add(bMenu).size(400, 60).pad(10).expandX();
        table.add(bReset).size(400, 60).pad(10).expandX();

        table.setFillParent(true);
        table.pack();

        stage.addActor(table);

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

    private void addNumberOption(Table table, final EnumSettings setting, final int value){
        final Label lText = new Label(setting.getName(), skin, "default");

        final TextField fValue = new TextField(String.valueOf(value), skin);
        fValue.setAlignment(1);
        fValue.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return Character.isDigit(c) || c=='-';
            }
        });

        TextButton bDecrease = new TextButton("-", skin, "default");
        bDecrease.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setOption(setting, decreaseValue(Settings.get(setting), setting.getStep(), setting.getMin()));
            }
        });

        TextButton bIncrease = new TextButton("+", skin, "default");
        bIncrease.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setOption(setting, increaseValue(Settings.get(setting), setting.getStep(), setting.getMax()));
            }
        });

        TextButton bReset = new TextButton("Reset", skin, "default");
        bReset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setOption(setting, setting.getDefaultValue());
            }
        });

        table.add(lText).width(450).expandX();
        table.add(bDecrease).size(50, 50).pad(5, 10, 5, 10);
        table.add(fValue).size(120, 60).pad(5, 0, 5, 0);
        table.add(bIncrease).size(50, 50).pad(5, 10, 5, 10);
        table.add(bReset).size(140, 60).pad(5);
        table.row();

        options.put(setting, fValue);
    }

    public void setOption(EnumSettings setting, int value){
        if(options.containsKey(setting)){
            options.get(setting).setText(String.valueOf(value));
            Settings.set(setting, value, this);
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
