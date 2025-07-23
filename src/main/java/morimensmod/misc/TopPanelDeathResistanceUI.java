package morimensmod.misc;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeUIPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import basemod.ReflectionHacks;
import basemod.TopPanelItem;
import morimensmod.characters.AbstractAwakener;
import morimensmod.util.TexLoader;

public class TopPanelDeathResistanceUI extends TopPanelItem {

    public static final String ID = makeID(TopPanelDeathResistanceUI.class.getSimpleName());
    private static final UIStrings TEXT = CardCrawlGame.languagePack.getUIString(ID);

    private static float ICON_Y;

    public TopPanelDeathResistanceUI() {
        super(getTexture(), ID);
        ICON_Y = ReflectionHacks.getPrivateStatic(TopPanel.class, "ICON_Y");
    }

    protected static Texture getTexture() {
        return TexLoader.getTexture(makeUIPath(removeModID(ID) + ".png"));
    }

    @Override
    protected void onClick() {
    }

    @Override
    protected void onHover() {
        ArrayList<PowerTip> tips = new ArrayList<>();
        tips.add(new PowerTip(TEXT.TEXT[0], TEXT.TEXT[1]));
        TipHelper.queuePowerTips(this.x - 64 * Settings.xScale, ICON_Y - 50F * Settings.yScale, tips);
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        float halfWidth = this.image.getWidth() / 2F;
        // see TopPanel.renderTopRightIcons(SpriteBatch sb)
        String text;
        if (p() instanceof AbstractAwakener)
            text = AbstractAwakener.getDeathResistanceUIText();
        else
            text = "0%";

        FontHelper.renderFontCentered(
                sb,
                FontHelper.topPanelAmountFont,
                text,
                this.x - halfWidth + halfWidth * Settings.scale + 36F * Settings.scale,
                ICON_Y + 16F * Settings.scale,
                Color.WHITE);
    }
}
