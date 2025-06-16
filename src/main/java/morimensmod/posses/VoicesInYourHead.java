package morimensmod.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class VoicesInYourHead extends AbstractPosse {

    public static final String ID = makeID(VoicesInYourHead.class.getSimpleName());
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    private static final int debuffAmount = 1;
    private static final int stealStrength = 1;

    public VoicesInYourHead() {
        super(ID);
    }

    @Override
    public void activate() {
        atb(new AllEnemyApplyPowerAction(p(), debuffAmount, (mo) -> new WeakPower(mo, debuffAmount, false)));
        atb(new AllEnemyApplyPowerAction(p(), debuffAmount, (mo) -> new VulnerablePower(mo, debuffAmount, false)));
        atb(new AllEnemyApplyPowerAction(p(), -stealStrength, (mo) -> new StrengthPower(mo, -stealStrength)));
        atb(new AllEnemyApplyPowerAction(p(), stealStrength, (mo) -> new GainStrengthPower(mo, stealStrength)));
        applyToSelf(new StrengthPower(p(), stealStrength));
        applyToSelf(new LoseStrengthPower(p(), stealStrength));
    }

    @Override
    public String getTitle() {
        return UI_STRINGS.TEXT[0];
    }

    @Override
    public String getDescription() {
        return UI_STRINGS.TEXT[1];
    }
}
