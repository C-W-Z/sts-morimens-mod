package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import morimensmod.characters.AbstractAwakener;

public class AliemusChangeAction extends AbstractGameAction {

    AbstractAwakener awaker;

    public AliemusChangeAction(AbstractPlayer awaker, int amount) {
        this.actionType = ActionType.SPECIAL;
        this.amount = amount;
        if (awaker instanceof AbstractAwakener)
            this.awaker = (AbstractAwakener) awaker;
        else
            this.awaker = null;
    }

    @Override
    public void update() {
        if (amount > 0) {

            AbstractAwakener.aliemus += amount;
            if (AbstractAwakener.aliemus > AbstractAwakener.extremeAlimus) {
                AbstractAwakener.aliemus = AbstractAwakener.extremeAlimus;
            }

        } else if (amount < 0) {

            AbstractAwakener.aliemus += amount;
            if (AbstractAwakener.aliemus < 0) {
                AbstractAwakener.aliemus = 0;
            }

        }

        isDone = true;
    }

}
