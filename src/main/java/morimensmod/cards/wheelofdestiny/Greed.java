package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class Greed extends AbstractWheelOfDestiny {
    public final static String ID = makeID(Greed.class.getSimpleName());

    public Greed() {
        super(ID, CardRarity.COMMON);
        magicNumber = baseMagicNumber = 250;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(250);
    }

    @Override
    public boolean onRemoveCardFromDeck(AbstractCard card) {
        if (card == this) {
            p().gainGold(magicNumber);
            return true;
        }
        return false;
    }
}
