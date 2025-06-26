package morimensmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static morimensmod.MorimensMod.makeID;

import java.util.ArrayList;

public class EasyModalChoiceAction extends SelectCardsCenteredAction {

    private static final String TEXT[] = CardCrawlGame.languagePack
            .getUIString(makeID(EasyModalChoiceAction.class.getSimpleName())).TEXT;

    public EasyModalChoiceAction(ArrayList<AbstractCard> list, int amount, String textforSelect) {
        super(list, amount, textforSelect, (cards) -> {
            for (AbstractCard q : cards) {
                q.onChoseThisOption();
            }
        });
    }

    public EasyModalChoiceAction(ArrayList<AbstractCard> list, int amount) {
        this(list, amount, TEXT[0] + amount + TEXT[1]);
    }

    public EasyModalChoiceAction(ArrayList<AbstractCard> list) {
        this(list, 1);
    }
}
