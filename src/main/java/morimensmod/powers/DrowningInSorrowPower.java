package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.getPowerAmount;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PoisonPower;

import morimensmod.actions.AliemusChangeAction;

public class DrowningInSorrowPower extends AbstractEasyPower {

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

    private static final int POISON_ALIEMUS_SCALE = 4;

    public DrowningInSorrowPower(AbstractCreature owner, int amount) {
        super(POWER_ID, getStrings().NAME, PowerType.BUFF, false, owner, amount);
        priority = 4;
    }

    @Override
    public void atStartOfTurn() {
        flash();
        applyToSelf(new PoisonPower(owner, owner, amount));
        addToBot(new AllEnemyApplyPowerAction(owner, amount, m -> new PoisonPower(m, owner, amount)));
        actB(() -> {
            if (owner instanceof AbstractPlayer) {
                int poisonAmount = getPowerAmount(owner, PoisonPower.POWER_ID);
                addToTop(new AliemusChangeAction((AbstractPlayer) owner, poisonAmount * POISON_ALIEMUS_SCALE));
            }
        });
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + POISON_ALIEMUS_SCALE + DESCRIPTIONS[2];
    }
}
