package morimensmod.dynamicvariables;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.cards.AbstractEasyCard;

public class Aliemus extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("A");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return false;
        return ((AbstractEasyCard) card).isAliemusModified;
    }

    @Override
    public int value(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return -1;
        return ((AbstractEasyCard) card).aliemus;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (!(card instanceof AbstractEasyCard))
            return;
        ((AbstractEasyCard) card).isAliemusModified = v;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return -1;
        return ((AbstractEasyCard) card).baseAliemus;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return false;
        return ((AbstractEasyCard) card).upgradedAliemus;
    }
}
