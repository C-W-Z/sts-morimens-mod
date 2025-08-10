package morimensmod.misc;

import static morimensmod.MorimensMod.makeCrystalPath;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.CardLib.getAllPosseCards;
import static morimensmod.util.General.removeModID;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.ReflectionHacks;
import basemod.interfaces.ISubscriber;
import me.antileaf.signature.card.AbstractSignatureCard;
import me.antileaf.signature.patches.card.SignaturePatch;
import me.antileaf.signature.utils.SignatureHelper;
import me.antileaf.signature.utils.internal.SignatureHelperInternal;
import morimensmod.cards.posses.AbstractPosse;
import morimensmod.config.ModConfig;
import morimensmod.util.TexLoader;

public class PosseSelectUI implements ISubscriber {

    // public static final String ID = PosseSelectUIID;

    protected static PosseSelectUI instance = null;

    public int index = 0;

    private AbstractCard cardToPreview;

    public Hitbox leftHb;

    public Hitbox rightHb;

    public String curName = "";

    public String nextName = "";

    public String prevName = "";

    public UIStrings uiStrings;

    private static final ArrayList<AbstractCard> posseList = getAllPosseCards();

    private static float centerX = Settings.WIDTH * 0.8F;
    private static float centerY = Settings.HEIGHT * 0.35F;

