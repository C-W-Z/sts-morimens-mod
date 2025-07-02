package morimensmod.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class ExhaustModifier extends AbstractCardModifier {

    public static final String ID = makeID(ExhaustModifier.class.getSimpleName());

    public ExhaustModifier() {
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.exhaust;
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
