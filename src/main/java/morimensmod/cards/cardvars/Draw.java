package morimensmod.cards.cardvars;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.cards.AbstractEasyCard;

public class Draw extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("DR");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).isDrawModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractEasyCard) {
            ((AbstractEasyCard) card).isDrawModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        return card.draw;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card.baseDraw;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).upgradedDraw;
        }
        return false;
    }
}