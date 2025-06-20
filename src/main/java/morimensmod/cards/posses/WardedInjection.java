package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;

import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class WardedInjection extends AbstractPosse {

    public final static String ID = makeID(WardedInjection.class.getSimpleName());

    // for register to CardLibrary
    public WardedInjection() {
        this(null, PosseType.UNLIMITED);
    }

    public WardedInjection(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    @Override
    public void activate() {
        addToBot(new GainBlockAction(awaker, awaker, 10));
        if (awaker.currentHealth < MathUtils.ceil(awaker.maxHealth * 0.25F))
            addToBot(new HealAction(awaker, awaker, 10));
    }
}
