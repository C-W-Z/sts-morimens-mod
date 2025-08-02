package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static morimensmod.MorimensMod.lastTurnCardsPlayed;
import static morimensmod.util.Wiz.*;

public class MoveLastPlayedCardToDrawPileTopAction extends AbstractGameAction {

    public MoveLastPlayedCardToDrawPileTopAction() {
        actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        isDone = true;
        for (int i = lastTurnCardsPlayed.size() - 1; i >= 0; i--) {
            AbstractCard c = lastTurnCardsPlayed.get(i);
            if (discardPile().contains(c)) {
                discardPile().moveToDeck(c, false);
                return;
            }
            if (drawPile().contains(c)) {
                drawPile().moveToDeck(c, false);
                return;
            }
            if (exhaustPile().contains(c)) {
                exhaustPile().moveToDeck(c, false);
                return;
            }
        }
    }
}
