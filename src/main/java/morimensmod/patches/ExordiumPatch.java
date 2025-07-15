package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;

@SpirePatch2(clz = Exordium.class, method = "initializeBoss")
public class ExordiumPatch {
    @SpirePostfixPatch
    public static void Postfix(Exordium __instance) {
        AbstractDungeon.bossList.clear();
    }
}
