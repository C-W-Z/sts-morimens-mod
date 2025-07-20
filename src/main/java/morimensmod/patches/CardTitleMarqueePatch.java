package morimensmod.patches;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import morimensmod.cards.AbstractEasyCard;

public class CardTitleMarqueePatch {

    @SpirePatch2(clz = AbstractCard.class, method = "renderTitle")
    public static class AbstractCardRenderTitlePatch {

        private static float offset;
        private static String prevCard = "";
        private static final HashMap<String, Float> widthMap = new HashMap<>();
        private static final float SCROLL_SPEED = 20F;
        private static float dir = -1;

        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractCard __instance, SpriteBatch sb, Color ___renderColor) {

            if (__instance.isLocked || !__instance.isSeen || __instance.angle != 0 ||
                    !__instance.hb.hovered || !(__instance instanceof AbstractEasyCard))
                return SpireReturn.Continue(); // 不改寫

            Float textWidth = widthMap.get(__instance.name);
            float maxWidth = __instance.cost < -1 ? AbstractCard.IMG_WIDTH * 0.9F : AbstractCard.IMG_WIDTH * 0.7F;

            if (textWidth == null) {
                GlyphLayout layout = new GlyphLayout();

                FontHelper.cardTitleFont.getData().setScale(1.0F);
                layout.setText(FontHelper.cardTitleFont, __instance.name, Color.WHITE, 0.0F, 1, false);

                if (layout.width > maxWidth)
                    widthMap.put(__instance.name, layout.width + AbstractCard.IMG_WIDTH * 0.1F);
                else
                    widthMap.put(__instance.name, -1F);

                textWidth = widthMap.get(__instance.name);
            }

            float padding = (textWidth - maxWidth) / 2F;

            if (textWidth < 0)
                return SpireReturn.Continue();

            if (!prevCard.equals(__instance.name)) {
                offset = padding;
                dir = -1;
                prevCard = __instance.name;
            }

            offset += dir * Gdx.graphics.getDeltaTime() * SCROLL_SPEED * Settings.scale
                    * (CardCrawlGame.isInARun() ? 2F : 1F);

            if (offset > padding)
                dir = -1;
            else if (offset < -padding)
                dir = 1;

            sb.flush();
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);

            int scissorX = (int) (__instance.current_x - maxWidth / 2);
            int scissorY = (int) (__instance.current_y + 150F * __instance.drawScale * Settings.scale);
            int scissorW = (int) maxWidth;
            int scissorH = (int) (50f * __instance.drawScale * Settings.scale);
            Gdx.gl.glScissor(scissorX, scissorY, scissorW, scissorH);

            FontHelper.cardTitleFont.getData().setScale(__instance.drawScale);

            Color renderColor = __instance.upgraded ? Settings.GREEN_TEXT_COLOR.cpy() : ___renderColor;
            renderColor.a = ___renderColor.a;

            FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, __instance.name, __instance.current_x + offset,
                    __instance.current_y, 0F, 175F * __instance.drawScale * Settings.scale, __instance.angle, false,
                    renderColor);

            sb.flush();
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);

            return SpireReturn.Return();
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderTitle")
    public static class SingleCardViewPopupRenderTitlePatch {

        private static float offset;
        private static String prevCard = "";
        private static final HashMap<String, Float> widthMap = new HashMap<>();
        private static final float SCROLL_SPEED = 40.F;
        private static float dir = -1;
        private static final float IMG_WIDTH = 594F * Settings.scale;

        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {

            if (___card.isLocked || !___card.isSeen || !(___card instanceof AbstractEasyCard))
                return SpireReturn.Continue(); // 不改寫

            Float textWidth = widthMap.get(___card.name);
            float maxWidth = ___card.cost < -1 ? IMG_WIDTH * 0.9F : IMG_WIDTH * 0.7F;

            if (textWidth == null) {
                GlyphLayout layout = new GlyphLayout();

                FontHelper.SCP_cardTitleFont_small.getData().setScale(1.0F);
                layout.setText(FontHelper.SCP_cardTitleFont_small, ___card.name, Color.WHITE, 0.0F, 1, false);

                if (layout.width > maxWidth)
                    widthMap.put(___card.name, layout.width + AbstractCard.IMG_WIDTH * 0.1F);
                else
                    widthMap.put(___card.name, -1F);

                textWidth = widthMap.get(___card.name);
            }

            float padding = (textWidth - maxWidth) / 2F;

            if (textWidth < 0)
                return SpireReturn.Continue();

            if (!prevCard.equals(___card.name)) {
                offset = padding;
                dir = -1;
                prevCard = ___card.name;
            }

            offset += dir * Gdx.graphics.getDeltaTime() * SCROLL_SPEED * Settings.scale;

            if (offset > padding)
                dir = -1;
            else if (offset < -padding)
                dir = 1;

            sb.flush();
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);

            int scissorX = (int) (Settings.WIDTH / 2F - maxWidth / 2);
            int scissorY = (int) (0);
            int scissorW = (int) maxWidth;
            int scissorH = (int) (Settings.HEIGHT);
            Gdx.gl.glScissor(scissorX, scissorY, scissorW, scissorH);

            Color renderColor = SingleCardViewPopup.isViewingUpgrade ? Settings.GREEN_TEXT_COLOR : Settings.CREAM_COLOR;

            FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, ___card.name,
                    Settings.WIDTH / 2F + offset, Settings.HEIGHT / 2F + 338F * Settings.scale, renderColor);

            sb.flush();
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);

            return SpireReturn.Return();
        }
    }
}
