package morimensmod.exalts;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actionify;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.getCleanCopy;
import static morimensmod.util.Wiz.isCommandCard;
import static morimensmod.util.Wiz.makeInHand;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField.ExhaustiveFields;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.helpers.CardModifierManager;
import morimensmod.cardmodifiers.ChangeCostUntilUseModifier;
import morimensmod.cardmodifiers.EtherealModifier;
import morimensmod.cardmodifiers.ExhaustModifier;
import morimensmod.cards.posses.AbstractPosse;
import morimensmod.characters.AbstractAwakener;

public class ParadoxConverged extends AbstractExalt {

    public static final String ID = makeID(ParadoxConverged.class.getSimpleName());
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    private AbstractPosse originPosse = null;

    private ArrayList<AbstractCard> lastTurnNonExhaustCards = new ArrayList<>();
    private ArrayList<AbstractCard> thisTurnNonExhaustCards = new ArrayList<>();
    public static final int MAX_CARD_PER_TURN = 3;
    public static final int COST_CHANGE = -1;

    @Override
    public void onCardUse(AbstractCard card) {
        if (card.exhaust || card.isEthereal || ExhaustiveFields.exhaustive.get(card) >= 0)
            return;
        if (!isCommandCard(card))
            return;
        thisTurnNonExhaustCards.add(card);
        if (thisTurnNonExhaustCards.size() > MAX_CARD_PER_TURN)
            thisTurnNonExhaustCards.remove(0);
    }

    @Override
    public void onBattleStart() {
        lastTurnNonExhaustCards.clear();
        thisTurnNonExhaustCards.clear();
        if (originPosse != null)
            ((AbstractAwakener) p()).setPosse(originPosse);
        originPosse = null;
    }

    @Override
    public void onPostBattle(AbstractRoom room) {
        onBattleStart();
    }

    @Override
    public void onPlayerTurnStart() {
        lastTurnNonExhaustCards = thisTurnNonExhaustCards;
        thisTurnNonExhaustCards = new ArrayList<>();
    }

    @Override
    public void exalt() {
        for (AbstractCard c : lastTurnNonExhaustCards) {
            AbstractCard copy = getCleanCopy(c);
            CardModifierManager.addModifier(copy, new ExhaustModifier());
            CardModifierManager.addModifier(copy, new EtherealModifier());
            CardModifierManager.addModifier(copy, new ChangeCostUntilUseModifier(COST_CHANGE));
            makeInHand(copy);
        }
        if (lastTurnNonExhaustCards.size() < MAX_CARD_PER_TURN)
            atb(new DrawCardAction(MAX_CARD_PER_TURN - lastTurnNonExhaustCards.size(), actionify(() -> {
                for (AbstractCard _c : DrawCardAction.drawnCards) {
                    CardModifierManager.addModifier(_c, new ChangeCostUntilUseModifier(COST_CHANGE));
                }
            })));
    }

    @Override
    public void overExalt() {
        for (AbstractCard c : lastTurnNonExhaustCards) {
            AbstractCard copy = getCleanCopy(c);
            CardModifierManager.addModifier(copy, new ExhaustModifier());
            CardModifierManager.addModifier(copy, new EtherealModifier());
            copy.freeToPlayOnce = true;
            makeInHand(copy);
        }
        if (lastTurnNonExhaustCards.size() < MAX_CARD_PER_TURN)
            atb(new DrawCardAction(MAX_CARD_PER_TURN - lastTurnNonExhaustCards.size(), actionify(() -> {
                for (AbstractCard _c : DrawCardAction.drawnCards)
                    _c.freeToPlayOnce = true;
            })));
    }

    @Override
    public String getExaltTitle() {
        return UI_STRINGS.TEXT[0];
    }

    @Override
    public String getExaltDescription() {
        return UI_STRINGS.TEXT[1];
    }

    @Override
    public String getOverExaltTitle() {
        return UI_STRINGS.EXTRA_TEXT[0];
    }

    @Override
    public String getOverExaltDescription() {
        return UI_STRINGS.EXTRA_TEXT[1];
    }
}
