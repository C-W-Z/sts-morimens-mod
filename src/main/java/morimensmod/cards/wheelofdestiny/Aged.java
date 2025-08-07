package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import morimensmod.actions.KeyflareChangeAction;

public class Aged extends AbstractWheelOfDestiny {
    public final static String ID = makeID(Aged.class.getSimpleName());

    public Aged() {
        super(ID, CardRarity.COMMON);
        magicNumber = baseMagicNumber = 250;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(250);
    }

    @Override
    public boolean onVictory(boolean victory) {
        if (victory)
            new KeyflareChangeAction(p(), magicNumber).update();
        return victory;
    }
}
