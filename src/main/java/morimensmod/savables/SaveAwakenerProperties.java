package morimensmod.savables;

import basemod.abstracts.CustomSavable;
import morimensmod.characters.AbstractAwakener;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.reflect.TypeToken;

public class SaveAwakenerProperties implements CustomSavable<ArrayList<Integer>> {

    private static final Logger logger = LogManager.getLogger(SaveAwakenerProperties.class);

    public static final String ID = makeID(SaveAwakenerProperties.class.getSimpleName());

    @Override
    public Type savedType() {
        return new TypeToken<ArrayList<Integer>>() {
        }.getType();
    }

    @Override
    public ArrayList<Integer> onSave() {
        logger.debug("onSave, aliemus: " + AbstractAwakener.getAliemus() + ", keyflare: " + AbstractAwakener.getKeyflare());
        ArrayList<Integer> props = new ArrayList<>();
        if (!(p() instanceof AbstractAwakener))
            return props;
        AbstractAwakener awaker = (AbstractAwakener) p();
        props.add(AbstractAwakener.getAliemus());
        props.add(AbstractAwakener.getKeyflare());
        props.add(awaker.baseAliemusRegen);
        props.add(awaker.baseKeyflareRegen);
        return props;
    }

    @Override
    public void onLoad(ArrayList<Integer> props) {
        logger.debug("onLoad, aliemus: " + props.get(0) + ", keyflare: " + props.get(1));
        if (!(p() instanceof AbstractAwakener))
            return;
        AbstractAwakener awaker = (AbstractAwakener) p();
        AbstractAwakener.setAliemus(props.get(0));
        AbstractAwakener.setKeyflare(props.get(1));
        awaker.baseAliemusRegen = props.get(2);
        awaker.baseKeyflareRegen = props.get(3);
    }
}
