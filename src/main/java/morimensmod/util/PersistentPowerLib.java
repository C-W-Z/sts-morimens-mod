package morimensmod.util;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.core.AbstractCreature;

import morimensmod.powers.PersistentPower;

public class PersistentPowerLib {

    private static final Logger logger = LogManager.getLogger(PersistentPowerLib.class);

    private static HashMap<String, PersistentPower> powers = new HashMap<>();

    public static void addPower(PersistentPower power) {
        logger.debug("Register PersistentPower: " + power.ID, power);
        powers.put(power.ID, power);
    }

    public static PersistentPower getPower(String powerID, AbstractCreature owner, int amount) {
        return powers.get(powerID).newPower(owner, amount);
    }
}
