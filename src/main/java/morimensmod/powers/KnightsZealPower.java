package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.isCommandCard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class KnightsZealPower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(KnightsZealPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int nCardsToGainStrength;

    public KnightsZealPower(AbstractCreature owner, int amount, int nCardsToGainStrength) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        this.nCardsToGainStrength = nCardsToGainStrength;
        isTwoAmount = true;
        amount2 = nCardsToGainStrength;
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!isCommandCard(card))
            return;
        amount2--;
        if (amount2 > 0) {
            updateDescription();
            return;
        }
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
        amount2 = nCardsToGainStrength;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + nCardsToGainStrength + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + amount2 + DESCRIPTIONS[3];
    }
}
