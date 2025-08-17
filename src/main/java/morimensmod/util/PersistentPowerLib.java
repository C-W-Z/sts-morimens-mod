package morimensmod.util;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.core.AbstractCreature;

import morimensmod.powers.AbstractPersistentPower;

public class PersistentPowerLib {

    private static final Logger logger = LogManager.getLogger(PersistentPowerLib.class);

    private static HashMap<String, AbstractPersistentPower> powers = new HashMap<>();

    public static void addPower(AbstractPersistentPower power) {
        logger.debug("Register PersistentPower: " + power.ID);
        powers.put(power.ID, power);
    }

    public static AbstractPersistentPower getPower(String powerID, AbstractCreature owner, int amount) {
        return powers.get(powerID).newPower(owner, amount);
    }
}
