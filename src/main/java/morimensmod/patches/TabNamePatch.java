package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;

import java.util.ArrayList;
import javassist.CtBehavior;

import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.patches.ColorPatch.CardColorPatch.AEQUOR_COLOR;
import static morimensmod.patches.ColorPatch.CardColorPatch.CARO_COLOR;
import static morimensmod.patches.ColorPatch.CardColorPatch.ULTRA_COLOR;

public class TabNamePatch {
    @SpirePatch2(clz = ColorTabBarFix.Render.class, method = "Insert")
    public static class _TabNamePatch {
        private static final UIStrings CHAOS_STRINGS = CardCrawlGame.languagePack.getUIString(CHAOS_COLOR.name());
        private static final UIStrings AEQUOR_STRINGS = CardCrawlGame.languagePack.getUIString(AEQUOR_COLOR.name());
        private static final UIStrings CARO_STRINGS = CardCrawlGame.languagePack.getUIString(CARO_COLOR.name());
        private static final UIStrings ULTRA_STRINGS = CardCrawlGame.languagePack.getUIString(ULTRA_COLOR.name());

        @SpireInsertPatch(locator = TabNameLocator.class, localvars = { "i", "tabName" })
        public static void InsertFix(int i, @ByRef String[] tabName) {
            ArrayList<ColorTabBarFix.ModColorTab> modTabs = ReflectionHacks
                    .getPrivateStatic(ColorTabBarFix.Fields.class, "modTabs");
            if (modTabs.get(i).color == CHAOS_COLOR)
                tabName[0] = CHAOS_STRINGS.TEXT[0];
            else if (modTabs.get(i).color == AEQUOR_COLOR)
                tabName[0] = AEQUOR_STRINGS.TEXT[0];
            else if (modTabs.get(i).color == CARO_COLOR)
                tabName[0] = CARO_STRINGS.TEXT[0];
            else if (modTabs.get(i).color == ULTRA_COLOR)
                tabName[0] = ULTRA_STRINGS.TEXT[0];
        }

        private static class TabNameLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(FontHelper.class,
                        "renderFontCentered");
                return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
            }
        }
    }
}
