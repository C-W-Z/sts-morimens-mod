package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import morimensmod.actions.AliemusChangeAction;

public class Whisper extends AbstractWheelOfDestiny {
    public final static String ID = makeID(Whisper.class.getSimpleName());

    public Whisper() {
        super(ID, CardRarity.COMMON);
        magicNumber = baseMagicNumber = 10;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(10);
    }

    @Override
    public boolean onVictory(boolean victory) {
        if (victory)
            new AliemusChangeAction(p(), magicNumber).update();
        return victory;
    }
}
