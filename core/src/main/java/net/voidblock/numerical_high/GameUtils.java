package net.voidblock.numerical_high;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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



}
