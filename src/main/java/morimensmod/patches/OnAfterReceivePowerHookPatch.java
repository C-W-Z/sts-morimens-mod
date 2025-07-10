package morimensmod.patches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import javassist.CtBehavior;
import morimensmod.interfaces.OnAfterReceivePower;

/**
 * @deprecated Use {@link #morimensmod.patches.OnPowerModifiedHookPatch()} instead.
 */
@Deprecated
@SpirePatch2(clz = ApplyPowerAction.class, method = "update")
public class OnAfterReceivePowerHookPatch {

    private static final Logger logger = LogManager.getLogger(OnAfterReceivePowerHookPatch.class);

    @SpireInsertPatch(locator = Locator.class)
    public static void afterReceivePower(ApplyPowerAction __instance, AbstractPower ___powerToApply) {
        AbstractCreature target = __instance.target;
        AbstractCreature source = __instance.source;
        AbstractPower power = ___powerToApply;

        logger.debug("afterReceivePowerPatch");

        if (power != null && target != null) {
            logger.debug(", power:" + ___powerToApply.name + "target:" + target.name);
            if (source != null)
                logger.debug(", source:" + source.name);
            // if (target instanceof OnAfterReceivePower)
            //     ((OnAfterReceivePower) target).onAfterReceivePower(power, target, source);
            for (AbstractPower p : target.powers)
                if (p instanceof OnAfterReceivePower)
                    ((OnAfterReceivePower) p).onAfterReceivePower(power, target, source);
            if (!(target instanceof AbstractPlayer))
                return;
            AbstractPlayer player = (AbstractPlayer) target;
            for (AbstractRelic r : player.relics)
                if (r instanceof OnAfterReceivePower)
                    ((OnAfterReceivePower) r).onAfterReceivePower(power, target, source);
            if (player.stance instanceof OnAfterReceivePower)
                ((OnAfterReceivePower) player.stance).onAfterReceivePower(power, target, source);
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior behavior) throws Exception {
            // 找 powerToApply.onInitialApplication()
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractPower.class, "onInitialApplication");
            int[] lines = LineFinder.findInOrder(behavior, matcher);
            // 插入點是 onInitialApplication() 之後一行
            return new int[]{lines[0] + 1};
        }
    }
}
