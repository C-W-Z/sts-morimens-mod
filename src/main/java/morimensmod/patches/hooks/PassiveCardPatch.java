package morimensmod.patches.hooks;

import static morimensmod.util.Wiz.deck;
import static morimensmod.util.Wiz.drawPile;
import static morimensmod.util.Wiz.p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.PassiveCard;

public class PassiveCardPatch {

    private static final Logger logger = LogManager.getLogger(PassiveCardPatch.class);

    @SpirePatch2(clz = CardGroup.class, method = "initializeDeck")
    public static class InitDeckPatch {

        @SpirePostfixPatch
        public static void Postfix(CardGroup __instance, CardGroup masterDeck) {
            if (__instance != drawPile())
                return;

            AbstractAwakener.onInitializeDeck();
            AbstractEasyCard.onInitializeDeck();
            if (p() instanceof AbstractAwakener)
                ((AbstractAwakener) p()).getExalt().onInitDeck();

            logger.debug("onInitDeck");
            __instance.group.forEach(c -> {
                if (c instanceof PassiveCard && ((PassiveCard) c).onInitDeck())
                    AbstractDungeon.effectList.add(0, new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            });
            __instance.group.removeIf(c -> c instanceof PassiveCard);
        }
    }

    // Called in Main Mod File
    public static void onBattleStartPreDraw() {
        logger.debug("onBattleStartPreDraw");
        deck().group.forEach(c -> {
            if (c instanceof PassiveCard)
                ((PassiveCard) c).onBattleStartPreDraw();
        });
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "onVictory")
    public static class OnVictoryPatch {

        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance) {
            logger.debug("onVictory");
            deck().group.forEach(c -> {
                if (c instanceof PassiveCard)
                    ((PassiveCard) c).onVictory(!p().isDying);
            });
        }
    }
}
