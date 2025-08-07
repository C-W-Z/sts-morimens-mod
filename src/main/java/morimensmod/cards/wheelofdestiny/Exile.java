package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.SYMPTOM_COLOR;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class Exile extends AbstractWheelOfDestiny {
    public final static String ID = makeID(Exile.class.getSimpleName());

    public Exile() {
        super(ID, CardRarity.COMMON);
        magicNumber = baseMagicNumber = 50;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(25);
    }

    @Override
    public void preRemoveCardFromDeck(AbstractCard card) {
        if (card.type != CardType.CURSE && card.color != SYMPTOM_COLOR)
            return;
        p().gainGold(magicNumber);
    }

    @Override
    public int getPrice() {
        return 50;
    }
}
