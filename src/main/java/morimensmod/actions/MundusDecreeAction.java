package morimensmod.actions;

import static morimensmod.util.Wiz.discardPile;
import static morimensmod.util.Wiz.drawPile;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MundusDecreeAction extends AbstractGameAction {

    AbstractCard card;

    public MundusDecreeAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (drawPile().group.contains(card))
        {
            card.freeToPlayOnce = true;
            drawPile().moveToHand(card);
        }
        else if (discardPile().group.contains(card))
        {
            card.freeToPlayOnce = true;
            discardPile().moveToHand(card);
        }
        isDone = true;
    }
}
