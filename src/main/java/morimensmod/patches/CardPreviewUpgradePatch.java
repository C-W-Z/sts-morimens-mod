package morimensmod.patches;

import static morimensmod.MorimensMod.makeID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
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
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class CardPreviewUpgradePatch {
    public static int upgradeNum = 1;

    public static Hitbox upgradeHbL = new Hitbox(100.0F * Settings.scale, 100.0F * Settings.scale);

    public static Hitbox upgradeHbR = new Hitbox(100.0F * Settings.scale, 100.0F * Settings.scale);

    public static boolean copyCanUpgrade = false;

    public static boolean copyCanUpgradeMuti = false;

    private static final String TEXT = (CardCrawlGame.languagePack
            .getUIString(makeID(CardPreviewUpgradePatch.class.getSimpleName()))).TEXT[0];

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "render")
    public static class UpgradeNumResetPatch {
        @SpirePrefixPatch
        public static void Patch(SingleCardViewPopup __instance, SpriteBatch sb) {
            if (!SingleCardViewPopup.isViewingUpgrade)
                upgradeNum = 1;
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "updateUpgradePreview")
    public static class UpdateHitBox {
        @SpirePrefixPatch
        public static void Patch(SingleCardViewPopup __instance) {
            if (!SingleCardViewPopup.isViewingUpgrade || !copyCanUpgradeMuti)
                return;
            if (upgradeNum > 1 && upgradeHbL != null) {
                upgradeHbL.update();
                if (InputHelper.justClickedLeft && upgradeHbL.hovered) {
                    upgradeHbL.clickStarted = true;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                }
                if (upgradeHbL.justHovered)
                    CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
                if (upgradeHbL.clicked) {
                    upgradeHbL.clicked = false;
                    upgradeNum--;
                }
            }
            if (upgradeNum < 20 && copyCanUpgrade &&
                    upgradeHbR != null) {
                upgradeHbR.update();
                if (InputHelper.justClickedLeft && upgradeHbR.hovered) {
                    upgradeHbR.clickStarted = true;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                }
                if (upgradeHbR.justHovered)
                    CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
                if (upgradeHbR.clicked) {
                    upgradeHbR.clicked = false;
                    upgradeNum++;
                }
            }
            if (upgradeNum > 20)
                upgradeNum = 20;
            if (upgradeNum < 1)
                upgradeNum = 1;
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderUpgradeViewToggle")
    public static class RenderHitbox {
        @SpirePrefixPatch
        public static void Patch(SingleCardViewPopup __instance, SpriteBatch sb) {
            if (!SingleCardViewPopup.isViewingUpgrade || !copyCanUpgradeMuti)
                return;
            FontHelper.renderFontCentered(
                    sb,
                    FontHelper.cardTitleFont,
                    TEXT,
                    Settings.WIDTH / 2.0F - 410.0F * Settings.scale,
                    125.0F * Settings.scale,
                    Settings.GOLD_COLOR
            );
            FontHelper.renderFontCentered(
                    sb,
                    FontHelper.cardTitleFont,
                    Integer.toString(upgradeNum),
                    Settings.WIDTH / 2.0F - 410.0F * Settings.scale,
                    70.0F * Settings.scale,
                    Settings.GOLD_COLOR);
            if (upgradeHbL != null) {
                if (upgradeHbL.hovered || Settings.isControllerMode)
                    sb.setColor(Color.WHITE);
                else
                    sb.setColor(Color.LIGHT_GRAY);

                if (upgradeNum > 1)
                    sb.draw(ImageMaster.CF_LEFT_ARROW,
                            upgradeHbL.cX - 24.0F,
                            upgradeHbL.cY - 24.0F,
                            24.0F, 24.0F, 48.0F, 48.0F,
                            Settings.scale * 1.5F,
                            Settings.scale * 1.5F,
                            0.0F, 0, 0, 48, 48,
                            false, false
                    );
                sb.setColor(Color.WHITE);
                upgradeHbL.render(sb);
            }
            if (upgradeHbR != null) {
                if (upgradeHbR.hovered || Settings.isControllerMode)
                    sb.setColor(Color.WHITE);
                else
                    sb.setColor(Color.LIGHT_GRAY);

                if (upgradeNum < 20 && copyCanUpgrade)
                    sb.draw(
                            ImageMaster.CF_RIGHT_ARROW,
                            upgradeHbR.cX - 24.0F,
                            upgradeHbR.cY - 24.0F,
                            24.0F, 24.0F, 48.0F, 48.0F,
                            Settings.scale * 1.5F,
                            Settings.scale * 1.5F,
                            0.0F, 0, 0, 48, 48,
                            false, false
                    );
                sb.setColor(Color.WHITE);
                upgradeHbR.render(sb);
            }
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class, CardGroup.class })
    public static class OpenPatch1 {
        @SpirePrefixPatch
        public static void Patch(SingleCardViewPopup __instance, AbstractCard card, CardGroup group) {
            upgradeHbL.move(Settings.WIDTH / 2.0F - 460.0F * Settings.scale, 70.0F * Settings.scale);
            upgradeHbR.move(Settings.WIDTH / 2.0F - 360.0F * Settings.scale, 70.0F * Settings.scale);
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class })
    public static class OpenPatch2 {
        @SpirePrefixPatch
        public static void Patch(SingleCardViewPopup __instance, AbstractCard card) {
            upgradeHbL.move(Settings.WIDTH / 2.0F - 460.0F * Settings.scale, 70.0F * Settings.scale);
            upgradeHbR.move(Settings.WIDTH / 2.0F - 360.0F * Settings.scale, 70.0F * Settings.scale);
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "render")
    public static class UpgradeCardPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Patch(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            copyCanUpgradeMuti = ___card.canUpgrade();
            for (int i = 0; i < upgradeNum - 1; i++)
                ___card.upgrade();
            copyCanUpgrade = ___card.canUpgrade();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractCard.class, "displayUpgrades");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "updateInput")
    public static class ClosePatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("close") && m.getLineNumber() == 291)
                        m.replace("if(" + CardPreviewUpgradePatch.class.getName()
                                + ".checkClickToClose()){ $proceed($$);}");
                }
            };
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "updateInput")
    public static class DontChangeClickedCaseyByRita {
        @SpireInsertPatch(rloc = 17)
        public static void Patch(SingleCardViewPopup __instance) {
            if (!checkClickToClose())
                InputHelper.justClickedLeft = true;
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "updateInput")
    public static class ClearSCPFontTextures {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
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
