package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.att;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;

import morimensmod.cards.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class WardedInjection extends AbstractPosse {

    public final static String ID = makeID(WardedInjection.class.getSimpleName());

    // for register to CardLibrary
    public WardedInjection() {
        super(ID, null, PosseType.UNLIMITED);
    }

    public WardedInjection(AbstractAwakener p, PosseType type) {
        super(ID, p, type);
    }

    @Override
    public void activate() {
        if (p().currentHealth < MathUtils.ceil(p().maxHealth * 0.25F))
            att(new HealAction(p(), p(), 10));
        att(new GainBlockAction(p(), p(), 10));
    }
}
