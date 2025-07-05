package morimensmod.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import morimensmod.util.TexLoader;

@SpirePatch2(clz = LoseDexterityPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
        int.class })
public class LoseDexterityPowerPatch {
    @SpirePostfixPatch
    public static void Postfix(LoseDexterityPower __instance) {
        TexLoader.loadRegion(__instance);
    }
}
