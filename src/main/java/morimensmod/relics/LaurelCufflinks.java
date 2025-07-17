package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import morimensmod.actions.AliemusChangeAction;

public class LaurelCufflinks extends AbstractEasyRelic {
    public static final String ID = makeID(LaurelCufflinks.class.getSimpleName());

    private static final int ALIEMUS = 10;

    public LaurelCufflinks() {
        super(ID, RelicTier.COMMON, LandingSound.SOLID);
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
