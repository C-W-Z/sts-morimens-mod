package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.p;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class TinyWish extends AbstractPosse {

    public final static String ID = makeID(TinyWish.class.getSimpleName());

    // for register to CardLibrary
    public TinyWish() {
        super(ID, null, PosseType.UNLIMITED);
    }

    public TinyWish(AbstractAwakener p, PosseType type) {
        super(ID, p, type);
    }

    @Override
    public void activate() {
        atb(new AliemusChangeAction(p(), 35));
    }
}
