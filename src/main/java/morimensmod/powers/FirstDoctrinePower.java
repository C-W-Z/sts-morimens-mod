package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FirstDoctrinePower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(FirstDoctrinePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int maxUsePerTurn = 3;

    public FirstDoctrinePower(AbstractCreature owner, int maxUsePerTurn) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, 1);
        this.maxUsePerTurn = maxUsePerTurn;

        isTwoAmount = true;
        amount2 = maxUsePerTurn; // remainUsesThisTurn

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        amount2 = maxUsePerTurn;
        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (amount2 <= 0)
            return;
        flash();
        addToBot(new GainEnergyAction(amount));
        amount2--;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + maxUsePerTurn + DESCRIPTIONS[2] + amount2 + DESCRIPTIONS[3];
    }
}
