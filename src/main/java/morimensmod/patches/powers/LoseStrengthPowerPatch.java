package morimensmod.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;

import morimensmod.config.ConfigPanel;
import morimensmod.util.TexLoader;

@SpirePatch2(clz = LoseStrengthPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
        int.class })
public class LoseStrengthPowerPatch {
    @SpirePostfixPatch
    public static void Postfix(LoseStrengthPower __instance) {
        if (ConfigPanel.USE_MORIMENS_POWER_ICON)
            TexLoader.loadRegion(__instance);
    }
}
