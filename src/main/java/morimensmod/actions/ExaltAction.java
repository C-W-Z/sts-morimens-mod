package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterExalt;
import morimensmod.interfaces.OnBeforeExalt;

public class ExaltAction extends AbstractGameAction {

    AbstractAwakener awaker;
    boolean overExalt;
    int exhaustAliemus;

    public ExaltAction(AbstractAwakener awaker) {
        this.actionType = ActionType.SPECIAL;
        this.awaker = awaker;
        this.overExalt = AbstractAwakener.aliemus >= AbstractAwakener.extremeAlimus;

        AbstractAwakener.exalting = true;
        AbstractAwakener.exaltedThisTurn++;

        int aliemusBefore = AbstractAwakener.aliemus;
        AbstractAwakener.aliemus -= overExalt ? AbstractAwakener.extremeAlimus : AbstractAwakener.aliemusLimit;
        if (AbstractAwakener.aliemus < 0)
            AbstractAwakener.aliemus = 0;
        else if (AbstractAwakener.aliemus > 0)
            AbstractAwakener.aliemus /= 2;
        exhaustAliemus = aliemusBefore - AbstractAwakener.aliemus;
    }

    @Override
    public void update() {
        // 呼叫所有 Power 的 hook
        for (AbstractPower p : awaker.powers)
            if (p instanceof OnBeforeExalt)
                ((OnBeforeExalt) p).onBeforeExalt(awaker, exhaustAliemus, overExalt);
        // 呼叫所有遺物的 hook
        for (AbstractRelic r : awaker.relics)
            if (r instanceof OnBeforeExalt)
                ((OnBeforeExalt) r).onBeforeExalt(awaker, exhaustAliemus, overExalt);
        // 呼叫姿態（Stance）的 hook
        if (awaker.stance instanceof OnBeforeExalt)
            ((OnBeforeExalt) awaker.stance).onBeforeExalt(awaker, exhaustAliemus, overExalt);

        if (overExalt)
            awaker.exalt.overExalt();
        else
            awaker.exalt.exalt();

        AbstractAwakener.exalting = false;

        isDone = true;

        // 呼叫所有 Power 的 hook
        for (AbstractPower p : awaker.powers)
            if (p instanceof OnAfterExalt)
                ((OnAfterExalt) p).onAfterExalt(awaker, exhaustAliemus, overExalt);
        // 呼叫所有遺物的 hook
        for (AbstractRelic r : awaker.relics)
            if (r instanceof OnAfterExalt)
                ((OnAfterExalt) r).onAfterExalt(awaker, exhaustAliemus, overExalt);
        // 呼叫姿態（Stance）的 hook
        if (awaker.stance instanceof OnAfterExalt)
            ((OnAfterExalt) awaker.stance).onAfterExalt(awaker, exhaustAliemus, overExalt);
    }
}
