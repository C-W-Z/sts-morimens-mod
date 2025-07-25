package morimensmod.relics.common;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.p;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.relics.AbstractEasyRelic;

public class LaurelCufflinks extends AbstractEasyRelic {
    public static final String ID = makeID(LaurelCufflinks.class.getSimpleName());

    private static final int ALIEMUS = 10;

    public LaurelCufflinks() {
        super(ID, RelicTier.COMMON, LandingSound.SOLID, AWAKENER_COLOR);
    }

    @Override
    public void atTurnStart() {
        flash();
        addToBot(new AliemusChangeAction(p(), ALIEMUS));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], ALIEMUS);
    }
}
