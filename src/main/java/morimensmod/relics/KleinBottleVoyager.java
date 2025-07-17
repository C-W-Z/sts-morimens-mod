package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.posses.AbstractPosse;
import morimensmod.config.ModSettings;
import morimensmod.interfaces.OnAfterPosse;
import morimensmod.misc.PosseType;

public class KleinBottleVoyager extends AbstractEasyRelic implements OnAfterPosse {
    public static final String ID = makeID(KleinBottleVoyager.class.getSimpleName());

    private static final int TMP_STR = 1;

    public KleinBottleVoyager() {
        super(ID, RelicTier.COMMON, LandingSound.SOLID, ModSettings.AWAKENER_CARD_COLORS);
    }

    @Override
    public void onAfterPosse(AbstractPosse posse, int exhaustKeyflare) {
        if (posse.getType() == PosseType.TMP)
            return;
        flash();
        applyToSelf(new StrengthPower(p(), TMP_STR));
        applyToSelf(new LoseStrengthPower(p(), TMP_STR));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], TMP_STR);
    }
}
