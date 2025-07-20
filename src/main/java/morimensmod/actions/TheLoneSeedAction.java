package morimensmod.actions;

import static morimensmod.util.Wiz.getCleanCopy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.helpers.CardModifierManager;
import morimensmod.cardmodifiers.ExhaustModifier;

public class TheLoneSeedAction extends AbstractGameAction {

    AbstractCard cleanCopy;

    public TheLoneSeedAction(AbstractCard card) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.cleanCopy = getCleanCopy(card);
    }

    @Override
    public void update() {
        CardModifierManager.addModifier(cleanCopy, new ExhaustModifier());
        addToTop(new MakeTempCardInHandAction(cleanCopy, 1));
        isDone = true;
    }
}
