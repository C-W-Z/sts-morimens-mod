package morimensmod.patches.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.PoisonPower;

import basemod.ReflectionHacks;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;

import morimensmod.config.ConfigPanel;
import morimensmod.powers.rouse.DrowningInSorrowPower;
import morimensmod.util.TexLoader;

import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.isInCombat;

public class PoisonPowerPatch {

    private static final Logger logger = LogManager.getLogger(PoisonPowerPatch.class);

    @SpirePatch2(clz = PoisonPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
            AbstractCreature.class, int.class })
    public static class ConstructorPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Insert(PoisonPower __instance, AbstractCreature owner, AbstractCreature source,
                int poisonAmt, boolean ___isTurnBased) {
            __instance.amount = poisonAmt;
            __instance.updateDescription();

            if (ConfigPanel.USE_MORIMENS_POWER_ICON)
                TexLoader.loadRegion(__instance);
            else
                ReflectionHacks.privateMethod(AbstractPower.class, "loadRegion", String.class)
                        .invoke(__instance, "poison");

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
            if (!isInCombat() || AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                return;
            logger.debug("apply poison action atEndOfTurn");
            __instance.flashWithoutSound();
            atb(new PoisonLoseHpAction(__instance.owner, null, __instance.amount, AttackEffect.POISON));
        }
    }

    /**
     * See DrowningInSorrowPower.getStrings()
     */
    @SpirePatch(clz = PoisonLoseHpAction.class, method = "update")
    public static class PoisonDamageTypeByTargetPatch {
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                @Override
                public void edit(NewExpr e) throws CannotCompileException {
                    if (e.getClassName().equals(DamageInfo.class.getName())) {
                        e.replace("{" +
                                "  if (this.target.hasPower(\"" + DrowningInSorrowPower.POWER_ID + "\")) {" +
                                "    $_ = new com.megacrit.cardcrawl.cards.DamageInfo($1, $2, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS);"
                                +
                                "  } else {" +
                                "    $_ = new com.megacrit.cardcrawl.cards.DamageInfo($1, $2, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS);"
                                +
                                "  }" +
                                "}");
                    }
                }
            };
        }
    }
}
