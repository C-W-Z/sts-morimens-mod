package morimensmod.patches.dungeons;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;

import basemod.BaseMod;

@SpirePatch2(clz = TheEnding.class, method = "initializeBoss")
public class TheEndingPatch {
    @SpirePostfixPatch
    public static void Postfix(TheEnding __instance) {
        if (!BaseMod.getBossIDs(TheEnding.ID).isEmpty())
            AbstractDungeon.bossList.clear();
    }
}
