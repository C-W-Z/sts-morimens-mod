package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterPosse;
import morimensmod.interfaces.OnBeforePosse;
import morimensmod.misc.PosseType;
import morimensmod.posses.AbstractPosse;
import morimensmod.powers.PosseTwicePower;

public class PosseAction extends AbstractGameAction {

    AbstractAwakener awaker;
    PosseType type;
    AbstractPosse posse;
    int exhaustKeyflare;

    public PosseAction(AbstractAwakener awaker, PosseType type, AbstractPosse posse) {
        this.actionType = ActionType.SPECIAL;
        this.awaker = awaker;
        this.type = type;
        this.posse = posse;

        exhaustKeyflare = AbstractAwakener.exhaustKeyflareForPosse(type);
    }

    @Override
    public void update() {
        // 呼叫所有 Power 的 hook
        for (AbstractPower p : awaker.powers)
            if (p instanceof OnBeforePosse)
                ((OnBeforePosse) p).onBeforePosse(awaker, exhaustKeyflare, type);
        // 呼叫所有遺物的 hook
        for (AbstractRelic r : awaker.relics)
            if (r instanceof OnBeforePosse)
                ((OnBeforePosse) r).onBeforePosse(awaker, exhaustKeyflare, type);
        // 呼叫姿態（Stance）的 hook
        if (awaker.stance instanceof OnBeforePosse)
            ((OnBeforePosse) awaker.stance).onBeforePosse(awaker, exhaustKeyflare, type);

        awaker.triggerPosse(type, posse);

        if (awaker.hasPower(PosseTwicePower.POWER_ID)) {
            addToTop(new PosseAction(awaker, PosseType.UNLIMITED, posse));
            addToTop(new ReducePowerAction(awaker, awaker, PosseTwicePower.POWER_ID, 1));
        }

        isDone = true;

        // 呼叫所有 Power 的 hook
        for (AbstractPower p : awaker.powers)
            if (p instanceof OnAfterPosse)
                ((OnAfterPosse) p).onAfterPosse(awaker, exhaustKeyflare, type);
        // 呼叫所有遺物的 hook
        for (AbstractRelic r : awaker.relics)
            if (r instanceof OnAfterPosse)
                ((OnAfterPosse) r).onAfterPosse(awaker, exhaustKeyflare, type);
        // 呼叫姿態（Stance）的 hook
        if (awaker.stance instanceof OnAfterPosse)
            ((OnAfterPosse) awaker.stance).onAfterPosse(awaker, exhaustKeyflare, type);
    }
}
