package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.actions.PosseAction;
import morimensmod.cards.AbstractPosse;
import morimensmod.interfaces.OnAfterPosse;
import morimensmod.misc.PosseType;

public class PosseTwicePower extends AbstractEasyPower implements OnAfterPosse {

    private static final Logger logger = LogManager.getLogger(PosseTwicePower.class);

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

    @Override
    public void onAfterPosse(AbstractPosse posse, int exhaustKeyflare) {
        if (posse.getType() == PosseType.UNLIMITED || posse.getType() == PosseType.TMP)
            return;

        logger.debug("type: " + posse.getType() + ", posse:" + posse.cardID);

        addToBot(new ReducePowerAction(posse.getAwakener(), posse.getAwakener(), this, 1));
        addToBot(new PosseAction(posse.getAwakener(), PosseType.TMP, posse, true));
    }
}
