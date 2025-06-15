package morimensmod.powers;

import static morimensmod.MorimensMod.logger;
import static morimensmod.MorimensMod.makeID;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import morimensmod.cards.AbstractEasyCard;

public class HandOfOblivionPower extends AbstractEasyPower {
    public final static String POWER_ID = makeID(HandOfOblivionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HandOfOblivionPower(AbstractCreature owner, int percent) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, percent);
        AbstractEasyCard.baseStrikeDamageAmplify += percent;

        logger.info("HandOfOblivionPower, AbstractEasyCard.baseStrikeDamageMultiply:" + AbstractEasyCard.baseStrikeDamageAmplify);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        // A new Power() must be called before stack, so the baseStrikeDamageMultiply increment will be done in the constructor
        // AbstractEasyCard.baseStrikeDamageMultiply += stackAmount;
        // logger.info("stackPower, AbstractEasyCard.baseStrikeDamageMultiply:" + AbstractEasyCard.baseStrikeDamageMultiply);
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        AbstractEasyCard.baseStrikeDamageAmplify -= reduceAmount;

        logger.info("reducePower, AbstractEasyCard.baseStrikeDamageMultiply:" + AbstractEasyCard.baseStrikeDamageAmplify);
    }

    @Override
    public void onRemove() {
        AbstractEasyCard.baseStrikeDamageAmplify -= amount;

        logger.info("onRemove, AbstractEasyCard.baseStrikeDamageMultiply:" + AbstractEasyCard.baseStrikeDamageAmplify);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}