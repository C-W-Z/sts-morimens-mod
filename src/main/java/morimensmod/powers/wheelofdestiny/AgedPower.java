package morimensmod.powers.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.actions.KeyflareChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.powers.AbstractEasyPower;

public class AgedPower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(AgedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int KEYFLARE_PER_AMOUNT = 500;

    public AgedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount * KEYFLARE_PER_AMOUNT);
    }

    @Override
    public void onVictory() {
        if (!(owner instanceof AbstractAwakener))
            return;
        flash();
        new KeyflareChangeAction((AbstractPlayer) owner, amount * KEYFLARE_PER_AMOUNT).update();
    }
}
