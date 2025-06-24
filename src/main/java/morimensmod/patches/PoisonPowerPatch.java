package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import javassist.CtBehavior;
import morimensmod.powers.DrowningInSorrowPower;
import static morimensmod.util.Wiz.atb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoisonPowerPatch {

    private static final Logger logger = LogManager.getLogger(PoisonPowerPatch.class);

    @SpirePatch2(clz = PoisonPower.class, method = "atStartOfTurn")
    public static class PoisonPowerAtStartOfTurnPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> stopStartTrigger(PoisonPower __instance) {
            if (__instance.owner.hasPower(DrowningInSorrowPower.POWER_ID)) {
                logger.debug("skip poison action atStartOfTurn");
                return SpireReturn.Return(); // 跳過原本的邏輯
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior behavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(PoisonPower.class, "flashWithoutSound");
                return LineFinder.findInOrder(behavior, matcher);
            }
        }
    }

    @SpirePatch2(clz = AbstractPower.class, method = "atEndOfTurn")
    public static class PoisonPowerAtEndOfTurnPatch {
        @SpirePrefixPatch
        public static void prefix(AbstractPower __instance, boolean isPlayer) {
            if (!(__instance instanceof PoisonPower))
                return;
            if (!__instance.owner.hasPower(DrowningInSorrowPower.POWER_ID))
                return;
            if (AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT
                    || AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                return;
            logger.debug("apply poison action atEndOfTurn");
            __instance.flashWithoutSound();
            atb(new PoisonLoseHpAction(__instance.owner, __instance.owner, __instance.amount, AttackEffect.POISON));
        }
    }
}
