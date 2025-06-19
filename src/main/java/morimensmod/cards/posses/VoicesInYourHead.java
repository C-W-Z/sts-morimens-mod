package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
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
        atb(new AllEnemyApplyPowerAction(p(), DEBUFF_AMOUNT, (mo) -> new WeakPower(mo, DEBUFF_AMOUNT, false)));
        atb(new AllEnemyApplyPowerAction(p(), DEBUFF_AMOUNT, (mo) -> new VulnerablePower(mo, DEBUFF_AMOUNT, false)));
        atb(new AllEnemyApplyPowerAction(p(), -STEAL_STR, (mo) -> new StrengthPower(mo, -STEAL_STR)));
        atb(new AllEnemyApplyPowerAction(p(), STEAL_STR, (mo) -> new GainStrengthPower(mo, STEAL_STR)));
        applyToSelf(new LoseStrengthPower(p(), STEAL_STR));
        applyToSelf(new StrengthPower(p(), STEAL_STR));
    }
}
