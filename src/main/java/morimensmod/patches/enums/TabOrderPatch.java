package morimensmod.patches.enums;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;

import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix.ModColorTab;

import com.megacrit.cardcrawl.screens.mainMenu.TabBarListener;
import java.util.ArrayList;
import morimensmod.patches.enums.ColorPatch.CardColorPatch;

@SpirePatch2(clz = ColorTabBarFix.Ctor.class, method = "Postfix")
public class TabOrderPatch {

    @SpirePostfixPatch
    public static void Postfix(TabBarListener delegate) {
        ArrayList<ModColorTab> modTabs = ReflectionHacks
                .getPrivateStatic(ColorTabBarFix.Fields.class, "modTabs");

        ArrayList<ModColorTab> newButton = new ArrayList<>();

        for (ModColorTab button : modTabs)
            if (button.color == CardColorPatch.POSSE_COLOR) {
                newButton.add(button);
                break;
            }

        for (ModColorTab button : modTabs)
            if (button.color == CardColorPatch.CHAOS_COLOR) {
                newButton.add(button);
                break;
            }

        for (ModColorTab button : modTabs)
            if (button.color == CardColorPatch.AEQUOR_COLOR) {
                newButton.add(button);
                break;
            }

        for (ModColorTab button : modTabs)
            if (button.color == CardColorPatch.CARO_COLOR) {
                newButton.add(button);
                break;
            }

        for (ModColorTab button : modTabs)
            if (button.color == CardColorPatch.ULTRA_COLOR) {
                newButton.add(button);
                break;
            }

        for (ModColorTab button : modTabs)
            if (button.color == CardColorPatch.WHEEL_OF_DESTINY_COLOR) {
                newButton.add(button);
                break;
            }

        for (ModColorTab button : modTabs)
            if (button.color == CardColorPatch.BUFF_COLOR) {
                newButton.add(button);
                break;
            }

        for (ModColorTab button : modTabs)
            if (button.color == CardColorPatch.SYMPTOM_COLOR) {
                newButton.add(button);
                break;
            }

        for (ModColorTab button : modTabs)
            if (button.color == CardColorPatch.STATUS_COLOR) {
                newButton.add(button);
                break;
            }

        modTabs.removeIf(button -> CardColorPatch.isMorimensModCardColor(button.color));
        modTabs.addAll(0, newButton);
        newButton.clear();
    }

    // @SpirePatch(clz = MasterDeckSortHeader.class, method = "render")
    // public static class MasterDeckSortHeaderColorPatch {
    //     @SpireInsertPatch(rloc = 14)
    //     public static void Insert(MasterDeckSortHeader __instance, SpriteBatch sb) {
    //         if (!BaseMod.isBaseGameCharacter(AbstractDungeon.player))
    //             sb.setColor(AbstractDungeon.player.getCardRenderColor());
    //     }
    // }
}