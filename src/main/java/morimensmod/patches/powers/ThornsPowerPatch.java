package morimensmod.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.ThornsPower;

import morimensmod.config.ConfigPanel;
import morimensmod.util.TexLoader;

@SpirePatch2(clz = ThornsPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
        int.class })
public class ThornsPowerPatch {
    @SpirePostfixPatch
    public static void Postfix(ThornsPower __instance) {
        if (ConfigPanel.USE_MORIMENS_POWER_ICON)
            TexLoader.loadRegion(__instance);
    }
}
