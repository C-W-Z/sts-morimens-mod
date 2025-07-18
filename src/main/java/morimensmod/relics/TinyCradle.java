package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterExalt;

public class TinyCradle extends AbstractEasyRelic implements OnAfterExalt {
    public static final String ID = makeID(TinyCradle.class.getSimpleName());

    private static final int PER_EXALT = 5;
    private static final int ALIEMUS = 100;

    public TinyCradle() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, AWAKENER_COLOR);
        counter = 0;
    }

    @Override
    public void onAfterExalt(AbstractAwakener awaker, int exhaustAliemus, boolean overExalt) {
        counter++;
        if (counter < PER_EXALT)
            return;
        counter -= PER_EXALT;
        flash();
        addToBot(new AliemusChangeAction(awaker, ALIEMUS));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], PER_EXALT, ALIEMUS);
    }
}
