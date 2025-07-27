package morimensmod.util;

import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.events.city.Vampires;

import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils.EventType;
import morimensmod.characters.AbstractAwakener;
import morimensmod.events.Junction;
import morimensmod.events.PoolOfGore;

public class EventLib {

    public static void register() {
        BaseMod.addEvent(new AddEventParams.Builder(Junction.ID, Junction.class)
                .eventType(EventType.NORMAL)
                .create());
        BaseMod.addEvent(new AddEventParams.Builder(PoolOfGore.ID, PoolOfGore.class)
                .bonusCondition(() -> p() instanceof AbstractAwakener)
                .overrideEvent(Vampires.ID)
                .eventType(EventType.OVERRIDE)
                .create());
    }
}
