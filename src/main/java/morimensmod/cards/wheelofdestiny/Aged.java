package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import morimensmod.actions.KeyflareChangeAction;
import morimensmod.interfaces.PassiveCard;

public class Aged extends AbstractWheelOfDestiny implements PassiveCard {
    public final static String ID = makeID(Aged.class.getSimpleName());

    public Aged() {
        super(ID, -2, CardRarity.COMMON);
        magicNumber = baseMagicNumber = 250;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(250);
    }

    @Override
    public boolean onInitDeck() {
        return false;
    }

    @Override
    public void onVictory(boolean victory) {
        if (victory)
            new KeyflareChangeAction(p(), magicNumber).update();
    }
}
