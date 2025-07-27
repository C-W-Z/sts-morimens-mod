package morimensmod.powers.monster;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.powers.AbstractEasyPower;

public class BloodBarrierPower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(BloodBarrierPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BloodBarrierPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        isTwoAmount = true;
        amount2 = amount;
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        amount2 += stackAmount;
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        checkAndTrigger(reduceAmount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        checkAndTrigger(damageAmount);
        return super.onAttacked(info, damageAmount);
    }

    private void checkAndTrigger(int damage) {
        amount2 -= damage;
        if (amount2 > 0)
            return;
        amount2 = amount;
        addToTop(new GainBlockAction(owner, owner, owner.maxHealth));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount, amount2);
    }
}
