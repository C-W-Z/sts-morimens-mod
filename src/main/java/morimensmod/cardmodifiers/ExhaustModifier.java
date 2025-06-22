package morimensmod.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.hasModifier;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ExhaustModifier extends AbstractCardModifier {

    // private static final Logger logger = LogManager.getLogger(ExhaustModifier.class);

    public static final String ID = makeID(ExhaustModifier.class.getSimpleName());
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    public ExhaustModifier() {
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        String newDescription;
        // logger.debug("modifierCount before modifyDescription: " + modifierCount(card));
        // 此時已經被加上exhuast了
        if (hasModifier(rawDescription))
            newDescription = String.format(UI_STRINGS.TEXT[0], rawDescription);
        else
            newDescription = String.format(UI_STRINGS.TEXT[1], rawDescription);
        return newDescription;
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
