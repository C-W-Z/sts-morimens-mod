package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import morimensmod.actions.MoveFromDrawPileAndChangeCostAction;

public class GatewayOfRetrospection extends AbstractPosse {

    public final static String ID = makeID(GatewayOfRetrospection.class.getSimpleName());

    public GatewayOfRetrospection() {
        super(ID);
        posseIndex = 2006;
    }

    @Override
    public void activate() {
        addToBot(new MoveFromDrawPileAndChangeCostAction(-1));
    }
}
