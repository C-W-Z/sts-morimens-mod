package morimensmod.actions;

import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.hand;
import static morimensmod.util.Wiz.p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterPosse;
import morimensmod.interfaces.OnBeforePosse;
import morimensmod.misc.PosseType;
import morimensmod.cards.posses.AbstractPosse;

public class PosseAction extends AbstractGameAction {

    private static final Logger logger = LogManager.getLogger(PosseAction.class);

    AbstractPosse posse;
    int exhaustKeyflare;

    public PosseAction(AbstractPosse posse) {
        this.actionType = ActionType.SPECIAL;
        this.posse = posse;
        exhaustKeyflare = AbstractAwakener.exhaustKeyflareForPosse(posse.getType());
    }

    public PosseAction(AbstractPlayer awaker, PosseType type, AbstractPosse posse) {
        this.actionType = ActionType.SPECIAL;
        this.posse = (AbstractPosse) posse.makeCopy();
        this.posse.set(awaker, type);
        exhaustKeyflare = AbstractAwakener.exhaustKeyflareForPosse(type);
    }

    @Override
    public void update() {
        // 呼叫所有遺物的 hook
        for (AbstractRelic r : posse.getAwakener().relics)
            if (r instanceof OnBeforePosse)
                ((OnBeforePosse) r).onBeforePosse(posse, exhaustKeyflare);
        // 呼叫所有 Power 的 hook
        for (AbstractPower p : posse.getAwakener().powers)
            if (p instanceof OnBeforePosse)
                ((OnBeforePosse) p).onBeforePosse(posse, exhaustKeyflare);
        // 呼叫姿態（Stance）的 hook
        if (posse.getAwakener().stance instanceof OnBeforePosse)
            ((OnBeforePosse) posse.getAwakener().stance).onBeforePosse(posse, exhaustKeyflare);
        // 呼叫所有手牌的 hook
        for (AbstractCard c : hand().group)
            if (c instanceof OnBeforePosse)
                ((OnBeforePosse) c).onBeforePosse(posse, exhaustKeyflare);

        logger.debug("PosseType: " + posse.getType());

        AbstractAwakener.triggerPosse(posse);

        isDone = true;

        // 讓PosseTwicePower在鑰令的activate裡的addToTop都被執行完之後再執行
        actB(() -> {
            // 呼叫所有遺物的 hook
            for (AbstractRelic r : posse.getAwakener().relics)
                if (r instanceof OnAfterPosse)
                    ((OnAfterPosse) r).onAfterPosse(posse, exhaustKeyflare);
            // 呼叫所有 Power 的 hook
            for (AbstractPower p : posse.getAwakener().powers)
                if (p instanceof OnAfterPosse)
                    ((OnAfterPosse) p).onAfterPosse(posse, exhaustKeyflare);
            // 呼叫姿態（Stance）的 hook
            if (posse.getAwakener().stance instanceof OnAfterPosse)
                ((OnAfterPosse) posse.getAwakener().stance).onAfterPosse(posse, exhaustKeyflare);
            // 呼叫所有手牌的 hook
            for (AbstractCard c : hand().group)
                if (c instanceof OnAfterPosse)
                    ((OnAfterPosse) c).onAfterPosse(posse, exhaustKeyflare);
            // 呼叫Exalt的hook
            if (p() instanceof AbstractAwakener)
                if (((AbstractAwakener) p()).getExalt() instanceof OnAfterPosse)
                    ((OnAfterPosse) ((AbstractAwakener) p()).getExalt()).onAfterPosse(posse, exhaustKeyflare);
        });
    }
}
