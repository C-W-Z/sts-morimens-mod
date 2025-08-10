package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;

import morimensmod.actions.AliemusChangeAction;

public class FestivalOfTides extends AbstractPosse {

    public final static String ID = makeID(FestivalOfTides.class.getSimpleName());

    public FestivalOfTides() {
        super(ID);
    }

    @Override
    public void activate() {
        addToBot(new DrawCardAction(awaker, 2));
        addToBot(new GainEnergyAction(2));
        addToBot(new AliemusChangeAction(awaker, -5));
        // TODO: 靜海+中毒
    }
}
