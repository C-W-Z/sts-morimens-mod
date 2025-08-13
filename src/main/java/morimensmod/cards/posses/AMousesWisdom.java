package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;

public class AMousesWisdom extends AbstractPosse {

    public final static String ID = makeID(AMousesWisdom.class.getSimpleName());

    public AMousesWisdom() {
        super(ID);
        posseIndex = 2;
        magicNumber = baseMagicNumber = 3; // energy
    }

    @Override
    public void activate() {
        addToBot(new GainEnergyAction(magicNumber));
    }

    @Override
    public String getUIDescription() {
        return String.format(cardStrings.EXTENDED_DESCRIPTION[0], magicNumber);
    }
}
