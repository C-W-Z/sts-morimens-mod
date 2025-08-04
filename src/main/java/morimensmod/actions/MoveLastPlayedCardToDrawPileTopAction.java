package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import static morimensmod.MorimensMod.thisTurnCardsPlayed;
import static morimensmod.util.Wiz.*;

import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoveLastPlayedCardToDrawPileTopAction extends AbstractGameAction {

    private static final Logger logger = LogManager.getLogger(MoveFromDrawPileAndChangeCostAction.class);

    private Predicate<AbstractCard> filter;

    public MoveLastPlayedCardToDrawPileTopAction(Predicate<AbstractCard> filter) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.filter = filter;
    }

    @Override
    public void update() {
        isDone = true;
        for (int i = thisTurnCardsPlayed.size() - 1; i >= 0; i--) {
            AbstractCard c = thisTurnCardsPlayed.get(i);
            logger.debug("lastTurnCard: " + c.name);
            if (!filter.test(c))
                continue;
            if (discardPile().contains(c)) {
                moveToDrawPileTop(discardPile(), c);
                logger.debug("discardPile.moveToDeck");
                return;
            }
            if (drawPile().contains(c)) {
                moveToDrawPileTop(drawPile(), c);
                logger.debug("drawPile.moveToDeck");
                return;
            }
            if (exhaustPile().contains(c)) {
                moveToDrawPileTop(exhaustPile(), c);
                logger.debug("exhaustPile.moveToDeck");
                return;
            }
        }
    }

    private void moveToDrawPileTop(CardGroup group, AbstractCard c) {
        group.moveToDeck(c, false);
        c.stopGlowing();
        c.unhover();
        c.unfadeOut();
        AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
    }
}
