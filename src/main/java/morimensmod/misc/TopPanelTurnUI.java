package morimensmod.misc;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeUIPath;
import static morimensmod.util.General.removeModID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import basemod.ReflectionHacks;
import basemod.TopPanelItem;
import morimensmod.util.TexLoader;

public class TopPanelTurnUI extends TopPanelItem {

    public static final String ID = makeID(TopPanelTurnUI.class.getSimpleName());

    private static float ICON_Y;

    public TopPanelTurnUI() {
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
        super.onHover();
        // TODO: show Tips
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        float halfWidth = this.image.getWidth() / 2F;
        // float halfHeight = (float)this.image.getHeight() / 2.0F;

        // see TopPanel.renderTopRightIcons(SpriteBatch sb)
        FontHelper.renderFontRightTopAligned(
                sb,
                FontHelper.topPanelAmountFont,
                Integer.toString(GameActionManager.turn),
                this.x - halfWidth + halfWidth * Settings.scale + 58F * Settings.scale,
                ICON_Y + 25F * Settings.scale,
                Color.WHITE);
    }
}
