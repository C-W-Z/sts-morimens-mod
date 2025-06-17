package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.cards.AbstractPosseCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;
import morimensmod.posses.VoicesInYourHead;

public class VoicesInYourHeadCard extends AbstractPosseCard {

    public final static String ID = makeID(VoicesInYourHead.class.getSimpleName());

    // for register to CardLibrary
    public VoicesInYourHeadCard() {
        super(ID, null, PosseType.UNLIMITED, new VoicesInYourHead());
    }

    public VoicesInYourHeadCard(AbstractAwakener p, PosseType type) {
        super(ID, p, type, new VoicesInYourHead());
    }

    public VoicesInYourHeadCard(Runnable onUseOrChosen) {
        super(ID, onUseOrChosen);
    }

    @Override
    public AbstractCard makeCopy() {
        return new VoicesInYourHeadCard(onUseOrChosen);
    }
}
