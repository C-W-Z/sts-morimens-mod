package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.discardPile;
import static morimensmod.util.Wiz.isCommandCard;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class RewindingTimePower extends AbstractEasyPower {
    public final static String POWER_ID = makeID(RewindingTimePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RewindingTimePower(AbstractCreature owner, int maxUsePerTurn) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, maxUsePerTurn);
        isTwoAmount = true;
        amount2 = amount; // remainUsesThisTurn
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        amount2 = amount;
        updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (amount2 <= 0)
            return;
        if (card.exhaust || card.purgeOnUse || !isCommandCard(card))
            return;
        flash();
        actB(() -> {
            if (discardPile().group.contains(card)) {
                discardPile().moveToHand(card);
                amount2--;
                updateDescription();
            }
        });
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        amount2 += stackAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }
}
