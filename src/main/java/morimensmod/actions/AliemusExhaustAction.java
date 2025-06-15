package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import morimensmod.characters.AbstractAwakener;

public class AliemusExhaustAction extends AbstractGameAction {

    AbstractAwakener awaker;
    boolean overExalt;

    public AliemusExhaustAction(AbstractPlayer awaker) {
        this(awaker, false);
    }

    public AliemusExhaustAction(AbstractPlayer awaker, boolean overExalt) {
        this.actionType = ActionType.SPECIAL;
        if (awaker instanceof AbstractAwakener)
            this.awaker = (AbstractAwakener) awaker;
        else
            this.awaker = null;
        this.overExalt = overExalt;
    }

    @Override
    public void update() {
        AbstractAwakener.aliemus -= overExalt ? AbstractAwakener.extremeAlimus : AbstractAwakener.maxAliemus;
        if (AbstractAwakener.aliemus < 0)
            AbstractAwakener.aliemus = 0;
        else if (AbstractAwakener.aliemus > 0)
            AbstractAwakener.aliemus /= 2;

        AbstractAwakener.exalting = false;

        isDone = true;
    }
}
