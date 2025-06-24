package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.powerAmount;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PoisonPower;

import morimensmod.actions.AliemusChangeAction;

public class DrowningInSorrowPower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(DrowningInSorrowPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int POISON_ALIEMUS_SCALE = 4;

    public DrowningInSorrowPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        priority = 4;
    }

    @Override
    public void atStartOfTurn() {
        flash();
        applyToSelf(new PoisonPower(owner, owner, amount));
        addToBot(new AllEnemyApplyPowerAction(owner, amount, m -> new PoisonPower(m, owner, amount)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (owner instanceof AbstractPlayer) {
                    int poisonAmount = powerAmount(owner, PoisonPower.POWER_ID);
                    addToTop(new AliemusChangeAction((AbstractPlayer) owner, poisonAmount * POISON_ALIEMUS_SCALE));
                }
                isDone = true;
            };
        });
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + POISON_ALIEMUS_SCALE + DESCRIPTIONS[2];
    }
}
