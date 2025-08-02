package morimensmod.actions;

import static morimensmod.util.Wiz.drawPile;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class PickDrawPileCardsAction extends SelectCardsCenteredAction {

    public PickDrawPileCardsAction(int topN, int pick) {
        super(getCards(topN),
                Math.min(topN, pick),
                String.format("Choose %d Cards to Place in Hand", Math.min(topN, pick)),
                (cards) -> {
                    for (AbstractCard c : cards)
                        drawPile().moveToHand(c);
                });
    }

    protected static ArrayList<AbstractCard> getCards(int topN) {
        if (drawPile().size() <= topN)
            return drawPile().group;
        ArrayList<AbstractCard> group = new ArrayList<>();
        for (int i = 0; i < topN; i++)
            group.add(drawPile().getNCardFromTop(i));
        return group;
    }
}
