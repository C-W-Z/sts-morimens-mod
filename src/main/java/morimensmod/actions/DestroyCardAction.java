package morimensmod.actions;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DestroyCardAction extends AbstractGameAction {

    AbstractCard card;

    public DestroyCardAction(AbstractCard card) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
    }

    @Override
    public void update() {
        for (Iterator<AbstractCard> i = AbstractDungeon.player.masterDeck.group.iterator(); i.hasNext();) {
            AbstractCard c = i.next();
            if (c.uuid.equals(card.uuid)) {
                i.remove();

                c.onRemoveFromMasterDeck();
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    r.onMasterDeckChange();

                break;
            }
        }
        isDone = true;
    }
}
