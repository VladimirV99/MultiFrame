package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.modes.GameModes;
import net.vladimir.multiframe.modes.dualframe.custom.DualFrameOptionsScreen;
import net.vladimir.multiframe.references.References;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;

public class GameSelectScreen extends ScreenAdapter {

    private MultiFrame game;
    private AssetManager assetManager;
    private SpriteBatch batch;

    private Stage stage;
    private Skin skin;

    private GameModes gameMode = null;

    private List<Button> buttons;

    private Label lModeTitle;
    private Label lModeDescription;

    private TextButton bEdit;

    public GameSelectScreen(MultiFrame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();

        this.buttons = new ArrayList<Button>();
    }

    @Override
    public void show() {
        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        stage = new Stage(
                new ExtendViewport(References.MENU_WIDTH, References.MENU_HEIGHT, References.MENU_WIDTH_MAX, References.MENU_HEIGHT_MAX),
                batch
        );

        lModeTitle = new Label("", skin, "large_white");
        lModeTitle.setAlignment(Align.center);
        lModeDescription = new Label("", skin, "default_white");
        lModeDescription.setWrap(true);
        bEdit = new TextButton("Edit", skin, "large");

        Table table = new Table();

        Table gameList = new Table(skin);
        gameList.align(Align.top);
        gameList.defaults();

        ButtonGroup<TextButton> gameListGroup = new ButtonGroup<TextButton>();
        gameListGroup.setMinCheckCount(0);

        addGameMode(gameList, gameListGroup, GameModes.DUALFRAME_EASY);
        addGameMode(gameList, gameListGroup, GameModes.DUALFRAME_NORMAL);
        addGameMode(gameList, gameListGroup, GameModes.DUALFRAME_HARD);
        addGameMode(gameList, gameListGroup, GameModes.DUALFRAME_CUSTOM);

        gameList.pack();
        ScrollPane gameListScroll = new ScrollPane(gameList, skin);

        Table selectionTable = new Table(skin);

        TextButton bBack = new TextButton("Back", skin, "large");
        bBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });
        bEdit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new DualFrameOptionsScreen(game));
            }
        });
        bEdit.setVisible(false);
        TextButton bPlay = new TextButton("Play", skin, "large");
        bPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, gameMode.getFrameHandler()));
            }
        });

        selectionTable.defaults().pad(10);
        selectionTable.add(lModeTitle).colspan(3).pad(30, 20, 20, 20).growX().row();
        selectionTable.add(lModeDescription).colspan(3).pad(20, 20, 10, 20).fillX().expandY().align(Align.top).row();
        selectionTable.add(bBack).align(Align.bottomLeft).growX();
        selectionTable.add(bEdit).growX();
        selectionTable.add(bPlay).align(Align.bottomRight).growX();

        selectionTable.pack();

        table.add(gameListScroll).growY();
        table.add(selectionTable).growX().fillY();

        table.setFillParent(true);
        table.pack();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

        selectMode(Settings.getLastSelection());
        if(Settings.getLastSelection()>=0 && Settings.getLastSelection()<buttons.size())
            buttons.get(Settings.getLastSelection()).toggle();
        gameListGroup.setMinCheckCount(1);
    }

    private void addGameMode(Table table, ButtonGroup<TextButton> group, final GameModes mode) {
        final int id = buttons.size();
        final TextButton button = new TextButton(mode.getTitle(), skin, "toggle");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                lModeTitle.setText(mode.getTitle());
                lModeDescription.setText(mode.getDescription());
                switch (mode) {
                    case DUALFRAME_CUSTOM:
                        bEdit.setVisible(true);
                        break;
                    default:
                        bEdit.setVisible(false);
                }
                gameMode = mode;
                selectMode(id);
            }
        });
        group.add(button);
        buttons.add(button);
        table.add(button).size(450, 130).row();
    }

    private void selectMode(int id) {
        if(Settings.getLastSelection() >= 0) {
            if (Settings.getLastSelection() < buttons.size()) {
                Settings.setLastSelection(id);
            } else {
                Settings.setLastSelection(buttons.size()-1);
            }
        }
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
