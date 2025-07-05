package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class TinyWish extends AbstractPosse {

    public final static String ID = makeID(TinyWish.class.getSimpleName());

    // for register to CardLibrary
    public TinyWish() {
        this(null, PosseType.UNLIMITED);
    }

    public TinyWish(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
        magicNumber = baseMagicNumber = 35; // 狂氣
    }

    @Override
    public void activate() {
        addToBot(new AliemusChangeAction(awaker, magicNumber));
    }

    @Override
    public String getUIDescription() {
        return String.format(cardStrings.EXTENDED_DESCRIPTION[0], magicNumber);
    }
}
