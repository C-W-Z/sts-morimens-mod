package morimensmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

public class EasyModalChoiceAction extends SelectCardsCenteredAction {

    private static final String TEXT = CardCrawlGame.languagePack
            .getUIString(EasyModalChoiceAction.class.getSimpleName()).TEXT[0];

    public EasyModalChoiceAction(ArrayList<AbstractCard> list, int amount, String textforSelect) {
        super(list, amount, textforSelect, (cards) -> {
            for (AbstractCard q : cards) {
                q.onChoseThisOption();
            }
        });
    }

    public EasyModalChoiceAction(ArrayList<AbstractCard> list, int amount) {
        this(list, amount, TEXT);
    }

    public EasyModalChoiceAction(ArrayList<AbstractCard> list) {
        this(list, 1, TEXT);
    }
}
