package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class FestivalOfTides extends AbstractPosse {

    public final static String ID = makeID(FestivalOfTides.class.getSimpleName());

    // for register to CardLibrary
    public FestivalOfTides() {
        this(null, PosseType.UNLIMITED);
    }

    public FestivalOfTides(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    @Override
    public void activate() {
        addToBot(new DrawCardAction(awaker, 2));
        addToBot(new GainEnergyAction(2));
        addToBot(new AliemusChangeAction(awaker, -5));
        // TODO: 靜海+中毒
    }
}
