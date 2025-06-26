package morimensmod.actions;

import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.hand;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
        overExalt = AbstractAwakener.enoughAliemusForOverExalt();
        exhaustAliemus = AbstractAwakener.exhaustAliemusForExalt(overExalt);
    }

    @Override
    public void update() {
        // 呼叫所有遺物的 hook
        for (AbstractRelic r : awaker.relics)
            if (r instanceof OnBeforeExalt)
                ((OnBeforeExalt) r).onBeforeExalt(awaker, exhaustAliemus, overExalt);
        // 呼叫所有 Power 的 hook
        for (AbstractPower p : awaker.powers)
            if (p instanceof OnBeforeExalt)
                ((OnBeforeExalt) p).onBeforeExalt(awaker, exhaustAliemus, overExalt);
        // 呼叫姿態（Stance）的 hook
        if (awaker.stance instanceof OnBeforeExalt)
            ((OnBeforeExalt) awaker.stance).onBeforeExalt(awaker, exhaustAliemus, overExalt);
        // 呼叫所有手牌的 hook
        for (AbstractCard c : hand().group)
            if (c instanceof OnBeforeExalt)
                ((OnBeforeExalt) c).onBeforeExalt(awaker, exhaustAliemus, overExalt);

        awaker.triggerExalt(overExalt);

        isDone = true;

        actB(() -> {
            // 呼叫所有遺物的 hook
            for (AbstractRelic r : awaker.relics)
                if (r instanceof OnAfterExalt)
                    ((OnAfterExalt) r).onAfterExalt(awaker, exhaustAliemus, overExalt);
            // 呼叫所有 Power 的 hook
            for (AbstractPower p : awaker.powers)
                if (p instanceof OnAfterExalt)
                    ((OnAfterExalt) p).onAfterExalt(awaker, exhaustAliemus, overExalt);
            // 呼叫姿態（Stance）的 hook
            if (awaker.stance instanceof OnAfterExalt)
                ((OnAfterExalt) awaker.stance).onAfterExalt(awaker, exhaustAliemus, overExalt);
            // 呼叫所有手牌的 hook
            for (AbstractCard c : hand().group)
                if (c instanceof OnAfterExalt)
                    ((OnAfterExalt) c).onAfterExalt(awaker, exhaustAliemus, overExalt);
        });
    }
}
