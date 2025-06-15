package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterExalt;

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

        // 呼叫所有 Power 的 hook
        for (AbstractPower p : awaker.powers)
            if (p instanceof OnAfterExalt)
                ((OnAfterExalt) p).onAfterExalt(awaker);
        // 呼叫所有遺物的 hook
        for (AbstractRelic r : awaker.relics)
            if (r instanceof OnAfterExalt)
                ((OnAfterExalt) r).onAfterExalt(awaker);
        // 呼叫姿態（Stance）的 hook
        if (awaker.stance instanceof OnAfterExalt)
            ((OnAfterExalt) awaker.stance).onAfterExalt(awaker);
    }
}
