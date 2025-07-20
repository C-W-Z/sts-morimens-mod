package morimensmod.patches;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;

import javassist.CtBehavior;

public class CardTitlePatch {

    private static final HashMap<String, Float> scaleMap = new HashMap<>();

    @SpirePatch2(clz = AbstractCard.class, method = "renderTitle")
    public static class RenderTitlePatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractCard __instance, SpriteBatch sb, boolean ___useSmallTitleFont,
                GlyphLayout ___gl, float ___TITLE_BOX_WIDTH) {

            Float scale = scaleMap.get(__instance.name);

            if (scale == null) {
                if (___useSmallTitleFont)
                    FontHelper.cardTitleFont.getData().setScale(__instance.drawScale * 0.85f);
                else
                    FontHelper.cardTitleFont.getData().setScale(__instance.drawScale);

                GlyphLayout layout = new GlyphLayout(FontHelper.cardTitleFont, __instance.name);

                if (layout.width > ___TITLE_BOX_WIDTH * 0.8F)
                    scaleMap.put(__instance.name, 0.7F);
                else
                    scaleMap.put(__instance.name, 1F);

                scale = scaleMap.get(__instance.name);
            }

            if (scale != 1)
                FontHelper.cardTitleFont.getData().setScale(__instance.drawScale * scale);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "upgraded");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}
