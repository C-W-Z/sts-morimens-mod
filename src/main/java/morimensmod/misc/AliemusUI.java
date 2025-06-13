package morimensmod.misc;

import basemod.ClickableUIElement;
import morimensmod.characters.AbstractAwakener;
import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeUIPath;
import static morimensmod.MorimensMod.modID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class AliemusUI extends ClickableUIElement {
    public Hitbox hb;
    private static final float hb_w = 130.0F * Settings.scale;
    private static final float hb_h = 130.0F * Settings.scale;
    private static final float baseX = 50.0F * Settings.scale;
    private static final float baseY = 400.0F * Settings.scale;
    private final float x = baseX;
    private final float y = baseY;
    public static float fontScale = 1.0F;

    private static final float RADIUS = 50.0F * Settings.scale;
    private static final int SEGMENTS = 100;

    private static final Texture BACKGROUND = TexLoader.getTexture(makeUIPath("Aliemus.png"));

    private static final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public static AliemusUI UI;

    public AliemusUI() {
        super(BACKGROUND, baseX, baseY, hb_w, hb_h);
        this.image = BACKGROUND;

        hb = new Hitbox(x, y, hb_w, hb_h); // square hitbox for the soul vessel, honestly no idea what the x y does here
        // this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, IMG_DIM, IMG_DIM, false,
        // false);
        this.setClickable(true);
    }

    public void render(SpriteBatch sb, float current_x) {
        // sb.end(); // 結束 SpriteBatch，切換為 shape 模式

        // shapeRenderer.setProjectionMatrix(sb.getProjectionMatrix());
        // shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // // 外圈（灰色圓形）
        // shapeRenderer.setColor(Color.DARK_GRAY);
        // shapeRenderer.circle(x, y, RADIUS, SEGMENTS);
        // shapeRenderer.setColor(Color.BLACK);
        // shapeRenderer.circle(x, y, RADIUS - 6f * Settings.scale); // 遮住內部，形成圓環

        // // 根據能量顯示圓弧（彩色）
        // float percent = (float) AbstractAwakener.aliemus /
        // AbstractAwakener.maxAliemus;
        // if (percent > 0) {
        // shapeRenderer.setColor(Color.CYAN); // 可改為漸層色
        // shapeRenderer.arc(x, y, RADIUS, 90, -360 * percent, SEGMENTS); // 順時針
        // shapeRenderer.setColor(Color.BLACK);
        // shapeRenderer.circle(x, y, RADIUS - 6f * Settings.scale); // 遮住內部，變成環
        // }

        // shapeRenderer.end();

        // sb.begin(); // 繼續用 SpriteBatch 畫別的東西

        sb.draw(BACKGROUND,
                x + 50F * Settings.scale - BACKGROUND.getWidth() / 2F,
                y + 50F * Settings.scale - BACKGROUND.getHeight() / 2F,
                BACKGROUND.getWidth() / 2F,
                BACKGROUND.getHeight() / 2F,
                (float) BACKGROUND.getWidth(),
                (float) BACKGROUND.getHeight(),
                Settings.scale * 0.75F,
                Settings.scale * 0.75F,
                0,
                0,
                0,
                BACKGROUND.getWidth(),
                BACKGROUND.getHeight(),
                false,
                false); // the image

        FontHelper.renderFontCentered(
                sb,
                FontHelper.energyNumFontBlue,
                AbstractAwakener.aliemus + "/" + AbstractAwakener.maxAliemus,
                x + 50F * Settings.scale,
                y + 50F * Settings.scale,
                Color.WHITE,
                fontScale); // the soul count
    }

    /*
     * private void updateHitboxPosition(float x, float y){
     * hb.translate(x - 150f * Settings.scale, y - 130f * Settings.scale);
     * }
     */

    @Override
    protected void onHover() {
        TipHelper.renderGenericTip(x - Settings.xScale * 20f, y + Settings.yScale *
                250f, "狂氣",
                "Something Here"); // popup text
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
        if (CardCrawlGame.dungeon != null && AbstractDungeon.player instanceof AbstractAwakener && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
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
