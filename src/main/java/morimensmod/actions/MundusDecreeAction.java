package morimensmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;

public class MundusDecreeAction extends MultiGroupSelectAction {

    public MundusDecreeAction() {
        super("Select 1 card to move to hand", (cards, card2Group) -> {
            for (AbstractCard c : cards) {
                if (c.cost >= 0)
                    c.freeToPlayOnce = true;
                card2Group.get(c).moveToHand(c);
            }
        }, 1, CardGroupType.DRAW_PILE, CardGroupType.DISCARD_PILE);
    }
}
