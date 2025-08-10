package morimensmod.dynamicvariables;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.cards.AbstractEasyCard;

public class SecondMagicNumber extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("M2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return false;
        return ((AbstractEasyCard) card).isSecondMagicModified;
    }

    @Override
    public int value(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return -1;
        return ((AbstractEasyCard) card).secondMagic;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (!(card instanceof AbstractEasyCard))
            return;
        ((AbstractEasyCard) card).isSecondMagicModified = v;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return -1;
        return ((AbstractEasyCard) card).baseSecondMagic;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return false;
        return ((AbstractEasyCard) card).upgradedSecondMagic;
    }
}
