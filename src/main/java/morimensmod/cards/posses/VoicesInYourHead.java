package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class VoicesInYourHead extends AbstractPosse {

    public final static String ID = makeID(VoicesInYourHead.class.getSimpleName());

    // for register to CardLibrary
    public VoicesInYourHead() {
        this(null, PosseType.UNLIMITED);
    }

    public VoicesInYourHead(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    private static final int DEBUFF_AMOUNT = 1;
    private static final int STEAL_STR = 1;

    @Override
    public void activate() {
        addToBot(new AllEnemyApplyPowerAction(awaker, DEBUFF_AMOUNT, m -> new WeakPower(m, DEBUFF_AMOUNT, false)));
        addToBot(new AllEnemyApplyPowerAction(awaker, DEBUFF_AMOUNT, m -> new VulnerablePower(m, DEBUFF_AMOUNT, false)));
        addToBot(new AllEnemyApplyPowerAction(awaker, -STEAL_STR, m -> new StrengthPower(m, -STEAL_STR)));
        addToBot(new AllEnemyApplyPowerAction(awaker, STEAL_STR, m -> new GainStrengthPower(m, STEAL_STR)));

        int n_monsters = (int) AbstractDungeon.getMonsters().monsters.stream().filter((m) -> {
            return !m.isDeadOrEscaped();
        }).count();
        applyToSelf(new LoseStrengthPower(awaker, STEAL_STR * n_monsters));
        applyToSelf(new StrengthPower(awaker, STEAL_STR * n_monsters));
    }
}
