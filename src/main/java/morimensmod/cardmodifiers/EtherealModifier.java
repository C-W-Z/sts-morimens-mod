package morimensmod.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;

import static morimensmod.MorimensMod.makeID;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EtherealModifier extends AbstractCardModifier {

    public static final String ID = makeID(EtherealModifier.class.getSimpleName());

    public EtherealModifier() {
    }

    public boolean shouldApply(AbstractCard card) {
        if (card.isEthereal)
            return false;
        if (CommonKeywordIconsField.useIcons.get(card))
            return true;
        CardModifierManager.addModifier(card, new EtherealMod());
        return false;
    }

    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
    }

    public void onRemove(AbstractCard card) {
        card.isEthereal = false;
    }

    public AbstractCardModifier makeCopy() {
        return new EtherealModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
