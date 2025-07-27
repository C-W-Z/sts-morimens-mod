package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.shuffleIn;
import static morimensmod.util.CardLib.getRandomSymptoms;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SymptomObedience extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomObedience.class.getSimpleName());

    public SymptomObedience() {
        super(ID, 1, CardTarget.NONE);
        magicNumber = baseMagicNumber = 1;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        for (AbstractCard c : getRandomSymptoms(magicNumber))
            shuffleIn(c);
    }
}
