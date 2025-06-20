package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;

import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class AMousesWisdom extends AbstractPosse {

    public final static String ID = makeID(AMousesWisdom.class.getSimpleName());

    // for register to CardLibrary
    public AMousesWisdom() {
        this(null, PosseType.UNLIMITED);
    }

    public AMousesWisdom(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    @Override
    public void activate() {
        addToBot(new GainEnergyAction(3));
    }
}
