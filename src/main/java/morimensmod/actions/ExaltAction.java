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

    public ExaltAction(AbstractAwakener awaker) {
        this.actionType = ActionType.SPECIAL;
        this.awaker = awaker;
        this.overExalt = AbstractAwakener.aliemus >= AbstractAwakener.extremeAlimus;
        AbstractAwakener.exalting = true;
        AbstractAwakener.exaltedThisTurn++;
    }

    @Override
    public void update() {
        AbstractAwakener.aliemus -= overExalt ? AbstractAwakener.extremeAlimus : AbstractAwakener.maxAliemus;
        if (AbstractAwakener.aliemus < 0)
            AbstractAwakener.aliemus = 0;
        else if (AbstractAwakener.aliemus > 0)
            AbstractAwakener.aliemus /= 2;

        // 呼叫所有 Power 的 hook
        for (AbstractPower p : awaker.powers)
            if (p instanceof OnBeforeExalt)
                ((OnBeforeExalt) p).onBeforeExalt(awaker);
        // 呼叫所有遺物的 hook
        for (AbstractRelic r : awaker.relics)
            if (r instanceof OnBeforeExalt)
                ((OnBeforeExalt) r).onBeforeExalt(awaker);
        // 呼叫姿態（Stance）的 hook
        if (awaker.stance instanceof OnBeforeExalt)
            ((OnBeforeExalt) awaker.stance).onBeforeExalt(awaker);

        if (overExalt)
            awaker.exalt.overExalt();
        else
            awaker.exalt.exalt();

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
