package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.makeInHand;

import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.buffs.OctahedronDice;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class AGunsCry extends AbstractPosse {

    public final static String ID = makeID(AGunsCry.class.getSimpleName());

    // for register to CardLibrary
    public AGunsCry() {
        this(null, PosseType.UNLIMITED);
    }

    public AGunsCry(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
        cardsToPreview = new OctahedronDice();
        cardsToPreview.upgrade();
    }

    @Override
    public void activate() {
        makeInHand(cardsToPreview);
        applyToSelf(new StrengthPower(awaker, 1));
        applyToSelf(new LoseStrengthPower(awaker, 1));
    }
}
