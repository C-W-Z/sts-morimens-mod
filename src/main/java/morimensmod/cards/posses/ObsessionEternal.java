package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.powerAmount;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class ObsessionEternal extends AbstractPosse {

    public final static String ID = makeID(ObsessionEternal.class.getSimpleName());

    // for register to CardLibrary
    public ObsessionEternal() {
        this(null, PosseType.UNLIMITED);
    }

    public ObsessionEternal(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    @Override
    public void activate() {
        applyToSelf(new StrengthPower(awaker, 3));
        actB(() -> {
            int str = powerAmount(awaker, StrengthPower.POWER_ID) - powerAmount(awaker, LoseStrengthPower.POWER_ID);
            int tmpStr = MathUtils.ceil(str * 0.25F);
            applyToSelf(new StrengthPower(awaker, tmpStr));
            applyToSelf(new LoseStrengthPower(awaker, tmpStr));
        });
    }
}
