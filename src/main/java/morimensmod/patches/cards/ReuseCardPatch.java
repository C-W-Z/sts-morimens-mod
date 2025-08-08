package morimensmod.patches.cards;

import java.util.ArrayList;
import java.util.Iterator;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField.ExhaustiveFields;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import javassist.CtBehavior;

public class ReuseCardPatch {

    private static void restoreReuseTimes(AbstractCard c) {
        int reuseTimes = ExhaustiveFields.baseExhaustive.get(c);
        if (reuseTimes > 0) {
            ExhaustiveFields.exhaustive.set(c, reuseTimes);
            c.exhaust = false;
        }
    }

    private static void restoreOneReuseTime(AbstractCard c) {
        // check whether is Reusable card or not
        int reuseTimes = ExhaustiveFields.baseExhaustive.get(c);
        if (reuseTimes > 0) {
            ExhaustiveFields.exhaustive.set(c, 1);
            c.exhaust = false;
        }
    }

    @SpirePatch2(clz = CardGroup.class, method = "removeCard", paramtypez = { AbstractCard.class })
    public static class RemoveCardPatch1 {
        @SpirePrefixPatch
        public static void Prefix(CardGroup __instance, AbstractCard c) {
            if (__instance.type != CardGroupType.EXHAUST_PILE)
                return;
            restoreReuseTimes(c);
        }
    }

    @SpirePatch2(clz = CardGroup.class, method = "removeCard", paramtypez = { String.class })
    public static class RemoveCardPatch2 {
        @SpireInsertPatch(locator = Locator.class, localvars = { "e" })
        public static void Insert(CardGroup __instance, String targetID, AbstractCard e) {
            if (__instance.type != CardGroupType.EXHAUST_PILE)
                return;
            restoreReuseTimes(e);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(Iterator.class, "remove");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch2(clz = CardGroup.class, method = "removeTopCard")
    public static class RemoveTopCardPatch {
        @SpirePrefixPatch
        public static void Prefix(CardGroup __instance) {
            if (__instance.type != CardGroupType.EXHAUST_PILE || __instance.group.isEmpty())
                return;
            AbstractCard topCard = __instance.group.get(__instance.group.size() - 1);
            restoreReuseTimes(topCard);
        }
    }

    // resetCardBeforeMoving在moveToExhaustPile裡也有呼叫
    @SpirePatch2(clz = CardGroup.class, method = "resetCardBeforeMoving")
    public static class ResetCardBeforeMovingPatch2 {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(CardGroup __instance, AbstractCard c) {
            if (__instance.type != CardGroupType.EXHAUST_PILE)
                return;
            restoreReuseTimes(c);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(ArrayList.class, "remove");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    // Strange Spoon生效要恢復1次使用次數
    @SpirePatch2(clz = UseCardAction.class, method = "update")
    public static class UseCardActionPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(UseCardAction __instance, AbstractCard ___targetCard) {
            restoreOneReuseTime(___targetCard);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "getRelic");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}
