package morimensmod.savables;

import basemod.abstracts.CustomSavable;
import morimensmod.cards.posses.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.CardLib.getAllPosses;
import static morimensmod.util.Wiz.p;

import java.lang.reflect.Type;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.reflect.TypeToken;

public class SaveAwakenerPosse implements CustomSavable<HashMap<String, String>> {

    private static final Logger logger = LogManager.getLogger(SaveAwakenerPosse.class);

    public static final String ID = makeID(SaveAwakenerPosse.class.getSimpleName());

    @Override
    public Type savedType() {
        return new TypeToken<HashMap<String, String>>() {
        }.getType();
    }

    @Override
    public HashMap<String, String> onSave() {
        HashMap<String, String> props = new HashMap<>();
        if (!(p() instanceof AbstractAwakener))
            return props;
        AbstractAwakener awaker = (AbstractAwakener) p();
        logger.debug("onSave, posse: " + awaker.getPosseID());
        props.put("posse", awaker.getPosseID());
        return props;
    }

    @Override
    public void onLoad(HashMap<String, String> props) {
        if (!(p() instanceof AbstractAwakener) || props == null)
            return;
        AbstractAwakener awaker = (AbstractAwakener) p();
        String posseID = props.get("posse");
        logger.debug("onLoad, posse: " + posseID);
        if (posseID != null) {
            for (AbstractPosse p : getAllPosses()) {
                if (p.cardID.equals(posseID)) {
                    awaker.setPosse((AbstractPosse) p.makeCopy());
                    break;
                }
            }
        }
    }
}
