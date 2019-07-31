package net.vladimir.multiframe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.vladimir.multiframe.MultiFrame;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.modes.GameModes;
import net.vladimir.multiframe.references.References;
import net.vladimir.multiframe.utils.RenderUtils;

public class GameSelectScreen extends ScreenAdapter {

    private MultiFrame game;
    private AssetManager assetManager;
    private SpriteBatch batch;

    private Stage stage;
    private Skin skin;

    private GameModes gameMode = null;

    private Label lModeTitle;
    private Label lModeDescription;

    private TextButton bEdit;

    public GameSelectScreen(MultiFrame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        stage = new Stage(new FitViewport(References.MENU_WIDTH, References.MENU_HEIGHT), batch);

        lModeTitle = new Label("", skin);
        lModeTitle.setAlignment(Align.center);
        lModeDescription = new Label("", skin);
        bEdit = new TextButton("Edit", skin);

        Table table = new Table();

        Table gameList = new Table(skin);
        gameList.align(Align.top);
        gameList.defaults().pad(10);

        ButtonGroup<TextButton> gameListGroup = new ButtonGroup<TextButton>();

        addGameMode(gameList, gameListGroup, GameModes.DUALFRAME_MEDIUM);
        addGameMode(gameList, gameListGroup, GameModes.DUALFRAME_CUSTOM);

        gameList.pack();
        ScrollPane gameListScroll = new ScrollPane(gameList);

        Table selectionTable = new Table(skin);

        TextButton bBack = new TextButton("Back", skin);
        bBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });
        bEdit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new OptionsScreen(game));
            }
        });
        bEdit.setVisible(false);
        TextButton bPlay = new TextButton("Play", skin);
        bPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, gameMode.getFrameHandler()));
            }
        });

        selectionTable.defaults().pad(20);
        selectionTable.add(lModeTitle).colspan(3).pad(30, 20, 20, 20).expandX().fillX().row();
        selectionTable.add(lModeDescription).colspan(3).pad(20, 20, 10, 20).fillX().expandY().align(Align.top).row();
        selectionTable.add(bBack).align(Align.bottomLeft).height(50).expandX().fillX();
        selectionTable.add(bEdit).height(50).expandX().fillX();
        selectionTable.add(bPlay).align(Align.bottomRight).height(50).expandX().fillX();

        selectionTable.pack();

        table.add(gameListScroll).expandY().fillY();
        table.add(selectionTable).expandX().fillX().fillY();

//        selectionTable.setDebug(true);
//        table.setDebug(true);

        table.setFillParent(true);
        table.pack();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    private void addGameMode(Table table, ButtonGroup<TextButton> group, final GameModes mode) {
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
            }
        });
        group.add(button);
        table.add(button).height(100).width(400).row();
    }

    @Override
    public void render(float delta) {
        update(delta);

        RenderUtils.clearScreen();
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
