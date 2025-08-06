package morimensmod.patches;

import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

import javassist.CtBehavior;
import morimensmod.characters.AbstractAwakener;
import morimensmod.rewards.WheelOfDestinyReward_Random;

public class WheelOfDestinyRewardPatch {

    @SpirePatch2(clz = AbstractRoom.class, method = "endBattle")
    public static class BossRewardPatch {
        @SpirePostfixPatch
        public static void Postifx(AbstractRoom __instance) {
            if (!(p() instanceof AbstractAwakener))
                return;
            if (__instance instanceof MonsterRoomBoss) {
                WheelOfDestinyReward_Random reward = new WheelOfDestinyReward_Random(3);
                if (reward.isValid())
                    __instance.rewards.add(reward);
            }
        }
    }

    @SpirePatch2(clz = MonsterRoomElite.class, method = "dropReward")
    public static class EliteRewardPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(MonsterRoomElite __instance) {
            if (!(p() instanceof AbstractAwakener))
                return;
            // WheelOfDestinyReward_Uncommon reward = new WheelOfDestinyReward_Uncommon(3);
            // if (reward.isValid())
            // __instance.rewards.add(reward);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(MonsterRoomElite.class, "addEmeraldKey");
                int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
                return new int[] { lines[0] + 1 };
            }
        }
    }
}
