package morimensmod.powers.rouse;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.powers.AbstractEasyPower;

public class FirstDoctrinePower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(FirstDoctrinePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int MAX_USE_PER_TURN = 3;

    public FirstDoctrinePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        isTwoAmount = true;
        amount2 = MAX_USE_PER_TURN; // remainUsesThisTurn
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        amount2 = MAX_USE_PER_TURN;
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (amount2 <= 0)
            return;
        flash();
        addToBot(new GainEnergyAction(amount));
        amount2--;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount, MAX_USE_PER_TURN, amount2);
    }
}
