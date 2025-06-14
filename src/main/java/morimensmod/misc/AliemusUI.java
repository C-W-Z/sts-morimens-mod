package morimensmod.misc;

import basemod.ClickableUIElement;
import morimensmod.characters.AbstractAwakener;
import morimensmod.util.TexLoader;
import morimensmod.util.WizArt;

import static morimensmod.MorimensMod.makeUIPath;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class AliemusUI extends ClickableUIElement {
    public Hitbox hb;
    private static final float SCALE = Settings.scale * 0.75F;
    private static final float hb_w = 128F * SCALE;
    private static final float hb_h = 128F * SCALE;
    private static final float baseX = 50F * SCALE;
    private static final float baseY = 400F * SCALE;
    private static final float centerX = baseX + 64F * SCALE;
    private static final float centerY = baseY + 64F * SCALE;
    private final float x = baseX;
    private final float y = baseY;
    public static float fontScale = 1.0F;

    private static final Texture BACKGROUND = TexLoader.getTexture(makeUIPath("Aliemus.png"));
    private static final UIStrings TEXT = CardCrawlGame.languagePack.getUIString("ALIEMUS");

    public static AliemusUI UI;

    public AliemusUI() {
        super(BACKGROUND, baseX, baseY, hb_w, hb_h);
        this.image = BACKGROUND;

        hb = new Hitbox(x, y, hb_w, hb_h); // square hitbox, honestly no idea what the x y does here
        this.setClickable(true);
    }

    public void render(SpriteBatch sb, float current_x) {
        WizArt.drawCentered(sb, BACKGROUND, centerX, centerY, SCALE);
        FontHelper.renderFontCentered(
                sb,
                FontHelper.energyNumFontBlue,
                AbstractAwakener.aliemus + "/" + AbstractAwakener.maxAliemus,
                centerX,
                centerY,
                Color.WHITE,
                fontScale); // the soul count
    }

    @Override
    protected void onHover() {
        // popup text
        TipHelper.renderGenericTip(x - Settings.xScale * 20f, y + Settings.yScale * 200f, TEXT.TEXT[0], TEXT.TEXT[1]);
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    protected void onClick() {
        System.out.println("超限爆發");
    }

    protected void onRightClick() {
        System.out.println("狂氣爆發");
    }

    @Override
    public void update() {
        super.update();
        if (this.hitbox.hovered && InputHelper.justClickedRight) {
            this.onRightClick();
        }
    }

    public static boolean loadAliemusUI() {
        if (CardCrawlGame.dungeon != null && AbstractDungeon.player instanceof AbstractAwakener
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (UI == null) {
                UI = new AliemusUI();
            }
            return true;
        }
        return false;
    }

    public static void renderAliemusUI(SpriteBatch sb, float current_x) {
        UI.render(sb, current_x);
    }

    public static void updateAliemusUI() {
        UI.update();
    }
}
