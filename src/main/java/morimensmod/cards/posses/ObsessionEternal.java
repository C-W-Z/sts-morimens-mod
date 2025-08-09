package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.getPowerAmount;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ObsessionEternal extends AbstractPosse {

    public final static String ID = makeID(ObsessionEternal.class.getSimpleName());

    public ObsessionEternal() {
        super(ID);
    }

    @Override
    public void activate() {
        applyToSelf(new StrengthPower(awaker, 3));
        actB(() -> {
            int str = getPowerAmount(awaker, StrengthPower.POWER_ID) - getPowerAmount(awaker, LoseStrengthPower.POWER_ID);
            int tmpStr = MathUtils.ceil(str * 0.25F);
            applyToSelf(new StrengthPower(awaker, tmpStr));
            applyToSelf(new LoseStrengthPower(awaker, tmpStr));
        });
    }
}
