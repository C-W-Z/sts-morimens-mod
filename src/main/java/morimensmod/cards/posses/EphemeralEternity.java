package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.ULTRA_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.helpers.CardModifierManager;
import morimensmod.cardmodifiers.EtherealModifier;
import morimensmod.cardmodifiers.ExhaustModifier;
import morimensmod.cards.AbstractPosse;
import morimensmod.cards.chaos.Defend;
import morimensmod.cards.chaos.Strike;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class EphemeralEternity extends AbstractPosse {

    public final static String ID = makeID(EphemeralEternity.class.getSimpleName());

    private ArrayList<AbstractCard> previews = new ArrayList<>();
    private int previewIndex = 0;
    private float rotationTimer = 0.0F;
    private static final float CARD_PREVIEW_TIME = 1.0F; // second

    // for register to CardLibrary
    public EphemeralEternity() {
        this(null, PosseType.UNLIMITED);
    }

    public EphemeralEternity(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
        previews.add(new Strike());
        previews.add(new Defend());
        this.cardsToPreview = previews.get(0);
    }

    @Override
    public void activate() {
        atb(new GainEnergyAction(1));

        AbstractCard strike = new Strike();
        CardModifierManager.addModifier(strike, new ExhaustModifier());
        CardModifierManager.addModifier(strike, new EtherealModifier());
        atb(new MakeTempCardInHandAction(strike));

        AbstractCard defend = new Defend();
        CardModifierManager.addModifier(defend, new ExhaustModifier());
        CardModifierManager.addModifier(defend, new EtherealModifier());
        atb(new MakeTempCardInHandAction(defend));

        if (p().getCardColor() == ULTRA_COLOR) {
            applyToSelf(new StrengthPower(p(), 2));
            applyToSelf(new LoseStrengthPower(p(), 2));
            applyToSelf(new DexterityPower(p(), 1));
            applyToSelf(new LoseDexterityPower(p(), 1));
        }
    }

   @Override
    public void renderCardPreview(SpriteBatch sb) {
        super.renderCardPreview(sb);
        updateCardPreview();
    }

    @Override
    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        super.renderCardPreviewInSingleView(sb);
        updateCardPreview();
    }

    private void updateCardPreview() {
        if (!this.previews.isEmpty()) {
            this.rotationTimer -= Gdx.graphics.getDeltaTime();
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = CARD_PREVIEW_TIME; // 每秒切換一次
                this.previewIndex = (this.previewIndex + 1) % this.previews.size();
                this.cardsToPreview = this.previews.get(this.previewIndex);
            }
        }
    }
}
