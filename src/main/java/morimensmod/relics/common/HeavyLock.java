package morimensmod.relics.common;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterExalt;
import morimensmod.relics.AbstractEasyRelic;

public class HeavyLock extends AbstractEasyRelic implements OnAfterExalt {
    public static final String ID = makeID(HeavyLock.class.getSimpleName());

    private static final int TMP_STR = 2;

    public HeavyLock() {
        super(ID, RelicTier.COMMON, LandingSound.HEAVY, AWAKENER_COLOR);
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
