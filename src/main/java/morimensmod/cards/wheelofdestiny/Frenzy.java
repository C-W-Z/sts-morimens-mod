package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;

import morimensmod.interfaces.PassiveCard;
import morimensmod.powers.wheelofdestiny.FrenzyPower;

public class Frenzy extends AbstractWheelOfDestiny implements PassiveCard {
    public final static String ID = makeID(Frenzy.class.getSimpleName());

    public Frenzy() {
        super(ID, -2, CardRarity.COMMON);
        magicNumber = baseMagicNumber = 3; // 狂氣
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public boolean onInitDeck() {
        applyToSelf(new FrenzyPower(p(), magicNumber));
        return true;
    }
}
