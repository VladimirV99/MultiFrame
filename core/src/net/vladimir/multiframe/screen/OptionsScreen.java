package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.modes.dualframe.custom.EnumDualFrameSettings;
import net.vladimir.multiframe.references.References;
import net.vladimir.multiframe.modes.dualframe.custom.DualFrameSettings;
import net.vladimir.multiframe.utils.RenderUtils;

import java.util.HashMap;
import java.util.Map;

public class OptionsScreen implements Screen {

    private final MultiFrame game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private Stage stage;
    private Skin skin;

    private HashMap<EnumDualFrameSettings, TextField> options = new HashMap<EnumDualFrameSettings, TextField>();

    public OptionsScreen(MultiFrame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(References.MENU_WIDTH,  References.MENU_HEIGHT), batch);
        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        options.clear();

        Table table = new Table();
        Table optionsTable = new Table();

//        table.setDebug(true);
//        optionsTable.setDebug(true);

        addNumberOption(optionsTable, EnumDualFrameSettings.PLAYER_SWITCH, DualFrameSettings.PLAYER_SWITCH);
        addNumberOption(optionsTable, EnumDualFrameSettings.PLAYER_SPEED, DualFrameSettings.PLAYER_SPEED);
        addNumberOption(optionsTable, EnumDualFrameSettings.PLAYER_Y, DualFrameSettings.PLAYER_Y);
        addNumberOption(optionsTable, EnumDualFrameSettings.OBSTACLE_SWITCH, DualFrameSettings.OBSTACLE_SWITCH);
        addNumberOption(optionsTable, EnumDualFrameSettings.OBSTACLE_HEIGHT, DualFrameSettings.OBSTACLE_HEIGHT);
        addNumberOption(optionsTable, EnumDualFrameSettings.OBSTACLE_GAP, DualFrameSettings.OBSTACLE_GAP);
        addNumberOption(optionsTable, EnumDualFrameSettings.OBSTACLE_DISTANCE, DualFrameSettings.OBSTACLE_DISTANCE);
        addNumberOption(optionsTable, EnumDualFrameSettings.OBSTACLE_SPEED, DualFrameSettings.OBSTACLE_SPEED);
        addNumberOption(optionsTable, EnumDualFrameSettings.WALL_WIDTH, DualFrameSettings.WALL_WIDTH);

        TextButton bMenu = new TextButton("Return To Menu", skin, "default");
        bMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });

        TextButton bReset = new TextButton("Reset Defaults", skin, "default");
        bReset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for(Map.Entry<EnumDualFrameSettings, TextField> entry : options.entrySet()){
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

    private void addNumberOption(Table table, final EnumDualFrameSettings setting, final int value){
        final Label lText = new Label(setting.getName(), skin, "default");

        final TextField fValue = new TextField(String.valueOf(value), skin);
        fValue.setAlignment(Align.center);
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
                setOption(setting, decreaseValue(DualFrameSettings.get(setting), setting.getStep(), setting.getMin()));
            }
        });

        TextButton bIncrease = new TextButton("+", skin, "default");
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

        table.add(lText).width(450).expandX();
        table.add(bDecrease).size(50, 50).pad(5, 10, 5, 10);
        table.add(fValue).size(120, 60).pad(5, 0, 5, 0);
        table.add(bIncrease).size(50, 50).pad(5, 10, 5, 10);
        table.add(bReset).size(140, 60).pad(5);
        table.row();

        options.put(setting, fValue);
    }

    public void setOption(EnumDualFrameSettings setting, int value){
        if(options.containsKey(setting)){
            options.get(setting).setText(String.valueOf(value));
            DualFrameSettings.set(setting, value, this);
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
