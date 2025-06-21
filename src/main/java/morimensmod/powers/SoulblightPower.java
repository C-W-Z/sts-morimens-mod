package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterReceivePower;

public class SoulblightPower extends AbstractEasyPower implements OnAfterReceivePower {

    public final static String POWER_ID = makeID(SoulblightPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int heal;
    private int aliemus;
    private static final int invokePoisonPercentPerRound = 50;

    public SoulblightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        applyPowers();
    }

    public void onAfterReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        applyPowers();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        applyPowers();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        applyPowers();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer)
            return;
        flash();
        addToBot(new HealAction(owner, owner, heal));
        addToBot(new AliemusChangeAction((AbstractAwakener) owner, aliemus));
    }

    private void applyPowers() {
        int healAmplify = 100 + AbstractAwakener.baseHealAmplify;
        heal = MathUtils.ceil(amount * healAmplify / 100F);
        if (p() instanceof AbstractAwakener) {
            int aliemusAmplify = 100 + AbstractAwakener.baseAliemusAmplify;
            aliemus = MathUtils.ceil(((AbstractAwakener) p()).aliemusRegen * aliemusAmplify / 100F);
        }
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + heal + DESCRIPTIONS[1] + aliemus + DESCRIPTIONS[2] + invokePoisonPercentPerRound + DESCRIPTIONS[3];
    }
}
