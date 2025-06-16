package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import morimensmod.cards.AbstractEasyCard;

public class HandOfOblivionPower extends AbstractEasyPower {

    public static final Logger logger = LogManager.getLogger(HandOfOblivionPower.class);

    public final static String POWER_ID = makeID(HandOfOblivionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HandOfOblivionPower(AbstractCreature owner, int percent) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, percent);
    }

    @Override
    public void onInitialApplication() {
        AbstractEasyCard.baseStrikeDamageAmplify += amount;
        logger.info("onInitialApplication, AbstractEasyCard.baseStrikeDamageAmplify:" + AbstractEasyCard.baseStrikeDamageAmplify);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        AbstractEasyCard.baseStrikeDamageAmplify += stackAmount;
        logger.info("stackPower, AbstractEasyCard.baseStrikeDamageAmplify:" + AbstractEasyCard.baseStrikeDamageAmplify);
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        AbstractEasyCard.baseStrikeDamageAmplify -= reduceAmount;

        logger.info("reducePower, AbstractEasyCard.baseStrikeDamageAmplify:" + AbstractEasyCard.baseStrikeDamageAmplify);
    }

    @Override
    public void onRemove() {
        AbstractEasyCard.baseStrikeDamageAmplify -= amount;

        logger.info("onRemove, AbstractEasyCard.baseStrikeDamageAmplify:" + AbstractEasyCard.baseStrikeDamageAmplify);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}