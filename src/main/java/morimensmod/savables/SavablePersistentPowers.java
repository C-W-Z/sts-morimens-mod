package morimensmod.savables;

import basemod.abstracts.CustomSavable;
import morimensmod.characters.AbstractAwakener;
import morimensmod.powers.PersistentPower;
import basemod.Pair;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SavablePersistentPowers implements CustomSavable<ArrayList<Pair<String, Integer>>> {

    private static final Logger logger = LogManager.getLogger(SavablePersistentPowers.class);

    public static final String ID = makeID(SavablePersistentPowers.class.getSimpleName());

    @Override
    public Type savedType() {
        return new TypeToken<ArrayList<Pair<String, Integer>>>() {
        }.getType();
    }

    @Override
    public ArrayList<Pair<String, Integer>> onSave() {
        ArrayList<Pair<String, Integer>> persistentPowers = new ArrayList<>();

        if (!(p() instanceof AbstractAwakener))
            return persistentPowers;

        for (AbstractPower p : p().powers)
            if (p instanceof PersistentPower) {
                logger.debug("onSave, ID: " + p.ID + ", amount: " + p.amount);
                persistentPowers.add(new Pair<>(p.ID, p.amount));
            }

        return persistentPowers;
    }

    @Override
    public void onLoad(ArrayList<Pair<String, Integer>> ID_Amount_Pairs) {
        if (!(p() instanceof AbstractAwakener))
            return;
        for (Pair<String, Integer> pair : ID_Amount_Pairs) {
            logger.debug("onLoad, ID: " + pair.getKey() + ", amount: " + pair.getValue());
            ((AbstractAwakener) p()).persistentPowers.add(pair);
        }
    }
}
