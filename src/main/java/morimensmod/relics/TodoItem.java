package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.RealmColorPatch.CardColorPatch.CHAOS_COLOR;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CHAOS_COLOR);
    }
}
