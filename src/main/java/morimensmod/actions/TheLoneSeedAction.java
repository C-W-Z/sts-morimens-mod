package morimensmod.actions;

import static morimensmod.util.Wiz.getCleanCopy;
import static morimensmod.util.Wiz.isCommandCard;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.att;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.helpers.CardModifierManager;
import morimensmod.cardmodifiers.ExhaustModifier;

public class TheLoneSeedAction extends SelectCardsInHandAction {

    private static final String TEXT = CardCrawlGame.languagePack
            .getUIString(makeID(TheLoneSeedAction.class.getSimpleName())).TEXT[0];

    public TheLoneSeedAction() {
        super(1, TEXT, c -> isCommandCard(c), cards -> {
            for (AbstractCard c : cards) {
                AbstractCard cleanCopy = getCleanCopy(c);
                CardModifierManager.addModifier(cleanCopy, new ExhaustModifier());
                att(new MakeTempCardInHandAction(cleanCopy, 1));
            }
        });
    }
}
