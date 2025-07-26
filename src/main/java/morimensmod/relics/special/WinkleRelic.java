package morimensmod.relics.special;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.p;

import morimensmod.characters.AbstractAwakener;
import morimensmod.relics.AbstractEasyRelic;

public class WinkleRelic extends AbstractEasyRelic {
    public static final String ID = makeID(WinkleRelic.class.getSimpleName());

    public WinkleRelic() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, AWAKENER_COLOR);
    }

    @Override
    public void atBattleStartPreDraw() {
        if (!(p() instanceof AbstractAwakener))
            return;
        AbstractAwakener.updateMaxExaltPerTurn(1);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
