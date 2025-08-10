package morimensmod.patches.blights;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;

import javassist.CtBehavior;
import morimensmod.blights.AbstractMorimensBlight;

public class MonsterBlightPatch {

    @SpirePatch2(clz = AbstractMonster.class, method = "calculateDamage")
    public static class CalculateDamagePatch {

        @SpireInsertPatch(locator = Locator.class, localvars = { "tmp" })
        public static void Insert(AbstractMonster __instance, @ByRef float[] tmp) {
            if (AbstractDungeon.player == null)
                return;
            int damageAmplify = 100;
            for (AbstractBlight b : AbstractDungeon.player.blights)
                if (b instanceof AbstractMorimensBlight)
                    damageAmplify += ((AbstractMorimensBlight) b).monsterDamageAmplify();
            if (damageAmplify == 100)
                return;
            tmp[0] *= damageAmplify / 100F;
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractStance.class, "atDamageReceive");
                int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
                return new int[] { lines[0] + 1 };
            }
        }
    }

    @SpirePatch2(clz = DamageInfo.class, method = "applyPowers")
    public static class ApplyDamagePatch {

        @SpireInsertPatch(locator = Locator.class, localvars = { "tmp" })
        public static void Insert(DamageInfo __instance, @ByRef float[] tmp) {
            if (AbstractDungeon.player == null)
                return;
            int damageAmplify = 100;
            for (AbstractBlight b : AbstractDungeon.player.blights)
                if (b instanceof AbstractMorimensBlight)
                    damageAmplify += ((AbstractMorimensBlight) b).monsterDamageAmplify();
            if (damageAmplify == 100)
                return;
            tmp[0] *= damageAmplify / 100F;
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractStance.class, "atDamageReceive");
                int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
                return new int[] { lines[0] + 1 };
            }
        }
    }

    @SpirePatch2(clz = AbstractMonster.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
            String.class, String.class, int.class, float.class, float.class, float.class, float.class, String.class,
            float.class, float.class, boolean.class
    })
    public static class MonsterConstuctorPatch {

        // @SpireInsertPatch(locator = Locator.class)
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster __instance) {
            if (AbstractDungeon.player == null)
                return;
            int healthAmplify = 100;
            for (AbstractBlight b : AbstractDungeon.player.blights)
                if (b instanceof AbstractMorimensBlight)
                    healthAmplify += ((AbstractMorimensBlight) b).monsterHealthAmplify();
            if (healthAmplify == 100)
                return;
            __instance.maxHealth = MathUtils.ceil(__instance.maxHealth * healthAmplify / 100F);
            __instance.currentHealth = __instance.maxHealth;
        }

        // private static class Locator extends SpireInsertLocator {
        //     @Override
        //     public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        //         Matcher matcher = new Matcher.ConstructorCallMatcher(Hitbox.class);
        //         return LineFinder.findAllInOrder(ctMethodToPatch, matcher);
        //     }
        // }
    }

    @SpirePatch2(clz = AbstractMonster.class, method = "setHp", paramtypez = { int.class, int.class })
    public static class MonsterSetHpPatch {

        // @SpireInsertPatch(locator = Locator.class)
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster __instance) {
            if (AbstractDungeon.player == null)
                return;
            int healthAmplify = 100;
            for (AbstractBlight b : AbstractDungeon.player.blights)
                if (b instanceof AbstractMorimensBlight)
                    healthAmplify += ((AbstractMorimensBlight) b).monsterHealthAmplify();
            if (healthAmplify == 100)
                return;
            __instance.maxHealth = MathUtils.ceil(__instance.maxHealth * healthAmplify / 100F);
            __instance.currentHealth = __instance.maxHealth;
        }

        // private static class Locator extends SpireInsertLocator {
        //     @Override
        //     public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        //         Matcher matcher = new Matcher.ConstructorCallMatcher(Hitbox.class);
        //         return LineFinder.findAllInOrder(ctMethodToPatch, matcher);
        //     }
        // }
    }
}
