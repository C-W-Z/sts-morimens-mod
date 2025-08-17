package morimensmod.relics.starter;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import basemod.abstracts.CustomSavable;
import morimensmod.actions.AliemusChangeAction;
import morimensmod.actions.KeyflareChangeAction;
import morimensmod.cards.posses.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterPosse;
import morimensmod.misc.PosseType;
import morimensmod.relics.AbstractEasyRelic;

public class ChaosRelic extends AbstractEasyRelic implements OnAfterPosse, CustomSavable<Boolean> {

    private static Logger logger = LogManager.getLogger(ChaosRelic.class);

    public static final String ID = makeID(ChaosRelic.class.getSimpleName());

    private static final int COMMON_RELIC = 1;
    private static final int KEYFLARE = 250;
    private static final int REALM_MASTERY = 10;
    private static final int ALIEMUS = 1;

    private boolean relicObtained = false;

    public ChaosRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, AWAKENER_COLOR);
    }

    @Override
    public void onPlayerEndTurn() {
        if (!(p() instanceof AbstractAwakener))
            return;
        flash();
        addToBot(new KeyflareChangeAction(p(), KEYFLARE));
    }

    @Override
    public void onAfterPosse(AbstractPosse posse, int exhaustKeyflare) {
        if (!(p() instanceof AbstractAwakener))
            return;
        if (posse.getType() == PosseType.TMP)
            return;
        flash();
        AbstractAwakener awaker = (AbstractAwakener) p();
        addToBot(new AliemusChangeAction(awaker, ALIEMUS * awaker.getRealmMastry() / REALM_MASTERY));
    }

    @Override
    public void onEquip() {
        logger.info("onEquip");
        obtainRelic();
    }

    // called in Main Mod file
    public void obtainRelic() {
        if (relicObtained)
            return;
        AbstractDungeon.effectsQueue.add(new AbstractGameEffect() {
            @Override
            public void update() {
                if (!relicObtained && AbstractDungeon.player != null && AbstractDungeon.getCurrRoom() != null) {
                    relicObtained = true;
                    flash();

                    // TODO: 未來可以做一個圖示是問號的遺物，效果是變成隨機遺物，也就是移除自身並獲得另一個隨機遺物
                    // AbstractDungeon.player.loseRelic(relicId);

                    // 隨機選一個普通遺物
                    AbstractRelic newRelic = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON);
                    // 實際給予
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                            Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, newRelic);
                }
                this.isDone = relicObtained;
            }

            @Override
            public void render(SpriteBatch sb) {}

            @Override
            public void dispose() {}
        });
    }

    @Override
    public String getUpdatedDescription() {
        if (!(p() instanceof AbstractAwakener))
            return String.format(DESCRIPTIONS[0], COMMON_RELIC, KEYFLARE, REALM_MASTERY, ALIEMUS);
        AbstractAwakener awaker = (AbstractAwakener) p();
        return String.format(DESCRIPTIONS[0], COMMON_RELIC, KEYFLARE, REALM_MASTERY, ALIEMUS)
                + String.format(DESCRIPTIONS[1], awaker.getRealmMastry(), awaker.getRealmMastry() / REALM_MASTERY);
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        if (this.hb.hovered) {
            this.description = getUpdatedDescription();
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
        }
        super.renderTip(sb);
    }

    @Override
    public void onLoad(Boolean value) {
        relicObtained = Boolean.TRUE.equals(value);
    }

    @Override
    public Boolean onSave() {
        return relicObtained;
    }
}
