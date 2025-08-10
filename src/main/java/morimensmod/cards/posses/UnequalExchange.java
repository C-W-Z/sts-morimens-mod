package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class UnequalExchange extends AbstractPosse {

    public final static String ID = makeID(UnequalExchange.class.getSimpleName());

    public UnequalExchange() {
        super(ID);
    }

    @Override
    public void activate() {
        addToBot(new DrawCardAction(awaker, 4));
    }
}
