package morimensmod.cards.wheelOfDestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.PassiveCard;
import morimensmod.powers.wheelOfDestiny.ManikinOfOblivionPower;

public class ManikinOfOblivion extends AbstractWheelOfDestiny implements PassiveCard {

    public final static String ID = makeID(ManikinOfOblivion.class.getSimpleName());

    public ManikinOfOblivion() {
        super(ID, -2, CardRarity.RARE);
        magicNumber = baseMagicNumber = 7; // 狂氣回充
        secondMagic = baseSecondMagic = 10; // 狂氣、中毒、治療提升%數
    }

    @Override
    public boolean onInitDeck() {
        applyToSelf(new ManikinOfOblivionPower(p(), secondMagic));
        if (upgraded && p() instanceof AbstractAwakener)
            actB(() -> ((AbstractAwakener) p()).aliemusRegen += magicNumber);
        return true;
    }
}
