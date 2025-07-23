package morimensmod.patches.enums;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AEQUOR_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.BUFF_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CARO_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.DERIVATIVE_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.POSSE_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.STATUS_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.SYMPTOM_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.ULTRA_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;

import java.util.ArrayList;
import javassist.CtBehavior;

@SpirePatch2(clz = ColorTabBarFix.Render.class, method = "Insert")
public class TabNamePatch {
    private static final UIStrings CHAOS_STRINGS = CardCrawlGame.languagePack.getUIString(CHAOS_COLOR.name());
    private static final UIStrings AEQUOR_STRINGS = CardCrawlGame.languagePack.getUIString(AEQUOR_COLOR.name());
    private static final UIStrings CARO_STRINGS = CardCrawlGame.languagePack.getUIString(CARO_COLOR.name());
    private static final UIStrings ULTRA_STRINGS = CardCrawlGame.languagePack.getUIString(ULTRA_COLOR.name());
    private static final UIStrings BUFF_STRINGS = CardCrawlGame.languagePack.getUIString(BUFF_COLOR.name());
    private static final UIStrings WHEEL_OF_DESTINY_STRINGS = CardCrawlGame.languagePack.getUIString(WHEEL_OF_DESTINY_COLOR.name());
    private static final UIStrings SYMPTOM_STRINGS = CardCrawlGame.languagePack.getUIString(SYMPTOM_COLOR.name());
    private static final UIStrings STATUS_STRINGS = CardCrawlGame.languagePack.getUIString(STATUS_COLOR.name());
    private static final UIStrings POSSE_STRINGS = CardCrawlGame.languagePack.getUIString(POSSE_COLOR.name());
    private static final UIStrings DERIVATIVE_STRINGS = CardCrawlGame.languagePack.getUIString(DERIVATIVE_COLOR.name());

    @SpireInsertPatch(locator = Locator.class, localvars = { "i", "tabName" })
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
        else if (modTabs.get(i).color == BUFF_COLOR)
            tabName[0] = BUFF_STRINGS.TEXT[0];
        else if (modTabs.get(i).color == WHEEL_OF_DESTINY_COLOR)
            tabName[0] = WHEEL_OF_DESTINY_STRINGS.TEXT[0];
        else if (modTabs.get(i).color == SYMPTOM_COLOR)
            tabName[0] = SYMPTOM_STRINGS.TEXT[0];
        else if (modTabs.get(i).color == STATUS_COLOR)
            tabName[0] = STATUS_STRINGS.TEXT[0];
        else if (modTabs.get(i).color == POSSE_COLOR)
            tabName[0] = POSSE_STRINGS.TEXT[0];
        else if (modTabs.get(i).color == DERIVATIVE_COLOR)
            tabName[0] = DERIVATIVE_STRINGS.TEXT[0];
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(FontHelper.class,
                    "renderFontCentered");
            return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
        }
    }
}
