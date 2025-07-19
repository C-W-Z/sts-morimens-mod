package morimensmod.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.ReflectionHacks;
import javassist.CtBehavior;
import morimensmod.config.ConfigPanel;
import morimensmod.util.TexLoader;
import static morimensmod.util.Wiz.att;

public class StrengthPowerPatch {

    @SpirePatch2(clz = StrengthPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
            int.class })
    public static class ConstructorPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Insert(StrengthPower __instance, AbstractCreature owner, int amount) {
            __instance.amount = amount;
            __instance.updateDescription();
            __instance.canGoNegative = true;

            if (ConfigPanel.USE_MORIMENS_POWER_ICON)
                TexLoader.loadRegion(__instance);
            else
                ReflectionHacks.privateMethod(AbstractPower.class, "loadRegion", String.class)
                        .invoke(__instance, "strength");

            return SpireReturn.Return();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctConstructor) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(StrengthPower.class, "amount");
                return LineFinder.findInOrder(ctConstructor, matcher);
            }
        }
    }

    @SpirePatch2(clz = StrengthPower.class, method = "stackPower")
    public static class StackPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Rewrite(StrengthPower __instance, int stackAmount, float ___fontScale) {
            ___fontScale += 8.0F;
            __instance.amount += stackAmount;
            if (__instance.amount == 0)
                att(new RemoveSpecificPowerAction(__instance.owner, __instance.owner, StrengthPower.POWER_ID));

            if (__instance.amount >= 50 && __instance.owner == AbstractDungeon.player)
                UnlockTracker.unlockAchievement("JAXXED");

            return SpireReturn.Return();
        }
    }

    @SpirePatch2(clz = StrengthPower.class, method = "reducePower")
    public static class ReducePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Rewrite(StrengthPower __instance, int reduceAmount, float ___fontScale) {
            ___fontScale += 8.0F;
            __instance.amount -= reduceAmount;
            if (__instance.amount == 0)
                att(new RemoveSpecificPowerAction(__instance.owner, __instance.owner, StrengthPower.POWER_ID));

            return SpireReturn.Return();
        }
    }
}
