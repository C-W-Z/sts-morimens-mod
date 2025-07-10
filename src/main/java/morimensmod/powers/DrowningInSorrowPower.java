package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.getPowerAmount;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterReceivePower;

public class DrowningInSorrowPower extends AbstractEasyPower implements OnAfterReceivePower {

    public final static String POWER_ID = makeID(DrowningInSorrowPower.class.getSimpleName());
    private static PowerStrings powerStrings;
    private static String[] DESCRIPTIONS;

    /**
     * PoisonPowerPatch會在CardCrawlGame.languagePack準備好之前就調用DrowningInSorrowPower.POWER_ID，導致static
     * initialize時出錯，因此必須延後載入
     *
     * @return PowerStrings
     */
    public static PowerStrings getStrings() {
        if (powerStrings == null) {
            powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
            DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        }
        return powerStrings;
    }

    public static final int POISON_ALIEMUS_SCALE_PER_AMOUNT = 4;
    public static final int POISON_PER_AMOUNT = 2;
    int poison;

    public DrowningInSorrowPower(AbstractCreature owner, int amount) {
        super(POWER_ID, getStrings().NAME, PowerType.BUFF, false, owner, amount);
        priority = 4;
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
    public void atStartOfTurn() {
        flash();
        applyToSelf(new PoisonPower(owner, owner, poison));
        addToBot(new AllEnemyApplyPowerAction(owner, poison, m -> new PoisonPower(m, owner, poison)));
        actB(() -> {
            if (!(owner instanceof AbstractAwakener))
                return;
            int poisonAmount = getPowerAmount(owner, PoisonPower.POWER_ID);
            int aliemusAmplify = 100 + AbstractAwakener.baseAliemusAmplify;
            int aliemus = MathUtils.ceil(poisonAmount * POISON_ALIEMUS_SCALE_PER_AMOUNT * amount * aliemusAmplify / 100F);
            addToTop(new AliemusChangeAction((AbstractPlayer) owner, aliemus));
        });
    }

    private void applyPowers() {
        int poisonAmplify = 100 + AbstractAwakener.basePoisonAmplify;
        poison = MathUtils.ceil(amount * POISON_PER_AMOUNT * poisonAmplify / 100F);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], poison, POISON_ALIEMUS_SCALE_PER_AMOUNT * amount);
    }
}
