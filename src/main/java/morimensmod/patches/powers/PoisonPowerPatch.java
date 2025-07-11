package morimensmod.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.PoisonPower;

import javassist.CtBehavior;
import morimensmod.util.TexLoader;

public class PoisonPowerPatch {
    @SpirePatch2(clz = PoisonPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
        AbstractCreature.class, int.class })
    public static class ConstructorPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Insert(PoisonPower __instance, AbstractCreature owner, AbstractCreature source, int poisonAmt, boolean ___isTurnBased) {
            __instance.amount = poisonAmt;
            __instance.updateDescription();

            TexLoader.loadRegion(__instance);

            __instance.type = PowerType.DEBUFF;
            ___isTurnBased = true;

            return SpireReturn.Return();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctConstructor) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(PoisonPower.class, "amount");
                return LineFinder.findInOrder(ctConstructor, matcher);
            }
        }
    }
}
