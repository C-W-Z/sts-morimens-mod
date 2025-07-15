package morimensmod.patches.dungeons;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;

import basemod.BaseMod;
import morimensmod.util.ModSettings;

@SpirePatch2(clz = Exordium.class, method = "initializeBoss")
public class ExordiumPatch {
    @SpirePostfixPatch
    public static void Postfix(Exordium __instance) {
        if (!ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER)
            return;
        if (!BaseMod.getBossIDs(Exordium.ID).isEmpty())
            AbstractDungeon.bossList.clear();
    }
}
