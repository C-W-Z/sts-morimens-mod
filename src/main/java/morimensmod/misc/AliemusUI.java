package morimensmod.misc;

import basemod.ClickableUIElement;
import morimensmod.actions.AliemusExhaustAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnBeforeExalt;
import morimensmod.interfaces.OnBeforeExaltTipRender;
import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeUIPath;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.p;
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
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
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
        drawCentered(sb, ICON, centerX, centerY, SCALE);
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
        ArrayList<PowerTip> tips = new ArrayList<>();
        tips.add(new PowerTip(TEXT.TEXT[0], TEXT.TEXT[1]));
        if (p() instanceof AbstractAwakener) {
            AbstractAwakener awaker = (AbstractAwakener) p();

            // 呼叫所有 Power 的 hook
            for (AbstractPower p : awaker.powers)
                if (p instanceof OnBeforeExaltTipRender)
                    ((OnBeforeExaltTipRender) p).onBeforeExaltTipRender(awaker);
            // 呼叫所有遺物的 hook
            for (AbstractRelic r : awaker.relics)
                if (r instanceof OnBeforeExaltTipRender)
                    ((OnBeforeExaltTipRender) r).onBeforeExaltTipRender(awaker);
            // 呼叫姿態（Stance）的 hook
            if (awaker.stance instanceof OnBeforeExaltTipRender)
                ((OnBeforeExaltTipRender) awaker.stance).onBeforeExaltTipRender(awaker);

            tips.add(new PowerTip(awaker.exalt.getExaltTitle(), awaker.exalt.getExaltDescription()));
            tips.add(new PowerTip(awaker.exalt.getOverExaltTitle(), awaker.exalt.getOverExaltDescription()));
        }
        TipHelper.queuePowerTips(fontX, y + Settings.yScale * 400f, tips);
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
                || !(p() instanceof AbstractAwakener) || AbstractAwakener.exalting)
            return;

        if (AbstractAwakener.exaltedThisTurn >= AbstractAwakener.maxExaltedPerTurn) {
            showThoughtBubble(TEXT.EXTRA_TEXT[1], 3.0F);
            return;
        }

        if (AbstractAwakener.aliemus < AbstractAwakener.maxAliemus) {
            showThoughtBubble(TEXT.EXTRA_TEXT[0], 3.0F);
            return;
        }

        AbstractAwakener.exalting = true;
        AbstractAwakener.exaltedThisTurn++;

        AbstractAwakener awaker = (AbstractAwakener) p();

        // 呼叫所有 Power 的 hook
        for (AbstractPower p : awaker.powers)
            if (p instanceof OnBeforeExalt)
                ((OnBeforeExalt) p).onBeforeExalt(awaker);
        // 呼叫所有遺物的 hook
        for (AbstractRelic r : awaker.relics)
            if (r instanceof OnBeforeExalt)
                ((OnBeforeExalt) r).onBeforeExalt(awaker);
        // 呼叫姿態（Stance）的 hook
        if (awaker.stance instanceof OnBeforeExalt)
            ((OnBeforeExalt) awaker.stance).onBeforeExalt(awaker);

        if (AbstractAwakener.aliemus >= AbstractAwakener.extremeAlimus)
            awaker.exalt.overExalt();
        else
            awaker.exalt.exalt();

        atb(new AliemusExhaustAction(awaker, AbstractAwakener.aliemus >= AbstractAwakener.extremeAlimus));
    }

    @Override
    public void update() {
        super.update();
        if (this.hitbox.hovered && InputHelper.justClickedRight) {
            this.onRightClick();
        }
    }

    public static boolean loadAliemusUI() {
        if (CardCrawlGame.dungeon == null || !(AbstractDungeon.player instanceof AbstractAwakener)
                || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
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
