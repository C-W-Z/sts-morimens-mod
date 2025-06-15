package morimensmod.actions;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

import morimensmod.characters.AbstractAwakener;

public class AliemusChangeAction extends AbstractGameAction {

    static final String TEXT = CardCrawlGame.languagePack.getUIString(makeID(AliemusChangeAction.class.getSimpleName())).TEXT[0];

    AbstractAwakener awaker;

    public AliemusChangeAction(AbstractPlayer awaker, int amount) {
        this.actionType = ActionType.SPECIAL;
        this.amount = amount;
        if (awaker instanceof AbstractAwakener)
            this.awaker = (AbstractAwakener) awaker;
        else
            this.awaker = null;
    }

    @Override
    public void update() {
        if (amount > 0) {

            AbstractAwakener.aliemus += amount;
            if (AbstractAwakener.aliemus > AbstractAwakener.extremeAlimus) {
                AbstractAwakener.aliemus = AbstractAwakener.extremeAlimus;
            }

            // addToTop(new TextAboveCreatureAction(p(), "+" + amount));
            AbstractDungeon.effectList.add(
                    new TextAboveCreatureEffect(
                            p().hb.cX - p().animX,
                            p().hb.cY + p().hb.height / 2.0F,
                            "+" + amount + TEXT,
                            Color.GOLD.cpy()));

        } else if (amount < 0) {

            AbstractAwakener.aliemus += amount;
            if (AbstractAwakener.aliemus < 0) {
                AbstractAwakener.aliemus = 0;
            }

            // addToTop(new TextAboveCreatureAction(p(), "" + amount));
            AbstractDungeon.effectList.add(
                    new TextAboveCreatureEffect(
                            p().hb.cX - p().animX,
                            p().hb.cY + p().hb.height / 2.0F,
                            amount + TEXT,
                            Color.GOLD.cpy()));
        }

        isDone = true;
    }

}
