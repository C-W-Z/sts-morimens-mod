package morimensmod.actions;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

import morimensmod.characters.AbstractAwakener;
import morimensmod.config.ModSettings;

public class AliemusChangeAction extends AbstractGameAction {

    private static final Logger logger = LogManager.getLogger(AliemusChangeAction.class);

    static final String TEXT = CardCrawlGame.languagePack.getUIString(makeID(AliemusChangeAction.class.getSimpleName())).TEXT[0];

    AbstractAwakener awaker;

    public AliemusChangeAction(AbstractPlayer awaker, int amount) {
        this.actionType = ActionType.SPECIAL;
        this.amount = amount;
        if (awaker instanceof AbstractAwakener)
            this.awaker = (AbstractAwakener) awaker;
        else {
            this.awaker = null;
            logger.error("awaker passed to AliemusChangeAction is NOT an instance of AbstractAwakener");
            isDone = true;
        }
    }

    @Override
    public void update() {
        isDone = true;
        if (this.awaker == null)
            return;

        AbstractAwakener.changeAliemus(amount);

        if (amount > 0) {

            // addToTop(new TextAboveCreatureAction(p(), "+" + amount));
            AbstractDungeon.effectList.add(
                    new TextAboveCreatureEffect(
                            p().hb.cX - p().animX,
                            p().hb.cY + p().hb.height / 2.0F,
                            "+" + amount + TEXT,
                            ModSettings.ALIEMUS_INCREASE_TEXT_COLOR));

        } else if (amount < 0) {

            // addToTop(new TextAboveCreatureAction(p(), "" + amount));
            AbstractDungeon.effectList.add(
                    new TextAboveCreatureEffect(
                            p().hb.cX - p().animX,
                            p().hb.cY + p().hb.height / 2.0F,
                            amount + TEXT,
                            ModSettings.ALIEMUS_DECREASE_TEXT_COLOR));
        }
    }
}
