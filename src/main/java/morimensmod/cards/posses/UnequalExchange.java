package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;

import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class UnequalExchange extends AbstractPosse {

    public final static String ID = makeID(UnequalExchange.class.getSimpleName());

    // for register to CardLibrary
    public UnequalExchange() {
        this(null, PosseType.UNLIMITED);
    }

    public UnequalExchange(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    @Override
    public void activate() {
        addToBot(new DrawCardAction(awaker, 4));
    }
}
