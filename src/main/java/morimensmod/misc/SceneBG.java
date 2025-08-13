package morimensmod.misc;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeUIPath;

public class SceneBG {

    public enum Image {
        Random,
        RandomBoss,
        /* BOSS BG */
        WeeklyDream02,
        WeeklyDream03,
        YardRedMoon,
        /* NORMAL BG */
        CourtYard,
        Woods,
        WoodsNight,
        YardNight,
    }

    private static final Image[] VALUES = Image.values();

    public static final Image DEFAULT = Image.Random;
    protected static Image currentImage;
    protected static Texture texture;

    static {
        setBG(DEFAULT);
    }

    public static Image getRandomImage(boolean isBoss) {
        if (isBoss)
            return VALUES[AbstractDungeon.miscRng.random(2, 4)];
        return VALUES[AbstractDungeon.miscRng.random(5, VALUES.length - 1)];
    }

    public static Texture getBGTexture() {
        if (currentImage == Image.Random)
            setRandomBG(false);
        else if (currentImage == Image.RandomBoss)
            setRandomBG(true);
        return texture;
    }

    public static void setBG(Image image) {
        currentImage = image;
        texture = TexLoader.getTexture(makeUIPath("scenebg/" + image.name() + ".png"));
    }

    public static void setRandomBG(boolean isBoss) {
        setBG(getRandomImage(isBoss));
    }
}
