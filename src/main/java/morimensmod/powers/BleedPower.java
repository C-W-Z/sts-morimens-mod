package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class BleedPower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(BleedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BleedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.DEBUFF, false, owner, amount);
    }

    @Override
    public int onHeal(int healAmount) {
        addToTop(new ReducePowerAction(owner, owner, this, 2 * healAmount));
        return super.onHeal(healAmount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer != owner.isPlayer)
            return;
        flash();
        addToBot(new DamageAction(owner, new DamageInfo(null, amount, DamageType.THORNS)));
        // addToBot(new LoseHPAction(owner, null, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }
}
