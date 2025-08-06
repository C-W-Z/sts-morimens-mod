package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;

import morimensmod.actions.AliemusRegenChangeAction;
import morimensmod.powers.wheelofdestiny.ManikinOfOblivionPower;

public class ManikinOfOblivion extends AbstractWheelOfDestiny {

    public final static String ID = makeID(ManikinOfOblivion.class.getSimpleName());

    public ManikinOfOblivion() {
        super(ID, CardRarity.RARE);
        magicNumber = baseMagicNumber = 0; // 狂氣回充
        secondMagic = baseSecondMagic = 10; // 狂氣、中毒、治療提升%數
    }

    @Override
    public void upp() {
        upgradeMagicNumber(7);
    }

    @Override
    public boolean onInitDeck() {
        applyToSelf(new ManikinOfOblivionPower(p(), secondMagic));
        if (upgraded)
            addToBot(new AliemusRegenChangeAction(p(), magicNumber));
        return true;
    }
}
