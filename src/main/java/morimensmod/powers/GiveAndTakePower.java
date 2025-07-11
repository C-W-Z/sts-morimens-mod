package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.ThornsPower;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnPowerModified;

public class GiveAndTakePower extends AbstractEasyPower implements OnPowerModified {

    public final static String POWER_ID = makeID(GiveAndTakePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int THORNS_PER_AMOUNT = 1;
    int counter;

    public GiveAndTakePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type == DamageType.THORNS)
            return;
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new ThornsPower(owner, counter), counter));
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], counter);
    }

    @Override
    public void onPowerModified() {
        int counterAmplify = 100 + AbstractAwakener.baseCounterAmplify;
        counter = MathUtils.ceil(amount * THORNS_PER_AMOUNT * counterAmplify / 100F);
        updateDescription();
    }
}
