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

public class SaveAwakenerFloatProperties implements CustomSavable<HashMap<String, Float>> {

    private static final Logger logger = LogManager.getLogger(SaveAwakenerFloatProperties.class);

    public static final String ID = makeID(SaveAwakenerFloatProperties.class.getSimpleName());

    @Override
    public Type savedType() {
        return new TypeToken<HashMap<String, Float>>() {
        }.getType();
    }

    @Override
    public HashMap<String, Float> onSave() {
        logger.debug("onSave, aliemus: " + AbstractAwakener.getAliemus() + ", keyflare: " + AbstractAwakener.getKeyflare());
        HashMap<String, Float> props = new HashMap<>();
        if (!(p() instanceof AbstractAwakener))
            return props;
        AbstractAwakener awaker = (AbstractAwakener) p();
        props.put("deathResistance", awaker.getDeathResistance());
        return props;
    }

    @Override
    public void onLoad(HashMap<String, Float> props) {
        if (!(p() instanceof AbstractAwakener))
            return;
        logger.debug("onLoad, ");
        AbstractAwakener awaker = (AbstractAwakener) p();
        awaker.setDeathResistance(props.get("deathResistance"));
    }

}
