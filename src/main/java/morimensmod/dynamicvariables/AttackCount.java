package morimensmod.dynamicvariables;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.cards.AbstractEasyCard;

public class AttackCount extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("AC");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return false;
        return ((AbstractEasyCard) card).isAttackCountModified;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (!(card instanceof AbstractEasyCard))
            return;
        ((AbstractEasyCard) card).isAttackCountModified = v;
    }

    @Override
    public int value(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return -1;
        return ((AbstractEasyCard) card).attackCount;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return -1;
        return ((AbstractEasyCard) card).baseAttackCount;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard))
            return false;
        return ((AbstractEasyCard) card).upgradedAttackCount;
    }
}
