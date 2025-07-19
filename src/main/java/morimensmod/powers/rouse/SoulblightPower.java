package morimensmod.powers.rouse;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnPowerModified;
import morimensmod.powers.AbstractEasyPower;

public class SoulblightPower extends AbstractEasyPower implements OnPowerModified {

    public final static String POWER_ID = makeID(SoulblightPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int heal;
    private int aliemus;
    public static final int HEAL_PER_AMOUNT = 4;
    public static final int INVOKE_POISON_PERCENT = 50;

    public SoulblightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer)
            return;
        flash();
        addToBot(new HealAction(owner, owner, heal));
        addToBot(new AliemusChangeAction((AbstractAwakener) owner, aliemus));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], heal, aliemus, INVOKE_POISON_PERCENT);
    }

    @Override
    public void onPowerModified() {
        int healAmplify = 100 + AbstractAwakener.baseHealAmplify;
        heal = MathUtils.ceil(amount * HEAL_PER_AMOUNT * healAmplify / 100F);
        if (p() instanceof AbstractAwakener) {
            int aliemusAmplify = 100 + AbstractAwakener.baseAliemusAmplify;
            aliemus = MathUtils.ceil(((AbstractAwakener) p()).aliemusRegen * aliemusAmplify / 100F);
        }
        updateDescription();
    }
}
