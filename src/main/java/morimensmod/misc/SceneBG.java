package morimensmod.misc;

import com.badlogic.gdx.graphics.Texture;

import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeUIPath;

public class SceneBG {

    public enum Image {
        CourtYard,
        YardNight,
    }

    public static Image currentImage;
    public static Texture texture;

    static {
        setBG(Image.CourtYard);
    }

    public static void setBG(Image image) {
        currentImage = image;
        texture = TexLoader.getTexture(makeUIPath("scenebg/" + image.name() + ".png"));
    }
}
