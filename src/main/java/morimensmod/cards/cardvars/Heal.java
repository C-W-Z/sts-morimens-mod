package morimensmod.cards.cardvars;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.cards.AbstractEasyCard;

public class Heal extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("H");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).isHealModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractEasyCard) {
            ((AbstractEasyCard) card).isHealModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        return card.heal;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card.baseHeal;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).upgradedHeal;
        }
        return false;
    }
}