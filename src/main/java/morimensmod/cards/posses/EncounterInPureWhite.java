package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.att;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

import morimensmod.cards.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class EncounterInPureWhite extends AbstractPosse {

    public final static String ID = makeID(EncounterInPureWhite.class.getSimpleName());

    // for register to CardLibrary
    public EncounterInPureWhite() {
        super(ID, null, PosseType.UNLIMITED);
    }

    public EncounterInPureWhite(AbstractAwakener p, PosseType type) {
        super(ID, p, type);
    }

    @Override
    public void activate() {
        int handSize = p().hand.size();
        att(new DrawCardAction(p(), handSize + 2));
        att(new DiscardAction(p(), p(), handSize, false));
    }
}
