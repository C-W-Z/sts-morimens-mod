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

import morimensmod.cards.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class VoicesInYourHead extends AbstractPosse {

    public final static String ID = makeID(VoicesInYourHead.class.getSimpleName());

    // for register to CardLibrary
    public VoicesInYourHead() {
        super(ID, null, PosseType.UNLIMITED);
    }

    public VoicesInYourHead(AbstractAwakener p, PosseType type) {
        super(ID, p, type);
    }

    private static final int debuffAmount = 1;
    private static final int stealStrength = 1;

    @Override
    public void activate() {
        atb(new AllEnemyApplyPowerAction(p(), debuffAmount, (mo) -> new WeakPower(mo, debuffAmount, false)));
        atb(new AllEnemyApplyPowerAction(p(), debuffAmount, (mo) -> new VulnerablePower(mo, debuffAmount, false)));
        atb(new AllEnemyApplyPowerAction(p(), -stealStrength, (mo) -> new StrengthPower(mo, -stealStrength)));
        atb(new AllEnemyApplyPowerAction(p(), stealStrength, (mo) -> new GainStrengthPower(mo, stealStrength)));
        applyToSelf(new StrengthPower(p(), stealStrength));
        applyToSelf(new LoseStrengthPower(p(), stealStrength));
    }
}
