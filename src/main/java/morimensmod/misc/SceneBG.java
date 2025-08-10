package morimensmod.misc;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeUIPath;

public class SceneBG {

    public enum Image {
        Random,
        CourtYard,
        WeeklyDream02,
        WeeklyDream03,
        Woods,
        WoodsNight,
        YardNight,
        YardRedMoon,
    }

    private static final Image[] VALUES = Image.values();

    public static final Image DEFAULT = Image.Random;
    protected static Image currentImage;
    protected static Texture texture;

    static {
        setBG(DEFAULT);
    }

    public static Image getRandomImage() {
        return VALUES[AbstractDungeon.miscRng.random(1, VALUES.length - 1)];
    }

    public static Texture getBGTexture() {
        if (currentImage == Image.Random)
            setRandomBG();
        return texture;
    }

    public static void setBG(Image image) {
        currentImage = image;
        texture = TexLoader.getTexture(makeUIPath("scenebg/" + image.name() + ".png"));
    }

    public static void setRandomBG() {
        setBG(getRandomImage());
    }
}
