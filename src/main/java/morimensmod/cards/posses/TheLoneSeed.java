package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import morimensmod.actions.AliemusChangeAction;
import morimensmod.actions.TheLoneSeedAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class TheLoneSeed extends AbstractPosse {

    public final static String ID = makeID(TheLoneSeed.class.getSimpleName());

    // for register to CardLibrary
    public TheLoneSeed() {
        this(null, PosseType.UNLIMITED);
    }

    public TheLoneSeed(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    @Override
    public void activate() {
        addToBot(new TheLoneSeedAction());
        addToBot(new AliemusChangeAction(awaker, 15));
    }
}
