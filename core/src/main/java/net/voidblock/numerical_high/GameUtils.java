package net.voidblock.numerical_high;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.table;


public class GameUtils {

    //make a new button
    public static ImageButton createButton(Texture texture) {
        //make a texture into a texture region
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        //make the imagebutton a drawable
        return new ImageButton(drawable);



    }


    //make new textures and add a filter
    public static Texture createTexture(String path) {
        Texture texture = new Texture(path);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        return texture;
    }


    public static Image createCard(String path) {
        Texture texture = createTexture(path);

        Image cardImage = new Image(texture);
        cardImage.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) cardImage.setScale(1.1f);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) cardImage.setScale(1.0f);
            }
        });

        return cardImage;
    }

    public void buyUpgrade(Table table, String path) {
        Image newCard = GameUtils.createCard(path);
        table.add(newCard).pad(5);
        if (table.getCells().size % 7 == 0) {
            table.row();
        }
    }

    public static ImageButton HoverEffects(ImageButton image) {
        image.setTransform(true); // needed to scale
        image.setOrigin(image.getWidth() / 2f, image.getHeight() / 2f); //makes it scale from the middle


        image.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) image.setScale(1.1f);

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) image.setScale(1.0f);
            }

        });

        return image;
    }

    public static ImageButton glideEffects(final ImageButton image, Table table) {
        // chose unqiue movement distance and speed per button
        final float distance = 3f + (float)Math.random() * 5f;
        final float speed = 0.9f + (float)Math.random() * 0.4f;


        // start the first time
        startNewGlide(image, distance, speed);

        image.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    // stop movement upon hover
                    Object action = image.getUserObject();
                    if (action instanceof com.badlogic.gdx.scenes.scene2d.Action) {
                        image.removeAction((com.badlogic.gdx.scenes.scene2d.Action) action);
                        image.setUserObject(null);
                    }

                    //return to original posistion
                    float targetX = table.getCell(image).getActorX();
                    float targetY = table.getCell(image).getActorY();
                    image.addAction(Actions.moveTo(targetX, targetY, 0.1f, com.badlogic.gdx.math.Interpolation.smooth));

                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    //make a new loop upon hovering stopping
                    startNewGlide(image, distance, speed);
                }
            }
        });

        return image;
    }

    private static void startNewGlide(ImageButton image, float distance, float speed) {
        //repeat the sequence of moving relative to the starting possition
        com.badlogic.gdx.scenes.scene2d.Action loop = Actions.forever(Actions.sequence(
            Actions.moveBy(distance, 0, speed, com.badlogic.gdx.math.Interpolation.smooth),
            Actions.moveBy(-distance, 0, speed, com.badlogic.gdx.math.Interpolation.smooth)
        ));

        image.addAction(loop);
        image.setUserObject(loop);
    }




    //methods to apply methods

    public static ImageButton createMainMenuButton(Texture texture, Table table) {
        ImageButton button = createButton(texture);
        button = HoverEffects(button);
        return glideEffects(button, table);
    }


}







