package morimensmod.patches.hooks;

import static morimensmod.util.Wiz.deck;
import static morimensmod.util.Wiz.drawPile;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;

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
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.DreamCatcher;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import javassist.CtBehavior;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.PassiveCard;

public class PassiveCardPatch {

    private static final Logger logger = LogManager.getLogger(PassiveCardPatch.class);

    @SpirePatch2(clz = CardGroup.class, method = "initializeDeck")
    public static class OnInitDeckPatch {

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

        @SpirePrefixPatch
        public static void Prefix(CardGroup __instance, AbstractCard c) {
            if (__instance.type != CardGroupType.MASTER_DECK)
                return;
            logger.debug("preRemoveCardFromDeck" + c.cardID);
            deck().group.forEach(card -> {
                if (card instanceof PassiveCard)
                    ((PassiveCard) card).preRemoveCardFromDeck(c);
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

    @SpirePatch2(clz = RestOption.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { boolean.class })
    public static class RestOptionTextPatch {

        @SpireInsertPatch(locator = Locator.class, localvars = { "healAmt" })
        public static void Insert(@ByRef String[] ___description, int healAmt) {
            deck().group.forEach(card -> {
                if (card instanceof PassiveCard) {
                    int newhealAmt = ((PassiveCard) card).onRestToChangeHealAmount(healAmt);
                    int diffHealAmt = newhealAmt - healAmt;
                    if (diffHealAmt != 0) {
                        ___description[0] += String.format("\n%+d", diffHealAmt) +
                                RestOption.TEXT[2] + card.name + LocalizedStrings.PERIOD;
                    }
                }
            });
        }

        // 插入所有hasRelic前一行
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findAllInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch2(clz = CampfireSleepEffect.class, method = "update")
    public static class OnRestPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Insert(CampfireSleepEffect __instance) {

            ArrayList<RewardItem> rewards = new ArrayList<>();
            deck().group.forEach(card -> {
                if (card instanceof PassiveCard) {
                    ArrayList<RewardItem> r = ((PassiveCard) card).onRestToObtainRewards();
                    if (r != null && !r.isEmpty())
                        rewards.addAll(r);
                }
            });

            if (rewards.isEmpty())
                return SpireReturn.Continue();

            if (AbstractDungeon.player.hasRelic(DreamCatcher.ID)) {
                AbstractDungeon.player.getRelic(DreamCatcher.ID).flash();
                ArrayList<AbstractCard> rewardCards = AbstractDungeon.getRewardCards();
                if (rewardCards != null && !rewardCards.isEmpty()) {
                    RewardItem reward = new RewardItem();
                    reward.type = RewardType.CARD;
                    reward.cards = rewardCards;
                    AbstractDungeon.getCurrRoom().rewards.add(reward);
                }
            }
            AbstractDungeon.getCurrRoom().rewards.addAll(rewards);
            AbstractDungeon.combatRewardScreen.open();
            __instance.isDone = true;
            ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
            AbstractRoom.waitTimer = 0.0F;
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;

            return SpireReturn.Return();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, matcher);
                return new int[] { lines[1] };
            }
        }
    }
}
