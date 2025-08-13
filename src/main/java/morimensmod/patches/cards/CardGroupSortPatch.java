package morimensmod.patches.cards;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.isMorimensModCardColor;

import java.util.Collections;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.compendium.CardLibSortHeader;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;

import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;
import javassist.CtBehavior;

public class CardGroupSortPatch {

    @SpirePatch2(cls = "com.megacrit.cardcrawl.cards.CardGroup$CardNameComparator", method = "compare", paramtypez = {
            AbstractCard.class, AbstractCard.class })
    public static class CardNameSortPatch {
        @SpirePrefixPatch
        public static SpireReturn<Integer> Prefix(AbstractCard c1, AbstractCard c2) {
            // if (c1 instanceof AbstractPosse && c2 instanceof AbstractPosse)
            // return SpireReturn.Return(c1.compareTo(c2));
            // if (c1 instanceof AbstractEasyCard && c2 instanceof AbstractEasyCard)
            // return SpireReturn.Return(c1.compareTo(c2));
            // return SpireReturn.Continue();
            return SpireReturn.Return(c1.compareTo(c2));
        }
    }

    @SpirePatches2({
            @SpirePatch2(cls = "com.megacrit.cardcrawl.cards.CardGroup$CardTypeComparator", method = "compare", paramtypez = {
                    AbstractCard.class, AbstractCard.class }),
            @SpirePatch2(cls = "com.megacrit.cardcrawl.cards.CardGroup$CardCostComparator", method = "compare", paramtypez = {
                    AbstractCard.class, AbstractCard.class }),
            @SpirePatch2(cls = "com.megacrit.cardcrawl.cards.CardGroup$CardRarityComparator", method = "compare", paramtypez = {
                    AbstractCard.class, AbstractCard.class })
    })
    public static class CardSortPatch {
        @SpirePostfixPatch
        public static int Postfix(int __result, AbstractCard c1, AbstractCard c2) {
            if (__result != 0)
                return __result;
            return c1.compareTo(c2);
        }
    }

    @SpirePatch2(clz = CardLibraryScreen.class, method = "didChangeTab")
    public static class SortOnChangeTabPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(CardLibraryScreen __instance, ColorTabBar tabBar, ColorTabBar.CurrentTab newSelection,
                CardLibSortHeader ___sortHeader) {
            if (newSelection == ColorTabBarFix.Enums.MOD && isMorimensModCardColor((ColorTabBarFix.Fields.getModTab()).color)) {
                Collections.sort(___sortHeader.group.group);
                ___sortHeader.clearActiveButtons();
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(CardLibraryScreen.class, "calculateScrollBounds");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}
