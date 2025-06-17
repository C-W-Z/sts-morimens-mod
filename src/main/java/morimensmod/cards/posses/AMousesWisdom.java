package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.att;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;

import morimensmod.cards.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class AMousesWisdom extends AbstractPosse {

    public final static String ID = makeID(AMousesWisdom.class.getSimpleName());

    // for register to CardLibrary
    public AMousesWisdom() {
        super(ID, null, PosseType.UNLIMITED);
    }

    public AMousesWisdom(AbstractAwakener p, PosseType type) {
        super(ID, p, type);
    }

    @Override
    public void activate() {
        att(new GainEnergyAction(3));
    }
}