    public PosseSelectUI() {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID(PosseSelectUI.class.getSimpleName()));
        this.index = 0;
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        initialize();
    }

    public static PosseSelectUI getUI() {
        if (instance == null)
            instance = new PosseSelectUI();
        return instance;
    }

    public static AbstractPosse getPosse() {
        return (AbstractPosse) posseList.get(getUI().index);
    }

    private int getPosseIndex(String posseID) {
        for (int j = 0; j < posseList.size(); j++)
            if (posseList.get(j).cardID.equals(posseID))
                return j;
        return 0;
    }

    public void initialize() {
        String posseID = ModConfig.Char.getPosseSelect();
        int i = getPosseIndex(posseID);
        if (this.index != i && i >= 0)
            this.index = i;
        refresh();
    }

    public int prevIndex() {
        return (this.index - 1 < 0) ? (posseList.size() - 1) : (this.index - 1);
    }

    public int nextIndex() {
        return (this.index + 1 > posseList.size() - 1) ? 0 : (this.index + 1);
    }

    public void refresh() {
        this.cardToPreview = posseList.get(this.index);
        this.curName = cardToPreview.name;
        this.nextName = posseList.get(nextIndex()).name;
        this.prevName = posseList.get(prevIndex()).name;
    }

    public void update() {
        this.leftHb.move(centerX - 180.0F * Settings.scale, centerY);
        this.rightHb.move(centerX + 180.0F * Settings.scale, centerY);
        updateInput();
    }

    private void updateInput() {
        this.leftHb.update();
        this.rightHb.update();

        if (this.leftHb.clicked) {
            this.leftHb.clicked = false;
            CardCrawlGame.sound.play("UI_CLICK_1");
            this.index = prevIndex();
            ModConfig.Char.savePosseSelect(posseList.get(index).cardID);
            refresh();
        }

        if (this.rightHb.clicked) {
            this.rightHb.clicked = false;
            CardCrawlGame.sound.play("UI_CLICK_1");
            this.index = nextIndex();
            ModConfig.Char.savePosseSelect(posseList.get(index).cardID);
            refresh();
        }

        if (InputHelper.justClickedLeft) {
            if (this.leftHb.hovered)
                this.leftHb.clickStarted = true;
            if (this.rightHb.hovered)
                this.rightHb.clickStarted = true;
        }
    }

    public void render(SpriteBatch sb) {
        renderCard(sb, centerX, centerY);

        // FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, uiStrings.TEXT[0], centerX,
        //         centerY + 300.0F * Settings.scale, Color.WHITE, 1.25F);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, uiStrings.TEXT[0], centerX,
                centerY + cardToPreview.hb.height, Color.WHITE, 1.25F);

        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;

        FontHelper.renderFontCentered(
                sb,
                FontHelper.cardTitleFont,
                this.prevName,
                centerX - dist * 1.5F,
                centerY - dist * 0.75F,
                color);
        FontHelper.renderFontCentered(
                sb,
                FontHelper.cardTitleFont,
                this.nextName,
                centerX + dist * 1.5F,
                centerY - dist * 0.75F,
                color);
        FontHelper.renderFontCentered(
                sb,
                FontHelper.cardTitleFont,
                this.curName,
                centerX,
                centerY - dist * 0.25F,
                Settings.GOLD_COLOR);

        if (this.leftHb.hovered)
            sb.setColor(Color.LIGHT_GRAY);
        else
            sb.setColor(Color.WHITE);

        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb.cX - 24.0F, this.leftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F,
                Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        if (this.rightHb.hovered)
            sb.setColor(Color.LIGHT_GRAY);
        else
            sb.setColor(Color.WHITE);

        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.rightHb.cX - 24.0F, this.rightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F,
                48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        this.rightHb.render(sb);
        this.leftHb.render(sb);
    }

    private boolean isHovered(Hitbox hb) {
        return (InputHelper.mX > hb.x && InputHelper.mX < hb.x + hb.width && InputHelper.mY > hb.y
                && InputHelper.mY < hb.y + hb.height);
    }

    public void renderCard(SpriteBatch sb, float x, float y) {
        if (this.cardToPreview == null)
            return;

        Texture crystal = TexLoader.getTexture(makeCrystalPath(removeModID(cardToPreview.cardID) + ".png"));
        sb.draw(crystal,
                x - this.cardToPreview.hb.width / 2F,
                y,
                this.cardToPreview.hb.width,
                this.cardToPreview.hb.height);

        this.cardToPreview.current_x = x;
        // this.cardToPreview.current_y = y + 150.0F * Settings.scale;
        // this.cardToPreview.hb.move(x, y + 150.0F * Settings.scale);
        // this.cardToPreview.drawScale = 0.7F;
        // this.cardToPreview.render(sb);

        this.cardToPreview.current_y = y + this.cardToPreview.hb.height / 2F + 30F * Settings.scale;
        this.cardToPreview.hb.move(x, y + this.cardToPreview.hb.height / 2F);
        this.cardToPreview.drawScale = 1F;

        // TODO: 自己寫timer取代getSignatureTransparency()，就不需要判斷shouldUseSignature了
        if (SignatureHelperInternal.shouldUseSignature(cardToPreview))
            renderShadow(cardToPreview, sb);

        if (Settings.lineBreakViaCharacter) {
            // this.cardToPreview.renderDescriptionCN(sb);
            ReflectionHacks.privateMethod(AbstractCard.class, "renderDescriptionCN", SpriteBatch.class)
                    .invoke(this.cardToPreview, sb);
        } else {
            // this.cardToPreview.renderDescription(sb);
            ReflectionHacks.privateMethod(AbstractCard.class, "renderDescription", SpriteBatch.class)
                    .invoke(this.cardToPreview, sb);
        }

        // TODO: 自己寫timer取代Fields.forcedTimer
        if (isHovered(this.cardToPreview.hb)) {
            if (SignatureHelperInternal.shouldUseSignature(cardToPreview))
                SignatureHelperInternal.forceToShowDescription(cardToPreview);

            TipHelper.renderTipForCard(this.cardToPreview, sb, this.cardToPreview.keywords);
        } else if (SignatureHelperInternal.shouldUseSignature(cardToPreview)) {
            if (cardToPreview instanceof AbstractSignatureCard)
                ((AbstractSignatureCard) cardToPreview).signatureHoveredTimer = Math.max(0,
                        ((AbstractSignatureCard) cardToPreview).signatureHoveredTimer - Gdx.graphics.getDeltaTime());
            else
                SignaturePatch.Fields.forcedTimer.set(cardToPreview, Math.max(0F,
                        SignaturePatch.Fields.forcedTimer.get(cardToPreview) - Gdx.graphics.getDeltaTime()));
        }
    }

    protected void renderShadow(AbstractCard _inst, SpriteBatch sb) {
        Color renderColor = ReflectionHacks.getPrivate(_inst, AbstractCard.class, "renderColor");
        String shadow;
        float alpha = renderColor.a;

        // SignatureHelper.Style style = SignatureHelper.DEFAULT_STYLE;

        if (_inst instanceof AbstractSignatureCard) {
            AbstractSignatureCard c = (AbstractSignatureCard) _inst;
            renderColor.a *= c.getSignatureTransparency();
            shadow = c.description.size() >= 4 ||
                    (c.style.descShadowSmall == null || c.style.descShadowSmall.isEmpty()) ? c.style.descShadow
                            : c.style.descShadowSmall;
        } else {
            renderColor.a *= (float) ReflectionHacks
                    .privateStaticMethod(SignaturePatch.class, "getSignatureTransparency", AbstractCard.class)
                    .invoke(new Object[] { _inst });
            SignatureHelper.Info info = SignatureHelperInternal.getInfo(_inst.cardID);
            shadow = _inst.description.size() >= 4 ||
                    (info.style.descShadowSmall == null || info.style.descShadowSmall.isEmpty()) ? info.style.descShadow
                            : info.style.descShadowSmall;
        }

        if (shadow != null)
            ReflectionHacks
                    .privateMethod(AbstractCard.class, "renderHelper",
                            SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class,
                            float.class)
                    .invoke(_inst, sb, renderColor, SignatureHelperInternal.load(shadow), _inst.current_x,
                            _inst.current_y);

        renderColor.a = alpha;
    }
}
