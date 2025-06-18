package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.actions.PosseAction;
import morimensmod.cards.AbstractPosse;
import morimensmod.interfaces.OnBeforePosse;
import morimensmod.misc.PosseType;

public class PosseTwicePower extends AbstractEasyPower implements OnBeforePosse {

    public final static String POWER_ID = makeID(PosseTwicePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PosseTwicePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    /**
     * 必須是onBeforePosse，
     * 因為Posse的activate中都是用addToTop，
     * 所以雙發的鑰令如果要在原本的鑰令執行後再執行的話，
     * 必須比原本的鑰令先addToTop
     */
    @Override
    public void onBeforePosse(AbstractPosse posse, int exhaustKeyflare) {
        if (posse.getPurgeOnUse())
            return;
        addToTop(new PosseAction(posse.getAwakener(), PosseType.UNLIMITED, posse, true));
        addToTop(new ReducePowerAction(posse.getAwakener(), posse.getAwakener(), this, 1));
    }
}
