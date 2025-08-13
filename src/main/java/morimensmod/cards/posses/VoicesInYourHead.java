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

public class VoicesInYourHead extends AbstractPosse {

    public final static String ID = makeID(VoicesInYourHead.class.getSimpleName());

    public VoicesInYourHead() {
        super(ID);
        posseIndex = 0;
        // only for display
        magicNumber = baseMagicNumber = 1; // debuff amount
        secondMagic = baseSecondMagic = 1; // steal STR
    }

    @Override
    public void activate() {
        addToBot(new AllEnemyApplyPowerAction(awaker, magicNumber, m -> new WeakPower(m, magicNumber, false)));
        addToBot(new AllEnemyApplyPowerAction(awaker, magicNumber, m -> new VulnerablePower(m, magicNumber, false)));
        addToBot(new AllEnemyApplyPowerAction(awaker, -secondMagic, m -> new StrengthPower(m, -secondMagic)));
        addToBot(new AllEnemyApplyPowerAction(awaker, secondMagic, m -> new GainStrengthPower(m, secondMagic)));

        int n_monsters = (int) AbstractDungeon.getMonsters().monsters.stream().filter((m) -> {
            return !m.isDeadOrEscaped();
        }).count();
        applyToSelf(new LoseStrengthPower(awaker, secondMagic * n_monsters));
        applyToSelf(new StrengthPower(awaker, secondMagic * n_monsters));
    }

    @Override
    public String getUIDescription() {
        return String.format(cardStrings.EXTENDED_DESCRIPTION[0], magicNumber, secondMagic);
    }
}
