package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.actions.TheLoneSeedAction;

public class TheLoneSeed extends AbstractPosse {

    public final static String ID = makeID(TheLoneSeed.class.getSimpleName());

    public TheLoneSeed() {
        super(ID);
    }

    @Override
    public void activate() {
        addToBot(new TheLoneSeedAction());
        addToBot(new AliemusChangeAction(awaker, 15));
    }

    @Override
    public boolean isAwakenerOnly() {
        return true;
    }
}
