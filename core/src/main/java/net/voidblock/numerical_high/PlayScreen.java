package net.voidblock.numerical_high;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

public class PlayScreen implements Screen {
    private final NumericalHigh game;
    private Stage stage;
    private Texture exitbutton, gamebackground;
    private Image shieldCard, revertCard;
    private BitmapFont customFont;

    private int score = 1024;
    private String inputNumber = "";
    private final int MAX_LENGTH = 7;
    private int numberOfGuesses;
    private boolean isWaiting = false;
    public int hasShield;
    private int randomNumber;
    private Random rand = new Random();
    private int shieldPlayed;

    private Label scoreLabel;
    private Label inputLabel;
    private ImageButton exitButton;

    public PlayScreen(final NumericalHigh game) {
        this.game = game;

        Table playerInventoryTable = new Table();
        playerInventoryTable.bottom();
        playerInventoryTable.setFillParent(true);


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("PixelOperator.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        parameter.color = Color.WHITE;
        customFont = generator.generateFont(parameter);
        generator.dispose();

        exitbutton = GameUtils.createTexture("return_button.png");
        gamebackground = GameUtils.createTexture("game_bg.png");
        shieldCard =  GameUtils.createCard("shield_upgrade_card.png");
        revertCard = GameUtils.createCard("revert_upgrade_card.png");


        playerInventoryTable.add(revertCard).size(44, 63).padRight(10);
        playerInventoryTable.add(shieldCard).size(44, 63);



        stage = new Stage(new FitViewport(480, 270));

        Image bg = new Image(gamebackground);
        bg.setSize(480, 270);
        stage.addActor(bg);

        scoreLabel = new Label("1024", new Label.LabelStyle(customFont, Color.WHITE));
        scoreLabel.setFontScale(0.35f);
        // This positions it 20 pixels from the left and 20 pixels from the top
        scoreLabel.setPosition(10, 270 - 45);
        stage.addActor(scoreLabel);

        inputLabel = new Label("", new Label.LabelStyle(customFont, Color.WHITE));
        inputLabel.setFontScale(0.5f);
        inputLabel.setSize(480, 270);
        inputLabel.setAlignment(Align.center);
        stage.addActor(inputLabel);

        exitButton = GameUtils.createButton(exitbutton);

        exitButton.setSize(24, 24);
        exitButton.getImage().setFillParent(true);
        exitButton.setPosition(480 - 28, 270 - 28);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(exitButton);

        stage.addActor(playerInventoryTable);


        shieldCard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (hasShield > 0) {
                    shieldPlayed += 1;
                    hasShield -= 1;
                    shieldCard.setVisible(false);
                }
            }
        });
        hasShield = 1;
        startNewRound();
    }

    private void startNewRound() {
        inputNumber = "";
        randomNumber = rand.nextInt(101);
        numberOfGuesses = 0;
        score = 1024;
        isWaiting = false;
        shieldPlayed = 0;
        if (hasShield > 0) shieldCard.setVisible(true);
        updateLabels();
    }

    private void updateLabels() {
        scoreLabel.setText(String.format("%04d", score));
        inputLabel.setText(inputNumber);
    }

    private void processGuess() {
        try {
            int guessedNumber = Integer.parseInt(inputNumber);
            numberOfGuesses += 1;

            if (numberOfGuesses != 1) {
                if (shieldPlayed > 0) {
                    shieldPlayed -= 1;
                } else {
                    score /= 2;
                }
            }

            isWaiting = true;

            if (guessedNumber == randomNumber) {
                inputNumber = "correct";
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        startNewRound();
                    }
                }, 1);
            } else {
                inputNumber = (guessedNumber < randomNumber) ? "higher" : "lower";
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        inputNumber = "";
                        isWaiting = false;
                        updateLabels();
                    }
                }, 1);
            }
            updateLabels();
        } catch (NumberFormatException e) {
            isWaiting = false;
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        stage.addListener(new ClickListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (isWaiting) return false;
                if (character == '\r' || character == '\n') {
                    if (!inputNumber.isEmpty()) processGuess();
                    return true;
                }
                if (character == '\b' || character == '\u007f') {
                    if (inputNumber.length() > 0) {
                        inputNumber = inputNumber.substring(0, inputNumber.length() - 1);
                        updateLabels();
                    }
                    return true;
                }
                if (Character.isDigit(character) && inputNumber.length() < MAX_LENGTH) {
                    inputNumber += character;
                    updateLabels();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { Gdx.input.setInputProcessor(null); }
    @Override public void dispose() { stage.dispose(); }
}

