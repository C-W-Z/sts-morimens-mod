package morimensmod.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.PoisonPower;

import morimensmod.util.TexLoader;

@SpirePatch2(clz = PoisonPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
        AbstractCreature.class, int.class })
public class PoisonPowerPatch {
    @SpirePostfixPatch
    public static void Postfix(PoisonPower __instance) {
        TexLoader.loadRegion(__instance);
    }
}
