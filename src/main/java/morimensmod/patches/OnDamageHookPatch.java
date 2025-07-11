package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import javassist.CtBehavior;
import morimensmod.interfaces.OnBeforeDamaged;

public class OnDamageHookPatch {

    @SpirePatch2(clz = AbstractPlayer.class, method = "damage")
    public static class OnPlayerDamagedHookPath {

        @SpireInsertPatch(locator = Locator.class, localvars = { "damageAmount" })
        public static void Insert(AbstractPlayer __instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractRelic r : __instance.relics)
                if (r instanceof OnBeforeDamaged)
                    damageAmount[0] = ((OnBeforeDamaged) r).onBeforeDamaged(info, damageAmount[0]);
            for (AbstractPower p : __instance.powers)
                if (p instanceof OnBeforeDamaged)
                    damageAmount[0] = ((OnBeforeDamaged) p).onBeforeDamaged(info, damageAmount[0]);
            if (__instance.stance instanceof OnBeforeDamaged)
                damageAmount[0] = ((OnBeforeDamaged) __instance.stance).onBeforeDamaged(info, damageAmount[0]);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasPower");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch2(clz = AbstractMonster.class, method = "damage")
    public static class OnMonsterDamagedHookPath {

        @SpireInsertPatch(locator = Locator.class, localvars = {"damageAmount"})
        public static void Insert(AbstractMonster __instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractPower p : __instance.powers)
                if (p instanceof OnBeforeDamaged)
                    damageAmount[0] = ((OnBeforeDamaged) p).onBeforeDamaged(info, damageAmount[0]);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "isDying");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}
