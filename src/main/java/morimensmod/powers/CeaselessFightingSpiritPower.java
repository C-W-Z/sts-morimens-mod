package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class CeaselessFightingSpiritPower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(CeaselessFightingSpiritPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int REDUCE_PER_TURN = 3;

    public CeaselessFightingSpiritPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer != owner.isPlayer)
            return;
        addToBot(new ReducePowerAction(owner, owner, WeakPower.POWER_ID, amount * REDUCE_PER_TURN));
        addToBot(new ReducePowerAction(owner, owner, VulnerablePower.POWER_ID, amount * REDUCE_PER_TURN));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount * REDUCE_PER_TURN);
    }
}
