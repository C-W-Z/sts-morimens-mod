package morimensmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import javassist.CtBehavior;
import me.antileaf.signature.card.AbstractSignatureCard;

public class CardTypeRenderPatch {

    private static final UIStrings BUFF_STRINGS = CardCrawlGame.languagePack.getUIString(CustomTags.BUFF.name());
    private static final UIStrings WHEEL_OF_DESTINY_STRINGS = CardCrawlGame.languagePack.getUIString(CustomTags.WHEEL_OF_DESTINY.name());

    @SpirePatch2(clz = AbstractCard.class, method = "renderType")
    public static class _CardTypeRenderPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = { "text" })
        public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef String[] text) {
            if (__instance.hasTag(CustomTags.BUFF))
                text[0] = BUFF_STRINGS.TEXT[0];
            else if (__instance.hasTag(CustomTags.WHEEL_OF_DESTINY))
                text[0] = WHEEL_OF_DESTINY_STRINGS.TEXT[0];
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                return LineFinder.findInOrder(ctMethodToPatch,
                        new Matcher.MethodCallMatcher(FontHelper.class, "renderRotatedText"));
            }
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderCardTypeText")
    public static class SingleCardViewRenderPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = { "label" })
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef String[] label,
                AbstractCard ___card) {
            if (___card.hasTag(CustomTags.BUFF))
                label[0] = BUFF_STRINGS.TEXT[0];
            else if (___card.hasTag(CustomTags.WHEEL_OF_DESTINY))
                label[0] = WHEEL_OF_DESTINY_STRINGS.TEXT[0];
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                return LineFinder.findInOrder(ctMethodToPatch,
                        new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered"));
            }
        }
    }

    @SpirePatch2(clz = AbstractSignatureCard.class, method = "renderType")
    public static class SignatureCardTypeRenderPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = { "text" })
        public static void Insert(AbstractSignatureCard __instance, SpriteBatch sb, @ByRef String[] text) {
            if (__instance.hasTag(CustomTags.BUFF))
                text[0] = BUFF_STRINGS.TEXT[0];
            else if (__instance.hasTag(CustomTags.WHEEL_OF_DESTINY))
                text[0] = WHEEL_OF_DESTINY_STRINGS.TEXT[0];
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                return LineFinder.findInOrder(ctMethodToPatch,
                        new Matcher.MethodCallMatcher(FontHelper.class, "renderRotatedText"));
            }
        }
    }
}
