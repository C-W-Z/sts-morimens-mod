package morimensmod.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.config.ConfigPanel;
import morimensmod.util.TexLoader;

@SpirePatch2(clz = WeakPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
        int.class, boolean.class })
public class WeakPowerPatch {
    @SpirePostfixPatch
    public static void Postfix(WeakPower __instance) {
        if (ConfigPanel.USE_MORIMENS_POWER_ICON)
            TexLoader.loadRegion(__instance);
    }
}
