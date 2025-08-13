package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SymptomShock extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomShock.class.getSimpleName());

    public SymptomShock() {
        super(ID, 1, CardTarget.SELF);
        sortIndex = 11;
        magicNumber = baseMagicNumber = 4;
        secondMagic = baseSecondMagic = 2;
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new ApplyPowerAction(p(), null, new StrengthPower(p(), -secondMagic)));
        addToBot(new ApplyPowerAction(p(), null, new GainStrengthPower(p(), secondMagic)));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p(), null, new StrengthPower(p(), magicNumber)));
        addToBot(new ApplyPowerAction(p(), null, new LoseStrengthPower(p(), magicNumber)));
    }
}
