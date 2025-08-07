package morimensmod.patches.hooks;

import static morimensmod.util.Wiz.deck;
import static morimensmod.util.Wiz.drawPile;
import static morimensmod.util.Wiz.p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import javassist.CtBehavior;
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
            if (c instanceof PassiveCard && ((PassiveCard) c).onBattleStartPreDraw())
                AbstractDungeon.effectList.add(0, new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
        });
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "onVictory")
    public static class OnVictoryPatch {

        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance) {
            logger.debug("onVictory");
            deck().group.forEach(c -> {
                if (c instanceof PassiveCard && ((PassiveCard) c).onVictory(!p().isDying))
                    AbstractDungeon.effectList.add(0, new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            });
        }
    }

    @SpirePatch2(clz = CardGroup.class, method = "removeCard", paramtypez = { AbstractCard.class })
    public static class OnRemoveCardPatch {

        @SpirePostfixPatch
        public static void Postfix(CardGroup __instance, AbstractCard c) {
            if (__instance.type != CardGroupType.MASTER_DECK)
                return;
            logger.debug("onRemoveCardFromDeck");
            deck().group.forEach(card -> {
                if (card instanceof PassiveCard && ((PassiveCard) card).onRemoveCardFromDeck(c))
                    AbstractDungeon.effectList.add(0, new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
            });
        }
    }

    @SpirePatch2(clz = CampfireSleepEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {})
    public static class CampfireHealAmountPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(CampfireSleepEffect __instance, @ByRef int[] ___healAmount) {
            deck().group.forEach(card -> {
                if (card instanceof PassiveCard)
                    ___healAmount[0] = ((PassiveCard) card).onRestToChangeHealAmount(___healAmount[0]);
            });
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch2(clz = CampfireSleepEffect.class, method = "update")
    public static class OnRestPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(CampfireSleepEffect __instance) {
            deck().group.forEach(card -> {
                if (card instanceof PassiveCard && ((PassiveCard) card).onRest())
                    AbstractDungeon.effectList.add(0, new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
            });
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "heal");
                int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
                return new int[] { lines[0] + 1 };
            }
        }
    }
}
