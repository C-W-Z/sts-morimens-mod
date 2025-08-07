package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.makeInHand;
import static morimensmod.util.Wiz.p;

import morimensmod.cards.chaos.Strike;
import morimensmod.characters.AbstractAwakener;
import morimensmod.powers.wheelofdestiny.HandOfOblivionPower;

public class HandOfOblivion extends AbstractWheelOfDestiny {
    public final static String ID = makeID(HandOfOblivion.class.getSimpleName());

    public HandOfOblivion() {
        super(ID, CardRarity.UNCOMMON);
        magicNumber = baseMagicNumber = 30; // 打擊增傷%數
        if (p() instanceof AbstractAwakener)
            cardsToPreview = ((AbstractAwakener) p()).getBasicStrike();
        else
            cardsToPreview = new Strike(); // Preview a Strike when hovering over this card.
    }

    @Override
    public void upp() {
        cardsToPreview.upgrade();
        upgradeMagicNumber(10);
    }

    @Override
    public boolean onInitDeck() {
        applyToSelf(new HandOfOblivionPower(p(), magicNumber));
        makeInHand(cardsToPreview);
        return true;
    }
}
