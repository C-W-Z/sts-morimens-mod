package morimensmod.patches.enums;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.DERIVATIVE_COLOR;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import basemod.ReflectionHacks;
import javassist.CtBehavior;
import me.antileaf.signature.card.AbstractSignatureCard;
import me.antileaf.signature.patches.card.SignaturePatch;
import me.antileaf.signature.utils.SignatureHelper;
import me.antileaf.signature.utils.internal.SignatureHelperInternal;

public class CardTypeRenderPatch {

    private static final UIStrings COMMAND_STRINGS = CardCrawlGame.languagePack.getUIString(CustomTags.COMMAND.name());
    private static final UIStrings ROUSE_STRINGS = CardCrawlGame.languagePack.getUIString(CustomTags.ROUSE.name());
    private static final UIStrings BUFF_STRINGS = CardCrawlGame.languagePack.getUIString(CustomTags.BUFF.name());
    private static final UIStrings WHEEL_OF_DESTINY_STRINGS = CardCrawlGame.languagePack
            .getUIString(CustomTags.WHEEL_OF_DESTINY.name());
    private static final UIStrings SYMPTOM_STRINGS = CardCrawlGame.languagePack.getUIString(CustomTags.SYMPTOM.name());
    private static final UIStrings STATUS_STRINGS = CardCrawlGame.languagePack.getUIString(CustomTags.STATUS.name());
    private static final UIStrings POSSE_STRINGS = CardCrawlGame.languagePack.getUIString(CustomTags.POSSE.name());
    private static final UIStrings DERIVATIVE_STRINGS = CardCrawlGame.languagePack.getUIString(DERIVATIVE_COLOR.name());

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderCardTypeText")
    public static class SingleCardViewRenderPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = { "label" })
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef String[] label,
                AbstractCard ___card) {
            if (___card.hasTag(CustomTags.COMMAND))
                label[0] = COMMAND_STRINGS.TEXT[0];
            else if (___card.hasTag(CustomTags.ROUSE))
                label[0] = ROUSE_STRINGS.TEXT[0];
            else if (___card.hasTag(CustomTags.BUFF))
                label[0] = BUFF_STRINGS.TEXT[0];
            else if (___card.hasTag(CustomTags.WHEEL_OF_DESTINY))
                label[0] = WHEEL_OF_DESTINY_STRINGS.TEXT[0];
            else if (___card.hasTag(CustomTags.SYMPTOM))
                label[0] = SYMPTOM_STRINGS.TEXT[0];
            else if (___card.hasTag(CustomTags.STATUS))
                label[0] = STATUS_STRINGS.TEXT[0];
            else if (___card.hasTag(CustomTags.POSSE))
                label[0] = POSSE_STRINGS.TEXT[0];
            if (___card.color == DERIVATIVE_COLOR)
                label[0] = DERIVATIVE_STRINGS.TEXT[0] + label[0];
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                return LineFinder.findInOrder(ctMethodToPatch,
                        new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered"));
            }
        }
    }

    /**
     * 暫時先用底下的Prefix，等SignatureLib更新後再改用這個Insert
     */
    // @SpirePatch2(clz = AbstractCard.class, method = "renderType")
    // public static class SignatureCardTypeRenderPatch {
    //     @SpireInsertPatch(locator = Locator.class, localvars = { "text" })
    //     public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef String[] text) {
    //         if (__instance.hasTag(CustomTags.COMMAND))
    //             text[0] = COMMAND_STRINGS.TEXT[0];
    //         else if (__instance.hasTag(CustomTags.ROUSE))
    //             text[0] = ROUSE_STRINGS.TEXT[0];
    //         else if (__instance.hasTag(CustomTags.BUFF))
    //             text[0] = BUFF_STRINGS.TEXT[0];
    //         else if (__instance.hasTag(CustomTags.WHEEL_OF_DESTINY))
    //             text[0] = WHEEL_OF_DESTINY_STRINGS.TEXT[0];
    //         else if (__instance.hasTag(CustomTags.SYMPTOM))
    //             text[0] = SYMPTOM_STRINGS.TEXT[0];
    //         else if (__instance.hasTag(CustomTags.STATUS))
    //             text[0] = STATUS_STRINGS.TEXT[0];
    //         else if (__instance.hasTag(CustomTags.POSSE))
    //             text[0] = POSSE_STRINGS.TEXT[0];
    //         if (__instance.color == DERIVATIVE_COLOR)
    //             text[0] = DERIVATIVE_STRINGS.TEXT[0] + text[0];
    //     }

    //     private static class Locator extends SpireInsertLocator {
    //         @Override
    //         public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
    //             return LineFinder.findInOrder(ctMethodToPatch,
    //                     new Matcher.MethodCallMatcher(FontHelper.class, "renderRotatedText"));
    //         }
    //     }
    // }

    @SpirePatch2(clz = AbstractCard.class, method = "renderType")
    public static class RenderTypePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (!SignatureHelperInternal.usePatch(__instance))
                return SpireReturn.Continue();
            if (getFrame(__instance) == null)
                return SpireReturn.Return();

            String text;

            if (__instance.hasTag(CustomTags.COMMAND))
                text = COMMAND_STRINGS.TEXT[0];
            else if (__instance.hasTag(CustomTags.ROUSE))
                text = ROUSE_STRINGS.TEXT[0];
            else if (__instance.hasTag(CustomTags.BUFF))
                text = BUFF_STRINGS.TEXT[0];
            else if (__instance.hasTag(CustomTags.WHEEL_OF_DESTINY))
                text = WHEEL_OF_DESTINY_STRINGS.TEXT[0];
            else if (__instance.hasTag(CustomTags.SYMPTOM))
                text = SYMPTOM_STRINGS.TEXT[0];
            else if (__instance.hasTag(CustomTags.STATUS))
                text = STATUS_STRINGS.TEXT[0];
            else if (__instance.hasTag(CustomTags.POSSE))
                text = POSSE_STRINGS.TEXT[0];
            else if (__instance.type == AbstractCard.CardType.ATTACK)
                text = AbstractCard.TEXT[0];
            else if (__instance.type == AbstractCard.CardType.SKILL)
                text = AbstractCard.TEXT[1];
            else if (__instance.type == AbstractCard.CardType.POWER)
                text = AbstractCard.TEXT[2];
            else if (__instance.type == AbstractCard.CardType.CURSE)
                text = AbstractCard.TEXT[3];
            else if (__instance.type == AbstractCard.CardType.STATUS)
                text = AbstractCard.TEXT[7];
            else
                text = AbstractCard.TEXT[5];

            if (__instance.color == DERIVATIVE_COLOR)
                text = DERIVATIVE_STRINGS.TEXT[0] + text;

            BitmapFont font = FontHelper.cardTypeFont;
            font.getData().setScale(__instance.drawScale);

            Color typeColor = ReflectionHacks.getPrivate(__instance, AbstractCard.class, "typeColor");
            Color renderColor = ReflectionHacks.getPrivate(__instance, AbstractCard.class, "renderColor");

            typeColor.a = renderColor.a;
            if ((SignatureHelperInternal.getInfo(__instance.cardID)).hideFrame.test(__instance))
                typeColor.a *= getSignatureTransparency(__instance);
            if (typeColor.a > 0.0F)
                FontHelper.renderRotatedText(sb, font, text, __instance.current_x,
                        __instance.current_y - 195.0F * __instance.drawScale * Settings.scale, 0.0F,
                        -1.0F * __instance.drawScale * Settings.scale, __instance.angle, false,
                        typeColor);

            return SpireReturn.Return();
        }

        // private static class Locator extends SpireInsertLocator {
        //     @Override
        //     public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        //         return LineFinder.findInOrder(ctMethodToPatch,
        //                 new Matcher.MethodCallMatcher(FontHelper.class, "renderRotatedText"));
        //     }
        // }

        private static TextureAtlas.AtlasRegion getFrame(AbstractCard card) {
            SignatureHelper.Style style = SignatureHelperInternal.getStyle(card);
            String frame;
            if (card.type == CardType.ATTACK) {
                if (card.rarity == CardRarity.RARE)
                    frame = style.cardTypeAttackRare;
                else if (card.rarity == CardRarity.UNCOMMON)
                    frame = style.cardTypeAttackUncommon;
                else
                    frame = style.cardTypeAttackCommon;
            } else if (card.type == CardType.POWER) {
                if (card.rarity == CardRarity.RARE)
                    frame = style.cardTypePowerRare;
                else if (card.rarity == CardRarity.UNCOMMON)
                    frame = style.cardTypePowerUncommon;
                else
                    frame = style.cardTypePowerCommon;
            } else if (card.rarity == CardRarity.RARE) {
                frame = style.cardTypeSkillRare;
            } else if (card.rarity == CardRarity.UNCOMMON) {
                frame = style.cardTypeSkillUncommon;
            } else {
                frame = style.cardTypeSkillCommon;
            }

            return frame != null && !frame.isEmpty() ? SignatureHelperInternal.load(frame) : null;
        }

        private static float getSignatureTransparency(AbstractCard card) {
            if (SignaturePatch.Fields.previewTransparency.get(card) >= 0.0F) {
                return SignaturePatch.Fields.previewTransparency.get(card);
            } else {
                float ret = Math.max(
                        SignaturePatch.Fields.signatureHoveredTimer.get(card),
                        SignaturePatch.Fields.forcedTimer.get(card)) / 0.3F;
                return Math.min(ret, 1.0F);
            }
        }
    }
}
