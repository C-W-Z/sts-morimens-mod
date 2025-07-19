package morimensmod.patches.powers;

import static morimensmod.util.Wiz.att;

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
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.GainStrengthPower;

import basemod.ReflectionHacks;
import javassist.CtBehavior;
import morimensmod.config.ConfigPanel;
import morimensmod.util.TexLoader;

public class GainStrengthPowerPatch {

    @SpirePatch2(clz = GainStrengthPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
            int.class })
    public static class ConstructorPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Insert(GainStrengthPower __instance, AbstractCreature owner, int newAmount) {
            __instance.amount = newAmount;
            __instance.type = PowerType.DEBUFF;
            __instance.updateDescription();

            if (ConfigPanel.USE_MORIMENS_POWER_ICON)
                TexLoader.loadRegion(__instance);
            else
                ReflectionHacks.privateMethod(AbstractPower.class, "loadRegion", String.class)
                        .invoke(__instance, "shackle");

            return SpireReturn.Return();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctConstructor) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(GainStrengthPower.class, "amount");
                return LineFinder.findInOrder(ctConstructor, matcher);
            }
        }
    }

    @SpirePatch2(clz = GainStrengthPower.class, method = "stackPower")
    public static class StackPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Rewrite(GainStrengthPower __instance, int stackAmount, float ___fontScale) {
            ___fontScale += 8.0F;
            __instance.amount += stackAmount;
            if (__instance.amount == 0)
                att(new RemoveSpecificPowerAction(__instance.owner, __instance.owner, GainStrengthPower.POWER_ID));

            return SpireReturn.Return();
        }
    }
}
