package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.PowerTip;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.actions.KeyflareChangeAction;
import morimensmod.cards.posses.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterPosse;
import morimensmod.misc.PosseType;

public class ChaosRelic extends AbstractEasyRelic implements OnAfterPosse {
    public static final String ID = makeID(ChaosRelic.class.getSimpleName());

    private static final int KEYFLARE = 250;
    private static final int REALM_MASTERY = 10;
    private static final int ALIEMUS = 1;

    public ChaosRelic() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL);
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
    public String getUpdatedDescription() {
        if (!(p() instanceof AbstractAwakener))
            return String.format(DESCRIPTIONS[0], KEYFLARE, REALM_MASTERY, ALIEMUS);
        AbstractAwakener awaker = (AbstractAwakener) p();
        return String.format(DESCRIPTIONS[0], KEYFLARE, REALM_MASTERY, ALIEMUS)
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
