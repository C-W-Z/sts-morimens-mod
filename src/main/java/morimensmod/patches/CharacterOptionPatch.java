package morimensmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.ReflectionHacks;
import morimensmod.characters.AbstractAwakener;

@SpirePatch2(clz = CharacterOption.class, method = "renderInfo")
public class CharacterOptionPatch {

    private static final float NAME_OFFSET_Y = 200 * Settings.scale;

    private static final float GOLD_XOFFSET = 30F;

    @SpirePrefixPatch
    private static SpireReturn<Void> overrideRenderInfo(CharacterOption __instance, SpriteBatch sb,
            String[] ___TEXT, float ___infoX, float ___infoY,
            String ___hp, int ___gold, String ___flavorText, int ___unlocksRemaining) {

        if (__instance.name.isEmpty() || !(__instance.c instanceof AbstractAwakener))
            return SpireReturn.Continue();

        if (!Settings.isMobile) {
            FontHelper.renderSmartText(
                    sb,
                    FontHelper.bannerNameFont,
                    __instance.name,
                    ___infoX - 35F * Settings.scale,
                    ___infoY + NAME_OFFSET_Y, 99999F,
                    38F * Settings.scale,
                    Settings.GOLD_COLOR);

            sb.draw(ImageMaster.TP_HP,
                    ___infoX - 10F * Settings.scale - 32F,
                    ___infoY + 95F * Settings.scale - 32F,
                    32F, 32F, 64F, 64F,
                    Settings.scale, Settings.scale,
                    0F, 0, 0, 64, 64, false, false);

            FontHelper.renderSmartText(
                    sb,
                    FontHelper.tipHeaderFont,
                    ___TEXT[4] + ___hp,
                    ___infoX + 18F * Settings.scale,
                    ___infoY + 102F * Settings.scale,
                    10000F, 10000F,
                    Settings.RED_TEXT_COLOR);

            /* Draw more right */
            sb.draw(ImageMaster.TP_GOLD,
                    ___infoX + (GOLD_XOFFSET + 190F) * Settings.scale - 32F,
                    ___infoY + 95F * Settings.scale - 32F,
                    32F, 32F, 64F, 64F,
                    Settings.scale, Settings.scale,
                    0F, 0, 0, 64, 64, false, false);

            FontHelper.renderSmartText(
                    sb,
                    FontHelper.tipHeaderFont,
                    ___TEXT[5] + Integer.toString(___gold),
                    ___infoX + (GOLD_XOFFSET + 220F) * Settings.scale,
                    ___infoY + 102F * Settings.scale,
                    10000F, 10000F, Settings.GOLD_COLOR);

            if (__instance.selected)
                FontHelper.renderSmartText(
                        sb,
                        FontHelper.tipHeaderFont,
                        ___flavorText,
                        ___infoX - 26F * Settings.scale,
                        ___infoY + 40F * Settings.scale,
                        10000F, 30F * Settings.scale,
                        Settings.CREAM_COLOR);

            if (___unlocksRemaining > 0) {
                FontHelper.renderSmartText(
                        sb,
                        FontHelper.tipHeaderFont,
                        Integer.toString(___unlocksRemaining) + ___TEXT[6],
                        ___infoX - 26F * Settings.scale,
                        ___infoY - 112F * Settings.scale,
                        10000F, 10000F,
                        Settings.CREAM_COLOR);

                int unlockProgress = UnlockTracker.getCurrentProgress(__instance.c.chosenClass);
                int unlockCost = UnlockTracker.getCurrentScoreCost(__instance.c.chosenClass);

                FontHelper.renderSmartText(
                        sb,
                        FontHelper.tipHeaderFont,
                        Integer.toString(unlockProgress) + "/" + unlockCost + ___TEXT[9],
                        ___infoX - 26F * Settings.scale,
                        ___infoY - 140F * Settings.scale,
                        10000F, 10000F,
                        Settings.CREAM_COLOR);
            }

            ReflectionHacks.privateMethod(CharacterOption.class, "renderRelics", SpriteBatch.class)
                    .invoke(__instance, sb);
        } else {
            FontHelper.renderSmartText(
                    sb,
                    FontHelper.bannerNameFont,
                    __instance.name,
                    ___infoX - 35F * Settings.scale,
                    ___infoY + 350F * Settings.scale,
                    99999F, 38F * Settings.scale,
                    Settings.GOLD_COLOR, 1.1F);

            sb.draw(ImageMaster.TP_HP,
                    ___infoX - 10F * Settings.scale - 32F,
                    ___infoY + 230F * Settings.scale - 32F,
                    32F, 32F, 64F, 64F,
                    Settings.scale, Settings.scale,
                    0F, 0, 0, 64, 64, false, false);

            FontHelper.renderSmartText(
                    sb,
                    FontHelper.buttonLabelFont,
                    ___TEXT[4] + ___hp,
                    ___infoX + 18F * Settings.scale,
                    ___infoY + 243F * Settings.scale,
                    10000F, 10000F,
                    Settings.RED_TEXT_COLOR, 0.8F);

            sb.draw(ImageMaster.TP_GOLD,
                    ___infoX + (GOLD_XOFFSET + 260F) * Settings.scale - 32F,
                    ___infoY + 230F * Settings.scale - 32F,
                    32F, 32F, 64F, 64F,
                    Settings.scale, Settings.scale,
                    0F, 0, 0, 64, 64, false, false);

            FontHelper.renderSmartText(
                    sb,
                    FontHelper.buttonLabelFont,
                    ___TEXT[5] + Integer.toString(___gold),
                    ___infoX + (GOLD_XOFFSET + 290F) * Settings.scale,
                    ___infoY + 243F * Settings.scale,
                    10000F, 10000F, Settings.GOLD_COLOR, 0.8F);

            if (__instance.selected)
                FontHelper.renderSmartText(
                        sb,
                        FontHelper.buttonLabelFont,
                        ___flavorText,
                        ___infoX - 26F * Settings.scale,
                        ___infoY + 170F * Settings.scale,
                        10000F, 40F * Settings.scale,
                        Settings.CREAM_COLOR, 0.9F);

            if (___unlocksRemaining > 0) {
                FontHelper.renderSmartText(
                        sb,
                        FontHelper.buttonLabelFont,
                        Integer.toString(___unlocksRemaining) + ___TEXT[6],
                        ___infoX - 26F * Settings.scale,
                        ___infoY - 60F * Settings.scale,
                        10000F, 10000F,
                        Settings.CREAM_COLOR, 0.8F);

                int unlockProgress = UnlockTracker.getCurrentProgress(__instance.c.chosenClass);
                int unlockCost = UnlockTracker.getCurrentScoreCost(__instance.c.chosenClass);

                FontHelper.renderSmartText(
                        sb,
                        FontHelper.buttonLabelFont,
                        Integer.toString(unlockProgress) + "/" + unlockCost + ___TEXT[9],
                        ___infoX - 26F * Settings.scale,
                        ___infoY - 100F * Settings.scale,
                        10000F, 10000F,
                        Settings.CREAM_COLOR, 0.8F);
            }

            ReflectionHacks.privateMethod(CharacterOption.class, "renderRelics", SpriteBatch.class)
                    .invoke(__instance, sb);
        }

        return SpireReturn.Return();
    }
}
