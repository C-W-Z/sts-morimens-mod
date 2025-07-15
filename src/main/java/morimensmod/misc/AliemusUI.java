package morimensmod.misc;

import basemod.ClickableUIElement;
import morimensmod.actions.ExaltAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.config.ModSettings;
import morimensmod.powers.SealPower;
import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeUIPath;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.p;
import static morimensmod.util.Wiz.isInCombat;
import static morimensmod.util.WizArt.drawCentered;
import static morimensmod.util.WizArt.showThoughtBubble;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

public class AliemusUI extends ClickableUIElement {

    private static final float SCALE = Settings.scale * ModSettings.CLICKABLE_UI_ICON_SCALE;
    private static final float hb_w = ModSettings.CLICKABLE_UI_ICON_SIZE * SCALE;
    private static final float hb_h = ModSettings.CLICKABLE_UI_ICON_SIZE * SCALE;
    private static final float baseX = 50F * Settings.scale;
    private static final float baseY = 400F * Settings.scale;
    private static final float centerX = baseX + hb_w / 2F;
    private static final float centerY = baseY + hb_h / 2F;
    private static final float fontX = baseX + hb_w + 0F * Settings.scale;
    private static final float fontScale = 1F;

    private static final Texture ICON = TexLoader.getTexture(makeUIPath("Aliemus.png"));
    private static final UIStrings TEXT = CardCrawlGame.languagePack
            .getUIString(makeID(AliemusUI.class.getSimpleName()));

    private static AliemusUI UI;

    public AliemusUI() {
        super(ICON, baseX / Settings.scale, baseY / Settings.scale, hb_w / Settings.scale, hb_h / Settings.scale);
        this.setClickable(true);
    }

    private void render(SpriteBatch sb, float current_x) {
        if (!(p() instanceof AbstractAwakener))
            return;

        drawCentered(sb, ICON, centerX, centerY, SCALE);
        FontHelper.energyNumFontBlue.getData().setScale(fontScale);

        Color textColor = Color.GRAY;
        if (AbstractAwakener.enoughExaltCountThisTurn() && !p().hasPower(SealPower.POWER_ID)) {
            if (AbstractAwakener.enoughAliemusForOverExalt())
                textColor = Color.RED;
            else if (AbstractAwakener.enoughAliemusForExalt())
                textColor = Color.GOLD;
        }

        FontHelper.renderFontLeft(
                sb,
                FontHelper.energyNumFontBlue,
                AbstractAwakener.getAliemusUIText(),
                fontX,
                centerY,
                textColor);
        FontHelper.energyNumFontBlue.getData().setScale(1F);
    }

    @Override
    protected void onHover() {
        if (AbstractDungeon.isScreenUp || !(p() instanceof AbstractAwakener))
            return;
        // popup text
        ArrayList<PowerTip> tips = new ArrayList<>();
        AbstractAwakener awaker = (AbstractAwakener) p();
        tips.add(new PowerTip(TEXT.TEXT[2], TEXT.TEXT[3] + awaker.aliemusRegen + " "));
        tips.add(new PowerTip(awaker.getExaltTitle() + TEXT.TEXT[0], awaker.getExaltDescription()));
        tips.add(new PowerTip(awaker.getOverExaltTitle() + TEXT.TEXT[1], awaker.getOverExaltDescription()));
        TipHelper.queuePowerTips(fontX, baseY + Settings.yScale * 400f, tips);
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    protected void onClick() {
    }

    protected void onRightClick() {
        if (p().currentHealth <= 0 || p().isDeadOrEscaped()
                || AbstractDungeon.isScreenUp || AbstractDungeon.actionManager.turnHasEnded
                || !(p() instanceof AbstractAwakener))
            return;

        if (p().hasPower(SealPower.POWER_ID)) {
            showThoughtBubble(TEXT.EXTRA_TEXT[3], ModSettings.UI_THOUGHT_BUBBLE_TIME);
            return;
        }

        if (AbstractAwakener.isPossing() || AbstractAwakener.isExalting()
                || !AbstractDungeon.actionManager.cardQueue.isEmpty()) {
            showThoughtBubble(TEXT.EXTRA_TEXT[2], ModSettings.UI_THOUGHT_BUBBLE_TIME);
            return;
        }

        if (!AbstractAwakener.enoughExaltCountThisTurn()) {
            showThoughtBubble(TEXT.EXTRA_TEXT[0], ModSettings.UI_THOUGHT_BUBBLE_TIME);
            return;
        }

        if (!AbstractAwakener.enoughAliemusForExalt()) {
            showThoughtBubble(TEXT.EXTRA_TEXT[1], ModSettings.UI_THOUGHT_BUBBLE_TIME);
            return;
        }

        atb(new ExaltAction((AbstractAwakener) p()));
    }

    @Override
    public void update() {
        super.update();
        if (this.hitbox.hovered && InputHelper.justClickedRight) {
            this.onRightClick();
        }
    }

    public static boolean loadAliemusUI() {
        if (!(AbstractDungeon.player instanceof AbstractAwakener) || !isInCombat())
            return false;
        if (UI == null)
            UI = new AliemusUI();
        return true;
    }

    public static void renderAliemusUI(SpriteBatch sb, float current_x) {
        UI.render(sb, current_x);
    }

    public static void updateAliemusUI() {
        UI.update();
    }
}
