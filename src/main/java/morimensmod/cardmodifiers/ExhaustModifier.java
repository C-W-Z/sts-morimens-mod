package morimensmod.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;

import static morimensmod.MorimensMod.makeID;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ExhaustModifier extends AbstractCardModifier {

    public static final String ID = makeID(ExhaustModifier.class.getSimpleName());

    public ExhaustModifier() {
    }

    public boolean shouldApply(AbstractCard card) {
        if (card.exhaust)
            return false;
        if (CommonKeywordIconsField.useIcons.get(card))
            return true;
        CardModifierManager.addModifier(card, new ExhaustMod());
        return false;
    }

    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
    }

    public void onRemove(AbstractCard card) {
        card.exhaust = false;
    }

    public AbstractCardModifier makeCopy() {
        return new ExhaustModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
