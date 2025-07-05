package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.characters.AbstractAwakener;

public class ElationPower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(ElationPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int AMPLIFY_PERCENT = 50;
    private static final int REDUCE_PER_TURN = 1;

    public ElationPower(AbstractCreature owner, int turns) {
        super(POWER_ID, NAME, PowerType.BUFF, true, owner, turns);
    }

    @Override
    public void onInitialApplication() {
        AbstractAwakener.baseDamageAmplify += AMPLIFY_PERCENT;
        AbstractAwakener.basePoisonAmplify += AMPLIFY_PERCENT;
        AbstractAwakener.baseCounterAmplify += AMPLIFY_PERCENT;
    }

    @Override
    public void onRemove() {
        AbstractAwakener.baseDamageAmplify -= AMPLIFY_PERCENT;
        AbstractAwakener.basePoisonAmplify -= AMPLIFY_PERCENT;
        AbstractAwakener.baseCounterAmplify -= AMPLIFY_PERCENT;
    }

    @Override
    public void atStartOfTurn() {
        addToTop(new ReducePowerAction(owner, owner, this, REDUCE_PER_TURN));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], AMPLIFY_PERCENT, REDUCE_PER_TURN);
    }
}
