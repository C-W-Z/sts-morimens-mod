package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class SuspiciousOintment extends AbstractEasyRelic {
    public static final String ID = makeID(SuspiciousOintment.class.getSimpleName());

    private static final int PER_N_HAND = 1;
    private static final int POISON_PER_CARD = 1;

    public SuspiciousOintment() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void onPlayerEndTurn() {
        int poison = hand().size() * POISON_PER_CARD / PER_N_HAND;
        flash();
        addToBot(new AllEnemyApplyPowerAction(p(), poison, (mo) -> new PoisonPower(mo, p(), poison)));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], PER_N_HAND, POISON_PER_CARD);
    }
}
