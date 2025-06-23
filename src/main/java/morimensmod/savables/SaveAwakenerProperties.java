package morimensmod.savables;

import basemod.abstracts.CustomSavable;
import morimensmod.characters.AbstractAwakener;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import java.lang.reflect.Type;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.reflect.TypeToken;

public class SaveAwakenerProperties implements CustomSavable<HashMap<String, Integer>> {

    private static final Logger logger = LogManager.getLogger(SaveAwakenerProperties.class);

    public static final String ID = makeID(SaveAwakenerProperties.class.getSimpleName());

    @Override
    public Type savedType() {
        return new TypeToken<HashMap<String, Integer>>() {
        }.getType();
    }

    @Override
    public HashMap<String, Integer> onSave() {
        logger.debug("onSave, aliemus: " + AbstractAwakener.getAliemus() + ", keyflare: " + AbstractAwakener.getKeyflare());
        HashMap<String, Integer> props = new HashMap<>();
        if (!(p() instanceof AbstractAwakener))
            return props;
        AbstractAwakener awaker = (AbstractAwakener) p();
        props.put("aliemus", AbstractAwakener.getAliemus());
        props.put("keyflare", AbstractAwakener.getKeyflare());
        props.put("baseAliemusRegen", awaker.baseAliemusRegen);
        props.put("baseKeyflareRegen", awaker.baseKeyflareRegen);
        props.put("deathResistanceCount", awaker.getDeathResistanceCount());
        return props;
    }

    @Override
    public void onLoad(HashMap<String, Integer> props) {
        if (!(p() instanceof AbstractAwakener))
            return;
        logger.debug("onLoad, aliemus: " + optionalToInt(props.get("aliemus")) + ", keyflare: " + optionalToInt(props.get("keyflare")));
        AbstractAwakener awaker = (AbstractAwakener) p();
        AbstractAwakener.setAliemus(optionalToInt(props.get("aliemus")));
        AbstractAwakener.setKeyflare(optionalToInt(props.get("keyflare")));
        awaker.baseAliemusRegen = optionalToInt(props.get("baseAliemusRegen"));
        awaker.baseKeyflareRegen = optionalToInt(props.get("baseKeyflareRegen"));
        awaker.setDeathResistanceCount(optionalToInt(props.get("deathResistanceCount")));
    }

    private int optionalToInt(Integer i) {
        return i == null ? 0 : i;
    }
}
