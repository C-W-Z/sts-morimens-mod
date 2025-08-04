package morimensmod.powers.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import basemod.BaseMod;
import morimensmod.characters.AbstractAwakener;
import morimensmod.powers.AbstractEasyPower;

public class WheelUnseenPower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(WheelUnseenPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int KEYFLARE_LIMIT = 100;
    public static final int HAND_CARD_LIMIT = 2;

    public WheelUnseenPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void onInitialApplication() {
        BaseMod.MAX_HAND_SIZE += HAND_CARD_LIMIT * amount;
        AbstractAwakener.updateMaxKeyflareScale(KEYFLARE_LIMIT * amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        BaseMod.MAX_HAND_SIZE += HAND_CARD_LIMIT * stackAmount;
        AbstractAwakener.updateMaxKeyflareScale(KEYFLARE_LIMIT * stackAmount);
        super.stackPower(stackAmount);
    }

    @Override
    public void reducePower(int reduceAmount) {
        if (reduceAmount > amount)
            reduceAmount = amount;
        BaseMod.MAX_HAND_SIZE -= HAND_CARD_LIMIT * reduceAmount;
        AbstractAwakener.updateMaxKeyflareScale(-KEYFLARE_LIMIT * reduceAmount);
        super.reducePower(reduceAmount);
    }

    @Override
    public void onRemove() {
        BaseMod.MAX_HAND_SIZE -= HAND_CARD_LIMIT * amount;
        AbstractAwakener.updateMaxKeyflareScale(-KEYFLARE_LIMIT * amount);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], KEYFLARE_LIMIT * amount, HAND_CARD_LIMIT * amount);
    }
}
