package morimensmod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.ReflectionHacks;
import javassist.CtBehavior;

@SpirePatch2(clz = AbstractCreature.class, method = "addBlock")
public class UnlimitedBlockPatch {

    @SpireInsertPatch(locator = Locator.class, localvars = { "effect" })
    public static SpireReturn<Void> Insert(AbstractCreature __instance, int blockAmount, boolean effect,
            Color ___blockTextColor, float ___blockScale) {
        if (__instance.currentBlock >= 99 && __instance.isPlayer)
            UnlockTracker.unlockAchievement("IMPERVIOUS");
        if (__instance.currentBlock > 999)
            UnlockTracker.unlockAchievement("BARRICADED");
        if (effect && __instance.currentBlock > 0) {
            ReflectionHacks
                    .privateMethod(AbstractCreature.class, "gainBlockAnimation")
                    .invoke(__instance);
        } else if (blockAmount > 0) {
            Color tmpCol = Settings.GOLD_COLOR.cpy();
            tmpCol.a = ___blockTextColor.a;
            ___blockTextColor = tmpCol;
            ___blockScale = 5.0F;
        }

        return SpireReturn.Return();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
            int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
            return new int[] { lines[0] + 1 };
        }
    }
}
