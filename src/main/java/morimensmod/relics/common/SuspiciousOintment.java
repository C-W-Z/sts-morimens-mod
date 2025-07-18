package morimensmod.relics.common;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.powers.PoisonPower;

import morimensmod.relics.AbstractEasyRelic;

public class SuspiciousOintment extends AbstractEasyRelic {
    public static final String ID = makeID(SuspiciousOintment.class.getSimpleName());

    private static final int POISON_PER_CARD = 1;

    public SuspiciousOintment() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void onPlayerEndTurn() {
        int poison = hand().size() * POISON_PER_CARD;
        flash();
        addToBot(new AllEnemyApplyPowerAction(p(), poison, (mo) -> new PoisonPower(mo, p(), poison)));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], POISON_PER_CARD);
    }
}
