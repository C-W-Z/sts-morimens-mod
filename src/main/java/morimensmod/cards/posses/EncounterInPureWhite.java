package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class EncounterInPureWhite extends AbstractPosse {

    public final static String ID = makeID(EncounterInPureWhite.class.getSimpleName());

    public EncounterInPureWhite() {
        super(ID);
        posseIndex = 3;
    }

    @Override
    public void activate() {
        int handSize = awaker.hand.size();
        addToBot(new DiscardAction(awaker, awaker, handSize, false));
        addToBot(new DrawCardAction(awaker, handSize + 2));
    }
}
