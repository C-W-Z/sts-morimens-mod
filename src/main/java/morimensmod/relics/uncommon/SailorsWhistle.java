package morimensmod.relics.uncommon;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.HealAction;

import morimensmod.relics.AbstractEasyRelic;

public class SailorsWhistle extends AbstractEasyRelic {
    public static final String ID = makeID(SailorsWhistle.class.getSimpleName());

    private static final int PER_BLOCK = 4;
    private static final int HEAL_PERCENT_OF_BLOCK = 25;

    public SailorsWhistle() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);
        counter = 0;
    }

    @Override
    public int onPlayerGainedBlock(float blockAmount) {
        counter++;
        if (counter >= PER_BLOCK) {
            counter -= PER_BLOCK;
            flash();
            addToTop(new HealAction(p(), null, MathUtils.ceil(blockAmount * HEAL_PERCENT_OF_BLOCK / 100F)));
        }
        return super.onPlayerGainedBlock(blockAmount);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], PER_BLOCK, HEAL_PERCENT_OF_BLOCK);
    }
}
