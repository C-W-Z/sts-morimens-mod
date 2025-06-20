package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.atb;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class EncounterInPureWhite extends AbstractPosse {

    public final static String ID = makeID(EncounterInPureWhite.class.getSimpleName());

    // for register to CardLibrary
    public EncounterInPureWhite() {
        this(null, PosseType.UNLIMITED);
    }

    public EncounterInPureWhite(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    @Override
    public void activate() {
        int handSize = awaker.hand.size();
        atb(new DiscardAction(awaker, awaker, handSize, false));
        atb(new DrawCardAction(awaker, handSize + 2));
    }
}
