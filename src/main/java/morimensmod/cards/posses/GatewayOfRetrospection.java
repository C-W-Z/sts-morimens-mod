package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import morimensmod.actions.MoveFromDrawPileAndChangeCostAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class GatewayOfRetrospection extends AbstractPosse {

    public final static String ID = makeID(GatewayOfRetrospection.class.getSimpleName());

    // for register to CardLibrary
    public GatewayOfRetrospection() {
        this(null, PosseType.UNLIMITED);
    }

    public GatewayOfRetrospection(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    @Override
    public void activate() {
        addToBot(new MoveFromDrawPileAndChangeCostAction(-1));
    }
}
