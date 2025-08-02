package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.actions.PickDrawPileCardsAction;

public class PickFromTop3DrawPileCardsPower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(OnlyUnlimitedPosseTwicePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int TOPN = 3;

    public PickFromTop3DrawPileCardsPower(AbstractCreature owner, int pick) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, pick);
        if (this.amount > TOPN)
            this.amount = TOPN;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount > TOPN)
            this.amount = TOPN;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new PickDrawPileCardsAction(TOPN, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], TOPN, amount);
    }
}
