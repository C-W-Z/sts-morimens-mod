package morimensmod.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import morimensmod.util.TexLoader;

@SpirePatch2(clz = VulnerablePower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
        int.class, boolean.class })
public class VulnerablePowerPatch {
    @SpirePostfixPatch
    public static void Postfix(VulnerablePower __instance) {
        TexLoader.loadRegion(__instance);
    }
}
