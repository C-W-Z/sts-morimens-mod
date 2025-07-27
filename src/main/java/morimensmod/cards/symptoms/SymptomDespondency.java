package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;

public class SymptomDespondency extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomDespondency.class.getSimpleName());

    public SymptomDespondency() {
        super(ID, 1, CardTarget.SELF);
        magicNumber = baseMagicNumber = 10;
        aliemus = baseAliemus = 5;
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new AliemusChangeAction(p(), -aliemus));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, null, magicNumber));
    }
}
