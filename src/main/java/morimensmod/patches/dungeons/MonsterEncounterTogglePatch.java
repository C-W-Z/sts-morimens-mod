package morimensmod.patches.dungeons;

import static morimensmod.MorimensMod.modID;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;
import java.util.Collections;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterInfo;

import javassist.CtBehavior;
import morimensmod.characters.AbstractAwakener;
import morimensmod.util.ModSettings;

public class MonsterEncounterTogglePatch {

    @SpirePatch2(clz = MonsterInfo.class, method = "normalizeWeights")
    public static class AddCustomMonstersPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void Postfix(@ByRef ArrayList<MonsterInfo>[] list) {
            if (ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER || p() instanceof AbstractAwakener)
                return;
            list[0].removeIf(m -> m.name.startsWith(modID));
        }

        // 插入點設在 sort 之後
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethod) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(Collections.class, "sort");
                int[] lines = LineFinder.findInOrder(ctMethod, matcher);
                return new int[] { lines[0] + 1 };
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { String.class, String.class,
            AbstractPlayer.class, ArrayList.class })
    public static class AddBossesPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Postfix() {
            if (ModSettings.OTHER_CHAR_ENCOUNTER_MOD_MONSTER || p() instanceof AbstractAwakener)
                return;
            AbstractDungeon.bossList.removeIf(id -> id.startsWith(modID));
        }

        // 插入點設在 setBoss 之前
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethod) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "setBoss");
                return LineFinder.findInOrder(ctMethod, matcher);
            }
        }
    }
}
