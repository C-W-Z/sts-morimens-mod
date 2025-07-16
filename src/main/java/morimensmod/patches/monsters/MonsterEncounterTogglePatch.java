package morimensmod.patches.monsters;

import static morimensmod.MorimensMod.modID;
import static morimensmod.util.Wiz.p;

import java.util.List;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.monsters.MonsterInfo;

import basemod.BaseMod;
import morimensmod.characters.AbstractAwakener;
import morimensmod.config.ConfigPanel;

public class MonsterEncounterTogglePatch {

    @SpirePatch2(clz = BaseMod.class, method = "getMonsterEncounters")
    public static class GetMonsterEncountersPatch {
        @SpirePostfixPatch
        public static List<MonsterInfo> Postfix(List<MonsterInfo> __result) {
            if (ConfigPanel.OTHER_CHAR_ENCOUNTER_MOD_MONSTER || p() instanceof AbstractAwakener)
                return __result;
            __result.removeIf(m -> m.name.startsWith(modID));
            return __result;
        }
    }

    @SpirePatch2(clz = BaseMod.class, method = "getStrongMonsterEncounters")
    public static class GetStrongMonsterEncountersPatch {
        @SpirePostfixPatch
        public static List<MonsterInfo> Postfix(List<MonsterInfo> __result) {
            if (ConfigPanel.OTHER_CHAR_ENCOUNTER_MOD_MONSTER || p() instanceof AbstractAwakener)
                return __result;
            __result.removeIf(m -> m.name.startsWith(modID));
            return __result;
        }
    }

    @SpirePatch2(clz = BaseMod.class, method = "getEliteEncounters")
    public static class GetEliteEncountersPatch {
        @SpirePostfixPatch
        public static List<MonsterInfo> Postfix(List<MonsterInfo> __result) {
            if (ConfigPanel.OTHER_CHAR_ENCOUNTER_MOD_MONSTER || p() instanceof AbstractAwakener)
                return __result;
            __result.removeIf(m -> m.name.startsWith(modID));
            return __result;
        }
    }

    @SpirePatch2(clz = BaseMod.class, method = "getBossIDs")
    public static class GetBossIDsPatch {
        @SpirePostfixPatch
        public static List<String> Postfix(List<String> __result) {
            if (ConfigPanel.OTHER_CHAR_ENCOUNTER_MOD_MONSTER || p() instanceof AbstractAwakener)
                return __result;
            __result.removeIf(id -> id.startsWith(modID));
            return __result;
        }
    }

    // @SpirePatch(clz = AbstractDungeon.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { String.class, String.class,
    //         AbstractPlayer.class, ArrayList.class })
    // public static class AddBossesPatch {
    //     @SpireInsertPatch(locator = Locator.class)
    //     public static void Postfix() {
    //         if (ConfigPanel.OTHER_CHAR_ENCOUNTER_MOD_MONSTER || p() instanceof AbstractAwakener)
    //             return;
    //         AbstractDungeon.bossList.removeIf(id -> id.startsWith(modID));
    //     }

    //     // 插入點設在 setBoss 之前
    //     private static class Locator extends SpireInsertLocator {
    //         @Override
    //         public int[] Locate(CtBehavior ctMethod) throws Exception {
    //             Matcher matcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "setBoss");
    //             return LineFinder.findInOrder(ctMethod, matcher);
    //         }
    //     }
    // }
}
