package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;

import java.util.ArrayList;
import javassist.CtBehavior;

import static morimensmod.patches.RealmColorPatch.CardColorPatch.CHAOS_COLOR;

public class RealmTabNamePatch {
    @SpirePatch2(clz = ColorTabBarFix.Render.class, method = "Insert")
    public static class TabNamePatch {
        private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(CHAOS_COLOR.name());

        @SpireInsertPatch(locator = TabNameLocator.class, localvars = { "i", "tabName" })
        public static void InsertFix(int i, @ByRef String[] tabName) {
            ArrayList<ColorTabBarFix.ModColorTab> modTabs = ReflectionHacks
                    .getPrivateStatic(ColorTabBarFix.Fields.class, "modTabs");
            if (modTabs.get(i).color == CHAOS_COLOR) {
                tabName[0] = uiStrings.TEXT[0];
            }
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
