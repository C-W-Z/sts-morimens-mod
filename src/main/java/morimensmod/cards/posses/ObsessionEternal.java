package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.p;
import static morimensmod.util.Wiz.powerAmount;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class ObsessionEternal extends AbstractPosse {

    public final static String ID = makeID(ObsessionEternal.class.getSimpleName());

    // for register to CardLibrary
    public ObsessionEternal() {
        super(ID, null, PosseType.UNLIMITED);
    }

    public ObsessionEternal(AbstractAwakener p, PosseType type) {
        super(ID, p, type);
    }

    @Override
    public void activate() {
        applyToSelf(new StrengthPower(p(), 3));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                int str = powerAmount(p(), StrengthPower.POWER_ID) - powerAmount(p(), LoseStrengthPower.POWER_ID);
                int tmpStr = MathUtils.ceil(str * 0.25F);
                applyToSelf(new StrengthPower(p(), tmpStr));
                applyToSelf(new LoseStrengthPower(p(), tmpStr));
                isDone = true;
            }
        });
    }
}
