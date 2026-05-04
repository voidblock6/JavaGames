package net.voidblock.numerical_high;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameUtils {

    public static ImageButton createButton(Texture tex) {
        //make a texture into a texture region
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(tex));
        //make the imagebutton a drawable
        return new ImageButton(drawable);
    }

}
