package morimensmod.exalts;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.actionify;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.CardLib.getAllPosses;
import static morimensmod.util.CardLib.getAllPosseCards;
import static morimensmod.util.Wiz.getCleanCopy;
import static morimensmod.util.Wiz.isNonExhaustCommandCard;
import static morimensmod.util.Wiz.makeInHand;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.helpers.CardModifierManager;
import morimensmod.actions.KeyflareChangeAction;
import morimensmod.cardmodifiers.ChangeCostUntilUseModifier;
import morimensmod.cardmodifiers.EtherealModifier;
import morimensmod.cardmodifiers.ExhaustModifier;
import morimensmod.cards.posses.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.config.ModSettings;
import morimensmod.interfaces.OnAfterPosse;
import morimensmod.misc.PosseType;
import morimensmod.powers.NegentropyPower;
import morimensmod.vfx.LargPortraitFlashInEffect;

public class ParadoxConverged extends AbstractExalt implements OnAfterPosse {

    public static final String ID = makeID(ParadoxConverged.class.getSimpleName());
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    private String originPosse = null;

    private ArrayList<AbstractCard> lastTurnNonExhaustCards = new ArrayList<>();
    private ArrayList<AbstractCard> thisTurnNonExhaustCards = new ArrayList<>();
    public static final int MAX_CARD_PER_TURN = 3;
    public static final int COST_CHANGE = -1;
    public static final int NEGENTROPY = 3;

    @Override
    public void onCardUse(AbstractCard card) {
        if (!isNonExhaustCommandCard(card))
            return;

        // 檢查是否已存在相同 cardID 的卡牌
        for (int i = 0; i < thisTurnNonExhaustCards.size(); i++) {
            AbstractCard c = thisTurnNonExhaustCards.get(i);
            if (c.cardID.equals(card.cardID)) { // 假設 cardID 是 String，否則改用 ==
                if (c.timesUpgraded == card.timesUpgraded) {
                    return; // 相同升級次數，不添加
                } else if (c.timesUpgraded < card.timesUpgraded) {
                    thisTurnNonExhaustCards.set(i, card); // 替換現有卡牌
                    return;
                }
                // 如果現有卡牌的 timesUpgraded 更大，則不添加新卡牌
            }
        }

        thisTurnNonExhaustCards.add(card);
        if (thisTurnNonExhaustCards.size() > MAX_CARD_PER_TURN)
            thisTurnNonExhaustCards.remove(0);
    }

    @Override
    public void onInitDeck() {
        super.onInitDeck();
        lastTurnNonExhaustCards.clear();
        thisTurnNonExhaustCards.clear();
        resetPosse();
    }

    @Override
    public void onPostBattle(AbstractRoom room) {
        resetPosse();
    }

    @Override
    public void onAfterPosse(AbstractPosse posse, int exhaustKeyflare) {
        if (posse.getType() == PosseType.REGULAR)
            resetPosse();
    }

    public void resetPosse() {
        if (originPosse != null) {
            for (AbstractCard p : getAllPosses()) {
                if (p.cardID.equals(originPosse)) {
                    ((AbstractAwakener) p()).setPosse((AbstractPosse) p.makeCopy());
                    break;
                }
            }
        }
        originPosse = null;
    }

    @Override
    public void onPlayerTurnStart() {
        lastTurnNonExhaustCards = thisTurnNonExhaustCards;
        thisTurnNonExhaustCards = new ArrayList<>();
    }

    @Override
    public void exalt() {
        atb(new VFXAction(p(), new LargPortraitFlashInEffect(removeModID(ID)), ModSettings.EXALT_PROTRAIT_DURATION, true));

        atb(new KeyflareChangeAction(p(), 200));

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

        atb(new SelectCardsCenteredAction(getAllPosseCards(), "Select a Posse", cards -> {
            originPosse = ((AbstractAwakener) p()).getPosseID();
            ((AbstractAwakener) p()).setPosse((AbstractPosse) cards.get(0).makeCopy());
        }));
    }

    @Override
    public void overExalt() {
        atb(new VFXAction(p(), new LargPortraitFlashInEffect(removeModID(ID)), ModSettings.EXALT_PROTRAIT_DURATION, true));

        atb(new KeyflareChangeAction(p(), 200));

        applyToSelf(new NegentropyPower(p(), NEGENTROPY));

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

        atb(new SelectCardsCenteredAction(getAllPosseCards(), "Select a Posse", cards -> {
            originPosse = ((AbstractAwakener) p()).getPosseID();
            ((AbstractAwakener) p()).setPosse((AbstractPosse) cards.get(0).makeCopy());
        }));
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
