package morimensmod.actions;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

import morimensmod.characters.AbstractAwakener;

public class KeyflareChangeAction extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(KeyflareChangeAction.class);

    static final String TEXT = CardCrawlGame.languagePack.getUIString(makeID(KeyflareChangeAction.class.getSimpleName())).TEXT[0];

    AbstractAwakener awaker;

    public KeyflareChangeAction(AbstractPlayer awaker, int amount) {
        this.actionType = ActionType.SPECIAL;
        this.amount = amount;
        if (awaker instanceof AbstractAwakener)
            this.awaker = (AbstractAwakener) awaker;
        else {
            this.awaker = null;
            logger.error("awaker passed to KeyflareChangeAction is NOT an instance of AbstractAwakener");
        }
    }

    @Override
    public void update() {
        if (amount > 0) {

            AbstractAwakener.changeKeyflare(amount);

            // addToTop(new TextAboveCreatureAction(p(), "+" + amount));
            AbstractDungeon.effectList.add(
                    new TextAboveCreatureEffect(
                            p().hb.cX - p().animX,
                            p().hb.cY + p().hb.height / 2.0F,
                            "+" + amount + TEXT,
                            Color.WHITE));

        } else if (amount < 0) {

            AbstractAwakener.changeKeyflare(amount);

            // addToTop(new TextAboveCreatureAction(p(), "" + amount));
            AbstractDungeon.effectList.add(
                    new TextAboveCreatureEffect(
                            p().hb.cX - p().animX,
                            p().hb.cY + p().hb.height / 2.0F,
                            amount + TEXT,
                            Color.WHITE));
        }

        isDone = true;
    }
}
