package morimensmod.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterPosse;
import morimensmod.interfaces.OnBeforePosse;
import morimensmod.misc.PosseType;
import morimensmod.cards.AbstractPosse;

public class PosseAction extends AbstractGameAction {

    private static final Logger logger = LogManager.getLogger(PosseAction.class);

    AbstractAwakener awaker;
    PosseType type;
    AbstractPosse posse;
    boolean purgeOnUse;
    int exhaustKeyflare;

    public PosseAction(AbstractPosse posse) {
        this.actionType = ActionType.SPECIAL;
        this.posse = posse;
        exhaustKeyflare = AbstractAwakener.exhaustKeyflareForPosse(posse.getType());
    }

    public PosseAction(AbstractAwakener awaker, PosseType type, AbstractPosse posse) {
        this(awaker, type, posse, false);
    }

    public PosseAction(AbstractAwakener awaker, PosseType type, AbstractPosse posse, boolean purgeOnUse) {
        this.actionType = ActionType.SPECIAL;
        this.awaker = awaker;
        this.type = type;
        if (purgeOnUse) {
            this.posse = (AbstractPosse) posse.makeCopy();
            this.posse.setPurgeOnUse(purgeOnUse);
        } else
            this.posse = posse;

        exhaustKeyflare = AbstractAwakener.exhaustKeyflareForPosse(type);
    }

    @Override
    public void update() {
        // 呼叫所有 Power 的 hook
        for (AbstractPower p : awaker.powers)
            if (p instanceof OnBeforePosse)
                ((OnBeforePosse) p).onBeforePosse(posse, exhaustKeyflare);
        // 呼叫所有遺物的 hook
        for (AbstractRelic r : awaker.relics)
            if (r instanceof OnBeforePosse)
                ((OnBeforePosse) r).onBeforePosse(posse, exhaustKeyflare);
        // 呼叫姿態（Stance）的 hook
        if (awaker.stance instanceof OnBeforePosse)
            ((OnBeforePosse) awaker.stance).onBeforePosse(posse, exhaustKeyflare);

        logger.debug("PosseType: " + type);

        awaker.triggerPosse(type, posse);

        // if (!purgeOnUse && awaker.hasPower(PosseTwicePower.POWER_ID)) {
        // addToTop(new PosseAction(awaker, PosseType.UNLIMITED, posse, true));
        // addToTop(new ReducePowerAction(awaker, awaker, PosseTwicePower.POWER_ID, 1));
        // }

        isDone = true;

        // 讓PosseTwicePower在鑰令的activate裡的addToTop都被執行完之後再執行
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                // 呼叫所有 Power 的 hook
                for (AbstractPower p : awaker.powers)
                    if (p instanceof OnAfterPosse)
                        ((OnAfterPosse) p).onAfterPosse(posse, exhaustKeyflare);
                // 呼叫所有遺物的 hook
                for (AbstractRelic r : awaker.relics)
                    if (r instanceof OnAfterPosse)
                        ((OnAfterPosse) r).onAfterPosse(posse, exhaustKeyflare);
                // 呼叫姿態（Stance）的 hook
                if (awaker.stance instanceof OnAfterPosse)
                    ((OnAfterPosse) awaker.stance).onAfterPosse(posse, exhaustKeyflare);

                isDone = true;
            }
        });
    }
}
