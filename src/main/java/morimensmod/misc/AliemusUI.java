package morimensmod.misc;

import basemod.ClickableUIElement;
import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.actions.MundusDecreeAction;
import morimensmod.cards.PileModalSelectCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.util.TexLoader;
import morimensmod.util.WizArt;

import static morimensmod.MorimensMod.makeUIPath;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.discardPile;
import static morimensmod.util.Wiz.drawPile;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
    private static final float hb_w = 120F * SCALE;
    private static final float hb_h = 120F * SCALE;
    private static final float baseX = 50F * Settings.scale;
    private static final float baseY = 400F * Settings.scale;
    private static final float centerX = baseX + 60F * SCALE;
    private static final float centerY = baseY + 60F * SCALE;
    private static final float fontX = baseX + 120F * SCALE + 0F * Settings.scale;
    private final float x = baseX;
    private final float y = baseY;
    public static float fontScale = 1F;

    private static final Texture ICON = TexLoader.getTexture(makeUIPath("Aliemus.png"));
    private static final UIStrings TEXT = CardCrawlGame.languagePack.getUIString("ALIEMUS");

    public static AliemusUI UI;

    public AliemusUI() {
        super(ICON, baseX, baseY, hb_w, hb_h);
        this.image = ICON;

        hb = new Hitbox(x, y, hb_w, hb_h); // square hitbox, honestly no idea what the x y does here
        this.setClickable(true);
    }

    public void render(SpriteBatch sb, float current_x) {
        WizArt.drawCentered(sb, ICON, centerX, centerY, SCALE);
        FontHelper.energyNumFontBlue.getData().setScale(fontScale);
        FontHelper.renderFontLeft(
                sb,
                FontHelper.energyNumFontBlue,
                AbstractAwakener.aliemus + "/" + AbstractAwakener.maxAliemus,
                fontX,
                centerY,
                Color.WHITE);
        FontHelper.energyNumFontBlue.getData().setScale(1F);
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
        ArrayList<AbstractCard> cardList = new ArrayList<>();

        for (AbstractCard c : drawPile().group) {
            System.out.println("c:" + c.name);
            cardList.add(new PileModalSelectCard(c, () -> atb(new MundusDecreeAction(c))));
        }

        for (AbstractCard c : discardPile().group) {
            System.out.println("c:" + c.name);
            cardList.add(new PileModalSelectCard(c, () -> atb(new MundusDecreeAction(c))));
        }

        System.out.println("cardList.size" + cardList.size());

        atb(new EasyModalChoiceAction(cardList));
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
