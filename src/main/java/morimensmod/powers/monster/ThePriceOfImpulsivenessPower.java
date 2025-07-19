package morimensmod.powers.monster;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.powers.AbstractEasyPower;

public class ThePriceOfImpulsivenessPower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(ThePriceOfImpulsivenessPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ThePriceOfImpulsivenessPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.DEBUFF, false, owner, amount);
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageType.THORNS) {
            addToTop(new ApplyPowerAction(owner, owner, new GainStrengthPower(owner, amount)));
            addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -amount)));
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }
}
