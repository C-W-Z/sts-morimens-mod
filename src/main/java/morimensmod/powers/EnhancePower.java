package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class EnhancePower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(EnhancePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int DMG_PERCENT = 25;
    public static final int REDUCE_PER_TURN = 1;

    public EnhancePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, true, owner, amount);
        this.priority = 6;
    }

    @Override
    public float atDamageGive(float damage, DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage * (100F + DMG_PERCENT) / 100F;
        return damage;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer)
            return;
        addToBot(new ReducePowerAction(owner, owner, this, REDUCE_PER_TURN));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], DMG_PERCENT, REDUCE_PER_TURN);
    }
}
