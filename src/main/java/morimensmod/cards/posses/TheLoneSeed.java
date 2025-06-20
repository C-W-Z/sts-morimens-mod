package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.hand;
import static morimensmod.util.Wiz.isCommandCard;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.actions.TheLoneSeedAction;
import morimensmod.cards.PileModalSelectCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class TheLoneSeed extends AbstractPosse {

    public final static String ID = makeID(TheLoneSeed.class.getSimpleName());

    // for register to CardLibrary
    public TheLoneSeed() {
        this(null, PosseType.UNLIMITED);
    }

    public TheLoneSeed(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    @Override
    public void activate() {
        ArrayList<AbstractCard> cardList = new ArrayList<>();

        for (AbstractCard c : hand().group) {
            if (isCommandCard(c))
                cardList.add(new PileModalSelectCard(c, () -> addToTop(new TheLoneSeedAction(c))));
        }

        addToBot(new EasyModalChoiceAction(cardList));

        addToBot(new AliemusChangeAction(awaker, 15));
    }
}
