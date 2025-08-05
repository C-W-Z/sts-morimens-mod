package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;

import morimensmod.interfaces.PassiveCard;
import morimensmod.powers.wheelofdestiny.AgedPower;

public class Aged extends AbstractWheelOfDestiny implements PassiveCard {
    public final static String ID = makeID(Aged.class.getSimpleName());

    public Aged() {
        super(ID, -2, CardRarity.COMMON);
        magicNumber = baseMagicNumber = AgedPower.KEYFLARE_PER_AMOUNT;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(AgedPower.KEYFLARE_PER_AMOUNT);
    }

    @Override
    public boolean onInitDeck() {
        applyToSelf(new AgedPower(p(), magicNumber / AgedPower.KEYFLARE_PER_AMOUNT));
        return true;
    }
}
