package morimensmod.actions;

import static morimensmod.util.Wiz.hand;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.helpers.CardModifierManager;
import morimensmod.cardmodifiers.ExhaustModifier;

public class TheLoneSeedAction extends AbstractGameAction {

    AbstractCard cleanCopy;

    public TheLoneSeedAction(AbstractCard card) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.cleanCopy = card.makeStatEquivalentCopy();
        CardModifierManager.removeAllModifiers(cleanCopy, true);
    }

    @Override
    public void update() {
        CardModifierManager.addModifier(cleanCopy, new ExhaustModifier());
        hand().addToHand(cleanCopy);
        isDone = true;
    }
}
