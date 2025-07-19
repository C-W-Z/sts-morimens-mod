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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

import basemod.ReflectionHacks;
import javassist.CtBehavior;
import morimensmod.config.ConfigPanel;
import morimensmod.util.TexLoader;
import static morimensmod.util.Wiz.att;

public class DexterityPowerPatch {

    @SpirePatch2(clz = DexterityPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
            int.class })
    public static class ConstructorPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Insert(DexterityPower __instance, AbstractCreature owner, int amount) {
            __instance.amount = amount;
            __instance.updateDescription();
            __instance.canGoNegative = true;

            if (ConfigPanel.USE_MORIMENS_POWER_ICON)
                TexLoader.loadRegion(__instance);
            else
                ReflectionHacks.privateMethod(AbstractPower.class, "loadRegion", String.class)
                        .invoke(__instance, "dexterity");

            return SpireReturn.Return();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctConstructor) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(DexterityPower.class, "amount");
                return LineFinder.findInOrder(ctConstructor, matcher);
            }
        }
    }

    @SpirePatch2(clz = DexterityPower.class, method = "stackPower")
    public static class StackPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Rewrite(DexterityPower __instance, int stackAmount, float ___fontScale) {
            ___fontScale += 8.0F;
            __instance.amount += stackAmount;
            if (__instance.amount == 0)
                att(new RemoveSpecificPowerAction(__instance.owner, __instance.owner, DexterityPower.POWER_ID));

            return SpireReturn.Return();
        }
    }

    @SpirePatch2(clz = DexterityPower.class, method = "reducePower")
    public static class ReducePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Rewrite(DexterityPower __instance, int reduceAmount, float ___fontScale) {
            ___fontScale += 8.0F;
            __instance.amount -= reduceAmount;
            if (__instance.amount == 0)
                att(new RemoveSpecificPowerAction(__instance.owner, __instance.owner, DexterityPower.POWER_ID));

            return SpireReturn.Return();
        }
    }
}
