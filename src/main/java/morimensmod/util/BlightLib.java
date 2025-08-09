package morimensmod.util;

import static morimensmod.MorimensMod.modID;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.blights.AbstractBlight;

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
}
