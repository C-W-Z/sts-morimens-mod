package morimensmod.patches;

// import static morimensmod.MorimensMod.logger;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import javassist.CtBehavior;
import morimensmod.characters.AbstractAwakener;

public class RenderPlayerImagePatch {
    @SpirePatch2(clz = AbstractPlayer.class, method = "renderPlayerImage")
    public static class _RenderPlayerImagePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractPlayer __instance, SpriteBatch sb) {
            // logger.info("RenderPlayerImagePatch:" + __instance.getClass().getSimpleName());
            if (!(__instance instanceof AbstractAwakener) || ((AbstractAwakener) __instance).anim == null)
                return SpireReturn.Continue();

            ((AbstractAwakener) __instance).anim.renderPlayerImage(sb, __instance);
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "render")
    public static class ReplaceFallbackRenderPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> patch(AbstractPlayer __instance, SpriteBatch sb, boolean ___renderCorpse) {
            // logger.info("ReplaceFallbackRenderPatch:" + __instance.getClass().getSimpleName() + ", " + !(__instance instanceof AbstractAwakener) + ", " + (((AbstractAwakener) __instance).anim == null));
            if (/*___renderCorpse || */!(__instance instanceof AbstractAwakener) || ((AbstractAwakener) __instance).anim == null)
                return SpireReturn.Continue();

            ((AbstractAwakener) __instance).anim.renderPlayerImage(sb, __instance);
            __instance.hb.render(sb);
            __instance.healthHb.render(sb);
            return SpireReturn.Return();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                // 精準找到 sb.draw(this.img, ...) 呼叫的位置
                Matcher.MethodCallMatcher matcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "draw");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}
