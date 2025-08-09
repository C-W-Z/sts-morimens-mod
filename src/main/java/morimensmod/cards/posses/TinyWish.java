package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;


import morimensmod.actions.AliemusChangeAction;

public class TinyWish extends AbstractPosse {

    public final static String ID = makeID(TinyWish.class.getSimpleName());

    public TinyWish() {
        super(ID);
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

    @Override
    public boolean isAwakenerOnly() {
        return true;
    }
}
