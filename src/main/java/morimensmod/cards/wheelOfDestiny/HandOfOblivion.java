package morimensmod.cards.wheelOfDestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.makeInHand;
import static morimensmod.util.Wiz.p;

import morimensmod.cards.chaos.Strike;
import morimensmod.interfaces.PassiveCard;
import morimensmod.powers.wheelOfDestiny.HandOfOblivionPower;

public class HandOfOblivion extends AbstractWheelOfDestiny implements PassiveCard {
    public final static String ID = makeID(HandOfOblivion.class.getSimpleName());

    public HandOfOblivion() {
        super(ID, -2, CardRarity.UNCOMMON);
        magicNumber = baseMagicNumber = 30; // 打擊增傷%數
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
