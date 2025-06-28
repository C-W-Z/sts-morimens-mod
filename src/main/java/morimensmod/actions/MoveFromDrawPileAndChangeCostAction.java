package morimensmod.actions;

import static morimensmod.util.Wiz.drawPile;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.helpers.CardModifierManager;
import morimensmod.cardmodifiers.ChangeCostUntilUseModifier;

public class MoveFromDrawPileAndChangeCostAction extends AbstractGameAction {

    AbstractCard card;

    public MoveFromDrawPileAndChangeCostAction(AbstractCard card, int amount) {
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (drawPile().group.contains(card))
        {
            if (amount != 0)
                CardModifierManager.addModifier(card, new ChangeCostUntilUseModifier(amount));
            drawPile().moveToHand(card);
        }
        isDone = true;
    }
}
