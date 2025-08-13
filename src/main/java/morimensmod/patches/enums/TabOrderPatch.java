package morimensmod.patches.enums;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;

import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix.ModColorTab;

import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.screens.mainMenu.TabBarListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import morimensmod.patches.enums.ColorPatch.CardColorPatch;

@SpirePatch2(clz = ColorTabBarFix.Ctor.class, method = "Postfix")
public class TabOrderPatch {

    public static final List<CardColor> orderedColors = Arrays.asList(
            CardColorPatch.POSSE_COLOR,
            CardColorPatch.CHAOS_COLOR,
            CardColorPatch.AEQUOR_COLOR,
            CardColorPatch.CARO_COLOR,
            CardColorPatch.ULTRA_COLOR,
            CardColorPatch.DERIVATIVE_COLOR,
            CardColorPatch.WHEEL_OF_DESTINY_COLOR,
            CardColorPatch.BUFF_COLOR,
            CardColorPatch.SYMPTOM_COLOR,
            CardColorPatch.STATUS_COLOR
    );

    @SpirePostfixPatch
    public static void Postfix(TabBarListener delegate) {
        ArrayList<ModColorTab> modTabs = ReflectionHacks
                .getPrivateStatic(ColorTabBarFix.Fields.class, "modTabs");

        // 依順序抽出對應 tab
        List<ModColorTab> newButton = orderedColors.stream()
                .map(color -> modTabs.stream()
                        .filter(button -> button.color == color)
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        modTabs.removeIf(button -> CardColorPatch.isMorimensModCardColor(button.color));
        modTabs.addAll(0, newButton);
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
