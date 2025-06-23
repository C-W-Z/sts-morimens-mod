package morimensmod.savables;

import basemod.abstracts.CustomSavable;
import morimensmod.characters.AbstractAwakener;
import basemod.Pair;

import static morimensmod.MorimensMod.makeID;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.reflect.TypeToken;

public class SavePersistentPowers implements CustomSavable<ArrayList<Pair<String, Integer>>> {

    private static final Logger logger = LogManager.getLogger(SavePersistentPowers.class);

    public static final String ID = makeID(SavePersistentPowers.class.getSimpleName());

    @Override
    public Type savedType() {
        return new TypeToken<ArrayList<Pair<String, Integer>>>() {
        }.getType();
    }

    @Override
    public ArrayList<Pair<String, Integer>> onSave() {
        logger.debug("onSave");

        for (Pair<String, Integer> pair : AbstractAwakener.persistentPowers)
            logger.debug("onSave, ID: " + pair.getKey() + ", amount: " + pair.getValue());

        return AbstractAwakener.persistentPowers;
    }

    @Override
    public void onLoad(ArrayList<Pair<String, Integer>> ID_Amount_Pairs) {
        if (ID_Amount_Pairs == null)
            return;

        logger.debug("onLoad");

        for (Pair<String, Integer> pair : ID_Amount_Pairs)
            logger.debug("onLoad, ID: " + pair.getKey() + ", amount: " + pair.getValue());

        AbstractAwakener.persistentPowers = ID_Amount_Pairs;
    }
}
