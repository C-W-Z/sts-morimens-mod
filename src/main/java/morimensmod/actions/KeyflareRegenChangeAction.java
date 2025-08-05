package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import morimensmod.characters.AbstractAwakener;

public class KeyflareRegenChangeAction extends AbstractGameAction {

    AbstractAwakener awaker = null;

    public KeyflareRegenChangeAction(AbstractPlayer awaker, int amount) {
        this.actionType = ActionType.SPECIAL;
        this.amount = amount;
        if (awaker instanceof AbstractAwakener)
            this.awaker = (AbstractAwakener) awaker;
        else
            isDone = true;
    }

    @Override
    public void update() {
        if (awaker != null)
            awaker.keyflareRegen += amount;
        isDone = true;
    }
}
