package morimensmod.misc;

import basemod.ClickableUIElement;
import morimensmod.characters.AbstractAwakener;
import morimensmod.util.ModSettings;
import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeUIPath;
import static morimensmod.util.Wiz.isInCombat;
import static morimensmod.util.WizArt.drawCentered;
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
import com.megacrit.cardcrawl.localization.UIStrings;

public class DeathResistanceUI extends ClickableUIElement {

    private static final float SCALE = Settings.scale * ModSettings.CLICKABLE_UI_ICON_SCALE;
    private static final float hb_w = ModSettings.CLICKABLE_UI_ICON_SIZE * SCALE;
    private static final float hb_h = ModSettings.CLICKABLE_UI_ICON_SIZE * SCALE;
    private static final float baseX = 50F * Settings.scale;
    private static final float baseY = 500F * Settings.scale;
    private static final float centerX = baseX + hb_w / 2F;
    private static final float centerY = baseY + hb_h / 2F;
    private static final float fontX = baseX + hb_w + 0F * Settings.scale;
    private static final float fontScale = 1F;

    private static final Texture ICON = TexLoader.getTexture(makeUIPath("DeathResistance.png"));
    private static final UIStrings TEXT = CardCrawlGame.languagePack.getUIString(makeID(DeathResistanceUI.class.getSimpleName()));

    private static DeathResistanceUI UI;

    public DeathResistanceUI() {
        super(ICON, baseX / Settings.scale, baseY / Settings.scale, hb_w / Settings.scale, hb_h / Settings.scale);
        this.setClickable(false);
    }

    private void render(SpriteBatch sb, float current_x) {
        drawCentered(sb, ICON, centerX, centerY, SCALE);
        FontHelper.energyNumFontBlue.getData().setScale(fontScale);
        FontHelper.renderFontLeft(
                sb,
                FontHelper.energyNumFontBlue,
                AbstractAwakener.getDeathResistanceUIText(),
                fontX,
                centerY,
                Color.WHITE);
        FontHelper.energyNumFontBlue.getData().setScale(1F);
    }

    @Override
    protected void onHover() {
        // popup text
        ArrayList<PowerTip> tips = new ArrayList<>();
        tips.add(new PowerTip(TEXT.TEXT[0], TEXT.TEXT[1]));
        TipHelper.queuePowerTips(fontX, centerY + Settings.yScale * 200f, tips);
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    protected void onClick() {
    }

    public static boolean loadDeathResistanceUI() {
        if (!(AbstractDungeon.player instanceof AbstractAwakener) || !isInCombat())
            return false;
        if (UI == null)
            UI = new DeathResistanceUI();
        return true;
    }

    public static void renderDeathResistanceUI(SpriteBatch sb, float current_x) {
        UI.render(sb, current_x);
    }

    public static void updateDeathResistanceUI() {
        UI.update();
    }
}
