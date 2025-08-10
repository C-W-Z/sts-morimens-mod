package morimensmod.misc;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeUIPath;

public class SceneBG {

    public enum Image {
        CourtYard,
        YardNight,
    }

    private static final Image[] VALUES = Image.values();

    public static Image DEFAULT = Image.CourtYard;
    public static Image currentImage;
    public static Texture texture;

    static {
        setBG(DEFAULT);
    }

    public static void setBG(Image image) {
        currentImage = image;
        texture = TexLoader.getTexture(makeUIPath("scenebg/" + image.name() + ".png"));
    }

    public static void setRandomBG() {
        setBG(VALUES[AbstractDungeon.miscRng.random(VALUES.length)]);
    }
}
