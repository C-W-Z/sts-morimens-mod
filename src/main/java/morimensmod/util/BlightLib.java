package morimensmod.util;

import static morimensmod.MorimensMod.modID;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import basemod.AutoAdd;
import morimensmod.blights.AbstractMorimensBlight;

public class BlightLib {

    private static Logger logger = LogManager.getLogger(BlightLib.class);

    public static ArrayList<AbstractMorimensBlight> blights = new ArrayList<>();

    static {
        new AutoAdd(modID)
                .packageFilter(AbstractMorimensBlight.class)
                .any(AbstractMorimensBlight.class, (info, b) -> blights.add(b));
    }

    public static AbstractBlight getBlight(String ID) {
        try {
            for (AbstractBlight b : blights)
                if (b.blightID.equals(ID))
                    return b.getClass().getConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException
                | NoSuchMethodException | InvocationTargetException e) {
            logger.fatal("BaseMod failed to auto-generate getBlight for blight: " + ID);
        }
        logger.error("Blight NOT FOUND: " + ID);
        return null;
    }

    public static void addBlight(AbstractBlight blight) {
        logger.info("add Blight: " + blight.blightID);
        AbstractDungeon.effectsQueue.add(new AbstractGameEffect() {
            @Override
            public void update() {
                this.isDone = AbstractDungeon.player.hasBlight(blight.blightID);
                if (!this.isDone && AbstractDungeon.getCurrRoom() != null) {
                    AbstractDungeon.getCurrRoom().spawnBlightAndObtain(
                            Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, blight);
                    this.isDone = true;
                }
            }

            @Override
            public void render(SpriteBatch sb) {}

            @Override
            public void dispose() {}
        });
    }
}
