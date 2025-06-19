package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.atb;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;

import morimensmod.cards.AbstractPosse;
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
        atb(new GainEnergyAction(3));
    }
}
