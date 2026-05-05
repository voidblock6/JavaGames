package net.voidblock.numerical_high;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MainMenuScreen implements Screen {
    private final NumericalHigh game;
    private Texture titlescreenTexture, playButtonTexture, inventoryButtonTexture, quitButtonTexture, optionsButtonTexture;
    private Stage stage;

    public MainMenuScreen(final NumericalHigh game) {
        this.game = game;
        stage = new Stage(new FitViewport(480, 270));

        titlescreenTexture = GameUtils.createTexture("titlescreen.png");
        playButtonTexture = GameUtils.createTexture("play_button.png");
        inventoryButtonTexture = GameUtils.createTexture("inventory_button.png");
        optionsButtonTexture = GameUtils.createTexture("options_button.png");
        quitButtonTexture = GameUtils.createTexture("quit_button.png");


        ImageButton playButton = GameUtils.createButton(playButtonTexture);
        ImageButton inventoryButton = GameUtils.createButton(inventoryButtonTexture);
        ImageButton optionsButton = GameUtils.createButton(optionsButtonTexture);
        ImageButton quitButton = GameUtils.createButton(quitButtonTexture);


        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
            }
        });

        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game));
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsScreen(game));
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Image background = new Image(titlescreenTexture);
        background.setSize(480, 270);
        stage.addActor(background);

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(playButton).padBottom(10).padTop(70);
        table.row();
        table.add(inventoryButton).padBottom(10);
        table.row();
        table.add(optionsButton).padBottom(10);
        table.row();
        table.add(quitButton);

        stage.addActor(table);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        titlescreenTexture.dispose();
        playButtonTexture.dispose();
        inventoryButtonTexture.dispose();
        quitButtonTexture.dispose();
        optionsButtonTexture.dispose();
    }


    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
