package morimensmod.patches.monsterRoom;

import static morimensmod.util.Wiz.p;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.monsters.MonsterInfo;

import basemod.BaseMod;
import javassist.CtBehavior;
import morimensmod.characters.AbstractAwakener;
import morimensmod.config.ConfigPanel;

public class DungeonsPatch {

    @SpirePatch2(clz = Exordium.class, method = "initializeBoss")
    public static class ExordiumBossPatch {
        @SpirePostfixPatch
        public static void Postfix(Exordium __instance) {
            // if (!ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER)
            // return;
            if (!BaseMod.getBossIDs(Exordium.ID).isEmpty())
                AbstractDungeon.bossList.clear();
        }
    }

    @SpirePatch2(clz = TheBeyond.class, method = "initializeBoss")
    public static class TheBeyondBossPatch {
        @SpirePostfixPatch
        public static void Postfix(TheBeyond __instance) {
            // if (!ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER)
            // return;
            if (!BaseMod.getBossIDs(TheBeyond.ID).isEmpty())
                AbstractDungeon.bossList.clear();
        }
    }

    @SpirePatch2(clz = TheCity.class, method = "initializeBoss")
    public static class TheCityBossPatch {
        @SpirePostfixPatch
        public static void Postfix(TheCity __instance) {
            // if (!ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER)
            // return;
            if (!BaseMod.getBossIDs(TheCity.ID).isEmpty())
                AbstractDungeon.bossList.clear();
        }
    }

    @SpirePatch2(clz = TheEnding.class, method = "initializeBoss")
    public static class TheEndingBossPatch {
        @SpirePostfixPatch
        public static void Postfix(TheEnding __instance) {
            // if (!ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER)
            // return;
            if (!BaseMod.getBossIDs(TheEnding.ID).isEmpty())
                AbstractDungeon.bossList.clear();
        }
    }

    @SpirePatch2(clz = Exordium.class, method = "generateElites")
    public static class ExordiumElitePatch {
        @SpireInsertPatch(locator = Locator.class, localvars = { "monsters" })
        public static void Insert(Exordium __instance, @ByRef ArrayList<MonsterInfo>[] monsters) {
            if ((ConfigPanel.OTHER_CHAR_ENCOUNTER_MOD_MONSTER && !(p() instanceof AbstractAwakener)) ||
                (ConfigPanel.AWAKENER_ENCOUNTER_MOD_MONSTER && p() instanceof AbstractAwakener)) {
                monsters[0].clear();
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(MonsterInfo.class, "normalizeWeights");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}
