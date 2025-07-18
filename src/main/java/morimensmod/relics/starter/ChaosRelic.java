package morimensmod.relics.starter;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.actions.KeyflareChangeAction;
import morimensmod.cards.posses.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterPosse;
import morimensmod.misc.PosseType;
import morimensmod.relics.AbstractEasyRelic;

public class ChaosRelic extends AbstractEasyRelic implements OnAfterPosse {
    public static final String ID = makeID(ChaosRelic.class.getSimpleName());

    private static final int COMMON_RELIC = 1;
    private static final int KEYFLARE = 250;
    private static final int REALM_MASTERY = 10;
    private static final int ALIEMUS = 1;

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
        AbstractDungeon.topLevelEffects.add(new AbstractGameEffect() {
            private boolean triggered = false;

            @Override
            public void update() {
                if (!triggered && AbstractDungeon.player != null && AbstractDungeon.getCurrRoom() != null) {
                    triggered = true;
                    // TODO: 未來可以做一個圖示是問號的遺物，效果是變成隨機遺物，也就是移除自身並獲得另一個隨機遺物
                    // AbstractDungeon.player.loseRelic(relicId);

                    // 隨機選一個普通遺物
                    AbstractRelic newRelic = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON);
                    // 實際給予
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                            Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, newRelic);
                }
                this.isDone = triggered;
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
}
