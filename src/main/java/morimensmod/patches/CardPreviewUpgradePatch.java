package morimensmod.patches;

import basemod.ReflectionHacks;

import static morimensmod.MorimensMod.makeID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class CardPreviewUpgradePatch {
    public static int upgradeNum = 1;

    public static Hitbox upgradeHbL = new Hitbox(100.0F * Settings.scale, 100.0F * Settings.scale);

    public static Hitbox upgradeHbR = new Hitbox(100.0F * Settings.scale, 100.0F * Settings.scale);

    public static boolean copyCanUpgrade = false;

    public static boolean copyCanUpgradeMuti = false;

    @SpirePatch(clz = SingleCardViewPopup.class, method = "render")
    public static class UpgradeNumResetPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, SpriteBatch sb) {
            if (!SingleCardViewPopup.isViewingUpgrade)
                CardPreviewUpgradePatch.upgradeNum = 1;
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "updateUpgradePreview")
    public static class UpdateHitBox {
        @SpireInsertPatch(rloc = 0, localvars = { "card" })
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, AbstractCard card) {
            if (SingleCardViewPopup.isViewingUpgrade && CardPreviewUpgradePatch.copyCanUpgradeMuti) {
                if (CardPreviewUpgradePatch.upgradeNum > 1 &&
                        CardPreviewUpgradePatch.upgradeHbL != null) {
                    CardPreviewUpgradePatch.upgradeHbL.update();
                    if (InputHelper.justClickedLeft && CardPreviewUpgradePatch.upgradeHbL.hovered) {
                        CardPreviewUpgradePatch.upgradeHbL.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1");
                    }
                    if (CardPreviewUpgradePatch.upgradeHbL.justHovered)
                        CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
                    if (CardPreviewUpgradePatch.upgradeHbL.clicked) {
                        CardPreviewUpgradePatch.upgradeHbL.clicked = false;
                        CardPreviewUpgradePatch.upgradeNum--;
                    }
                }
                if (CardPreviewUpgradePatch.upgradeNum < 20 && CardPreviewUpgradePatch.copyCanUpgrade &&
                        CardPreviewUpgradePatch.upgradeHbR != null) {
                    CardPreviewUpgradePatch.upgradeHbR.update();
                    if (InputHelper.justClickedLeft && CardPreviewUpgradePatch.upgradeHbR.hovered) {
                        CardPreviewUpgradePatch.upgradeHbR.clickStarted = true;
                        CardCrawlGame.sound.play("UI_CLICK_1");
                    }
                    if (CardPreviewUpgradePatch.upgradeHbR.justHovered)
                        CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
                    if (CardPreviewUpgradePatch.upgradeHbR.clicked) {
                        CardPreviewUpgradePatch.upgradeHbR.clicked = false;
                        CardPreviewUpgradePatch.upgradeNum++;
                    }
                }
                if (CardPreviewUpgradePatch.upgradeNum > 20)
                    CardPreviewUpgradePatch.upgradeNum = 20;
                if (CardPreviewUpgradePatch.upgradeNum < 1)
                    CardPreviewUpgradePatch.upgradeNum = 1;
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderUpgradeViewToggle")
    public static class RenderHitbox {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, SpriteBatch sb) {
            if (SingleCardViewPopup.isViewingUpgrade && CardPreviewUpgradePatch.copyCanUpgradeMuti) {
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont,
                        (CardCrawlGame.languagePack
                                .getUIString(makeID(CardPreviewUpgradePatch.class.getSimpleName()))).TEXT[0],
                        Settings.WIDTH / 2.0F - 410.0F * Settings.scale, 125.0F * Settings.scale, Settings.GOLD_COLOR);
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont,
                        Integer.toString(CardPreviewUpgradePatch.upgradeNum),
                        Settings.WIDTH / 2.0F - 410.0F * Settings.scale, 70.0F * Settings.scale, Settings.GOLD_COLOR
                                .cpy());
                if (CardPreviewUpgradePatch.upgradeHbL != null) {
                    if (CardPreviewUpgradePatch.upgradeHbL.hovered || Settings.isControllerMode) {
                        sb.setColor(Color.WHITE.cpy());
                    } else {
                        sb.setColor(Color.LIGHT_GRAY.cpy());
                    }
                    if (CardPreviewUpgradePatch.upgradeNum > 1)
                        sb.draw(ImageMaster.CF_LEFT_ARROW, CardPreviewUpgradePatch.upgradeHbL.cX - 24.0F,
                                CardPreviewUpgradePatch.upgradeHbL.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F,
                                Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 48, 48, false, false);
                    sb.setColor(Color.WHITE.cpy());
                    CardPreviewUpgradePatch.upgradeHbL.render(sb);
                }
                if (CardPreviewUpgradePatch.upgradeHbR != null) {
                    if (CardPreviewUpgradePatch.upgradeHbR.hovered || Settings.isControllerMode) {
                        sb.setColor(Color.WHITE.cpy());
                    } else {
                        sb.setColor(Color.LIGHT_GRAY.cpy());
                    }
                    if (CardPreviewUpgradePatch.upgradeNum < 20 && CardPreviewUpgradePatch.copyCanUpgrade)
                        sb.draw(ImageMaster.CF_RIGHT_ARROW, CardPreviewUpgradePatch.upgradeHbR.cX - 24.0F,
                                CardPreviewUpgradePatch.upgradeHbR.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F,
                                Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 48, 48, false, false);
                    sb.setColor(Color.WHITE.cpy());
                    CardPreviewUpgradePatch.upgradeHbR.render(sb);
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class, CardGroup.class })
    public static class OpenPatch1 {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, AbstractCard card, CardGroup group) {
            CardPreviewUpgradePatch.upgradeHbL.move(Settings.WIDTH / 2.0F - 460.0F * Settings.scale,
                    70.0F * Settings.scale);
            CardPreviewUpgradePatch.upgradeHbR.move(Settings.WIDTH / 2.0F - 360.0F * Settings.scale,
                    70.0F * Settings.scale);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class })
    public static class OpenPatch2 {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, AbstractCard card) {
            CardPreviewUpgradePatch.upgradeHbL.move(Settings.WIDTH / 2.0F - 460.0F * Settings.scale,
                    70.0F * Settings.scale);
            CardPreviewUpgradePatch.upgradeHbR.move(Settings.WIDTH / 2.0F - 360.0F * Settings.scale,
                    70.0F * Settings.scale);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "render")
    public static class UpgradeCardPatch {
        @SpireInsertPatch(rloc = 4)
        public static void Insert(SingleCardViewPopup _instance, SpriteBatch sb) {
            AbstractCard card = (AbstractCard) ReflectionHacks.getPrivate(_instance, SingleCardViewPopup.class, "card");
            CardPreviewUpgradePatch.copyCanUpgradeMuti = card.canUpgrade();
            for (int i = 0; i < CardPreviewUpgradePatch.upgradeNum - 1; i++)
                card.upgrade();
            CardPreviewUpgradePatch.copyCanUpgrade = card.canUpgrade();
            ReflectionHacks.setPrivate(_instance, SingleCardViewPopup.class, "card", card);
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "updateInput")
    public static class ClosePatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("close") && m.getLineNumber() == 291)
                        m.replace("if(" + CardPreviewUpgradePatch.class.getName()
                                + ".checkClickToClose()){ $proceed($$);}");
                }
            };
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "updateInput")
    public static class DontChangeClickedCaseyByRita {
        @SpireInsertPatch(rloc = 17)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance) {
            if (!CardPreviewUpgradePatch.checkClickToClose())
                InputHelper.justClickedLeft = true;
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "updateInput")
    public static class ClearSCPFontTextures {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("ClearSCPFontTextures") && m.getLineNumber() == 293)
                        m.replace("if(" + CardPreviewUpgradePatch.class.getName()
                                + ".checkClickToClose()){ $proceed($$);}");
                }
            };
        }
    }

    public static boolean checkClickToClose() {
        upgradeHbL.update();
        upgradeHbR.update();
        if (SingleCardViewPopup.isViewingUpgrade && copyCanUpgradeMuti)
            return (!upgradeHbL.hovered && !upgradeHbR.hovered);
        return true;
    }
}
