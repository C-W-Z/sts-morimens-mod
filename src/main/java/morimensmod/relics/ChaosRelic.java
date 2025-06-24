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

    private static final int END_TURN_KEYFLARE = 250;

    public ChaosRelic() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL);
        counter = -1;
    }

    @Override
    public void onPlayerEndTurn() {
        if (!(p() instanceof AbstractAwakener))
            return;
        addToBot(new KeyflareChangeAction(p(), END_TURN_KEYFLARE));
    }

    @Override
    public void onAfterPosse(AbstractPosse posse, int exhaustKeyflare) {
        if (!(p() instanceof AbstractAwakener))
            return;
        if (posse.getType() == PosseType.TMP)
            return;
        AbstractAwakener awaker = (AbstractAwakener) p();
        addToBot(new AliemusChangeAction(awaker, awaker.getRealmMastry() / 10));
    }

    @Override
    public String getUpdatedDescription() {
        if (!(p() instanceof AbstractAwakener))
            return DESCRIPTIONS[0];
        AbstractAwakener awaker = (AbstractAwakener) p();
        return DESCRIPTIONS[0] + DESCRIPTIONS[1] + awaker.getRealmMastry() + DESCRIPTIONS[2]
                + awaker.getRealmMastry() / 10;
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
