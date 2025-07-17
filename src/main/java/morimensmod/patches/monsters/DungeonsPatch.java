package morimensmod.patches.monsters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;

import basemod.BaseMod;

public class DungeonsPatch {

    @SpirePatch2(clz = Exordium.class, method = "initializeBoss")
    public static class ExordiumPatch {
        @SpirePostfixPatch
        public static void Postfix(Exordium __instance) {
            // if (!ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER)
            // return;
            if (!BaseMod.getBossIDs(Exordium.ID).isEmpty())
                AbstractDungeon.bossList.clear();
        }
    }

    @SpirePatch2(clz = TheBeyond.class, method = "initializeBoss")
    public static class TheBeyondPatch {
        @SpirePostfixPatch
        public static void Postfix(TheBeyond __instance) {
            // if (!ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER)
            // return;
            if (!BaseMod.getBossIDs(TheBeyond.ID).isEmpty())
                AbstractDungeon.bossList.clear();
        }
    }

    @SpirePatch2(clz = TheCity.class, method = "initializeBoss")
    public static class TheCityPatch {
        @SpirePostfixPatch
        public static void Postfix(TheCity __instance) {
            // if (!ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER)
            // return;
            if (!BaseMod.getBossIDs(TheCity.ID).isEmpty())
                AbstractDungeon.bossList.clear();
        }
    }

    @SpirePatch2(clz = TheEnding.class, method = "initializeBoss")
    public static class TheEndingPatch {
        @SpirePostfixPatch
        public static void Postfix(TheEnding __instance) {
            // if (!ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER)
            // return;
            if (!BaseMod.getBossIDs(TheEnding.ID).isEmpty())
                AbstractDungeon.bossList.clear();
        }
    }

}
