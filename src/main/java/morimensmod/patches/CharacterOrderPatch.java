package morimensmod.patches;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import basemod.CustomCharacterSelectScreen;
import basemod.ReflectionHacks;
import morimensmod.characters.*;

@SpirePatch2(clz = CharacterSelectScreen.class, method = "open")
public class CharacterOrderPatch {

    // 角色顯示順序（類別順序）
    public static final List<Class<?>> order = Arrays.asList(
            Ironclad.class,
            TheSilent.class,
            Defect.class,
            Watcher.class,
            Ramona.class,
            RamonaTimeworm.class,
            Lotan.class);

    @SpirePrefixPatch
    public static void Prefix(CharacterSelectScreen __instance) {
        if (!(__instance instanceof CustomCharacterSelectScreen))
            return;

        ArrayList<CharacterOption> allOptions = ReflectionHacks.getPrivate(
                __instance, CustomCharacterSelectScreen.class, "allOptions");

        allOptions.sort(Comparator.comparingInt(o -> {
            int idx = order.indexOf(o.c.getClass());
            return idx == -1 ? Integer.MAX_VALUE : idx;
        }));

        ReflectionHacks.setPrivate(__instance, CustomCharacterSelectScreen.class, "allOptions", allOptions);

        int optionsIndex = ReflectionHacks.getPrivate(__instance, CustomCharacterSelectScreen.class, "optionsIndex");
        int optionsPerIndex = ReflectionHacks.getPrivate(__instance, CustomCharacterSelectScreen.class,
                "optionsPerIndex");
        __instance.options = new ArrayList<>(allOptions.subList(
                optionsIndex, Math.min(allOptions.size(), optionsIndex + optionsPerIndex)));
        __instance.options.forEach(o -> o.selected = false);

        try {
            ReflectionHacks.getCachedMethod(CustomCharacterSelectScreen.class, "positionButtons")
                    .invoke(__instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
