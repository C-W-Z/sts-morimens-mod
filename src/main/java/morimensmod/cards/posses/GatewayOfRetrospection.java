package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.drawPile;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.actions.MoveFromDrawPileAndChangeCostAction;
import morimensmod.cards.PileModalSelectCard;
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
        ArrayList<AbstractCard> cardList = new ArrayList<>();
        for (AbstractCard c : drawPile().group) {
            cardList.add(new PileModalSelectCard(c,
                    () -> addToTop(new MoveFromDrawPileAndChangeCostAction(c, -1))));
        }
        addToBot(new EasyModalChoiceAction(cardList));
    }
}
