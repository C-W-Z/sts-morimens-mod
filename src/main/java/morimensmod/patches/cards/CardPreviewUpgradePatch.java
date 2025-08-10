package morimensmod.patches.cards;

import static morimensmod.MorimensMod.isShionModLoaded;
import static morimensmod.MorimensMod.makeID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import basemod.ReflectionHacks;
import javassist.CtBehavior;

public class CardPreviewUpgradePatch {

    private static final int MAX_DISPLAY_UPGRADE_NUM = 20;

    private static int upgradeNum = 1;

    private static Hitbox upgradeHbL = new Hitbox(100.0F * Settings.scale, 100.0F * Settings.scale);

    private static Hitbox upgradeHbR = new Hitbox(100.0F * Settings.scale, 100.0F * Settings.scale);

    private static boolean copyCanUpgrade = false;

    private static boolean copyCanUpgradeMuti = false;

    private static final String TEXT = (CardCrawlGame.languagePack
            .getUIString(makeID(CardPreviewUpgradePatch.class.getSimpleName()))).TEXT[0];

    public static boolean dontPath() {
        return isShionModLoaded;
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "render")
    public static class UpgradeNumResetPatch {
        @SpirePrefixPatch
        public static void Patch(SingleCardViewPopup __instance, SpriteBatch sb) {
            if (dontPath())
                return;
            if (!SingleCardViewPopup.isViewingUpgrade)
                upgradeNum = 1;
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "updateUpgradePreview")
    public static class UpdateHitBox {
        @SpirePrefixPatch
        public static void Patch(SingleCardViewPopup __instance) {
            if (dontPath())
                return;
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
            if (upgradeNum < MAX_DISPLAY_UPGRADE_NUM && copyCanUpgrade &&
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
            if (upgradeNum > MAX_DISPLAY_UPGRADE_NUM)
                upgradeNum = MAX_DISPLAY_UPGRADE_NUM;
            if (upgradeNum < 1)
                upgradeNum = 1;
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderUpgradeViewToggle")
    public static class RenderHitbox {
        @SpirePrefixPatch
        public static void Patch(SingleCardViewPopup __instance, SpriteBatch sb) {
            if (dontPath())
                return;
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

                if (upgradeNum < MAX_DISPLAY_UPGRADE_NUM && copyCanUpgrade)
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
            if (dontPath())
                return;
            upgradeHbL.move(Settings.WIDTH / 2.0F - 460.0F * Settings.scale, 70.0F * Settings.scale);
            upgradeHbR.move(Settings.WIDTH / 2.0F - 360.0F * Settings.scale, 70.0F * Settings.scale);
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class })
    public static class OpenPatch2 {
        @SpirePrefixPatch
        public static void Patch(SingleCardViewPopup __instance, AbstractCard card) {
            if (dontPath())
                return;
            upgradeHbL.move(Settings.WIDTH / 2.0F - 460.0F * Settings.scale, 70.0F * Settings.scale);
            upgradeHbR.move(Settings.WIDTH / 2.0F - 360.0F * Settings.scale, 70.0F * Settings.scale);
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "render")
    public static class UpgradeCardPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Patch(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            if (dontPath())
                return;
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
    public static class UpdateInputPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Patch(SingleCardViewPopup __instance, AbstractCard ___prevCard,
                AbstractCard ___nextCard) {
            if (dontPath())
                return SpireReturn.Continue();
            // 用這段code取代掉原本的code
            boolean clickToClose = checkClickToClose();
            if (clickToClose)
                __instance.close();
            InputHelper.justClickedLeft = !clickToClose;
            // if (clickToClose)
            //     FontHelper.ClearSCPFontTextures();

            // 繼續之後的Codes
            if (___prevCard != null && InputActionSet.left.isJustPressed())
                ReflectionHacks.privateMethod(SingleCardViewPopup.class, "openPrev").invoke(__instance);
            else if (___nextCard != null && InputActionSet.right.isJustPressed())
                ReflectionHacks.privateMethod(SingleCardViewPopup.class, "openNext").invoke(__instance);

            return SpireReturn.Return();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                // 找到 if (!this.cardHb.hovered && !this.upgradeHb.hovered && (this.betaArtHb == null || (this.betaArtHb != null && !this.betaArtHb.hovered))) 的下一行
                Matcher matcher = new Matcher.FieldAccessMatcher(SingleCardViewPopup.class, "cardHb");
                int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
                return new int[] {lines[0] + 1};
            }
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
