package morimensmod.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class EtherealModifier extends AbstractCardModifier {

    public static final String ID = makeID(EtherealModifier.class.getSimpleName());

    public EtherealModifier() {
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.isEthereal;
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
