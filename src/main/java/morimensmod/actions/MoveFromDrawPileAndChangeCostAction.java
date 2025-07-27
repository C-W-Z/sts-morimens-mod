package morimensmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;

import basemod.helpers.CardModifierManager;
import morimensmod.cardmodifiers.ChangeCostUntilUseModifier;

public class MoveFromDrawPileAndChangeCostAction extends MultiGroupSelectAction {

    public MoveFromDrawPileAndChangeCostAction(int amount) {
        super("Select 1 card to move to hand", (cards, card2Group) -> {
            for (AbstractCard c : cards) {
                if (amount != 0)
                    CardModifierManager.addModifier(c, new ChangeCostUntilUseModifier(amount));
                card2Group.get(c).moveToHand(c);
            }
        }, 1, CardGroupType.DRAW_PILE);
    }
}
