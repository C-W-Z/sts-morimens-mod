package morimensmod.dynamicvariables;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.cards.AbstractEasyCard;

public class ThirdMagicNumber extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("M3");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return false;
        return ((AbstractEasyCard) card).isThirdMagicModified;
    }

    @Override
    public int value(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return -1;
        return ((AbstractEasyCard) card).thirdMagic;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (!(card instanceof AbstractEasyCard))
            return;
        ((AbstractEasyCard) card).isThirdMagicModified = v;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return -1;
        return ((AbstractEasyCard) card).baseThirdMagic;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return false;
        return ((AbstractEasyCard) card).upgradedThirdMagic;
    }
}
