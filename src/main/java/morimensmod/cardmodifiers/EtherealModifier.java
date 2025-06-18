package morimensmod.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.hasModifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class EtherealModifier extends AbstractCardModifier {

    private static final Logger logger = LogManager.getLogger(EtherealModifier.class);

    public static final String ID = makeID(EtherealModifier.class.getSimpleName());
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    public EtherealModifier() {
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        String newDescription;
        if (hasModifier(card))
            newDescription = String.format(UI_STRINGS.TEXT[0], rawDescription);
        else
            newDescription = String.format(UI_STRINGS.TEXT[1], rawDescription);
        logger.debug("after modifyDescription:" + newDescription);
        return newDescription;
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
