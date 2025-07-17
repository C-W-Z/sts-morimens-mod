package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.characters.AbstractAwakener;
import morimensmod.config.ModSettings;
import morimensmod.interfaces.OnAfterExalt;

public class HeavyLock extends AbstractEasyRelic implements OnAfterExalt {
    public static final String ID = makeID(HeavyLock.class.getSimpleName());

    private static final int TMP_STR = 2;

    public HeavyLock() {
        super(ID, RelicTier.COMMON, LandingSound.HEAVY, ModSettings.AWAKENER_CARD_COLORS);
    }

    @Override
    public void onAfterExalt(AbstractAwakener awaker, int exhaustAliemus, boolean overExalt) {
        flash();
        applyToSelf(new StrengthPower(p(), TMP_STR));
        applyToSelf(new LoseStrengthPower(p(), TMP_STR));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], TMP_STR);
    }
}
