package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class NegentropyPower extends PersistentPower {

    public final static String POWER_ID = makeID(NegentropyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int INVOKE_AMOUNT = 3; // 每三層觸發，移除3層

    // only for AutoAdd
    public NegentropyPower() {
        this(null, 1);
    }

    public NegentropyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public PersistentPower newPower(AbstractCreature owner, int amount) {
        return new NegentropyPower(owner, amount);
    }
}
