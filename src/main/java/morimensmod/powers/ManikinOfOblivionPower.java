package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterExalt;

public class ManikinOfOblivionPower extends AbstractEasyPower implements OnAfterExalt {

    private static final Logger logger = LogManager.getLogger(ManikinOfOblivionPower.class);

    public final static String POWER_ID = makeID(ManikinOfOblivionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int RECHARGE_ALIEMUS_PERCENT = 20;

    public ManikinOfOblivionPower(AbstractCreature owner, int percent) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, percent);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractAwakener.baseAliemusAmplify += amount;
        AbstractAwakener.baseHealAmplify += amount;
        AbstractAwakener.basePoisonAmplify += amount;
        logger.debug("onInitialApplication, AbstractAwakener.baseAliemusAmplify:" + AbstractAwakener.baseAliemusAmplify);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        AbstractAwakener.baseAliemusAmplify += stackAmount;
        AbstractAwakener.baseHealAmplify += stackAmount;
        AbstractAwakener.basePoisonAmplify += stackAmount;
        logger.debug("stackPower, AbstractAwakener.baseAliemusAmplify:" + AbstractAwakener.baseAliemusAmplify);
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);

        AbstractAwakener.baseAliemusAmplify -= reduceAmount;
        AbstractAwakener.baseHealAmplify -= reduceAmount;
        AbstractAwakener.basePoisonAmplify -= reduceAmount;

        logger.debug("reducePower, AbstractAwakener.baseAliemusAmplify:" + AbstractAwakener.baseAliemusAmplify);
    }

    @Override
    public void onRemove() {
        AbstractAwakener.baseAliemusAmplify -= amount;
        AbstractAwakener.baseHealAmplify -= amount;
        AbstractAwakener.basePoisonAmplify -= amount;

        logger.debug("onRemove, AbstractAwakener.baseAliemusAmplify:" + AbstractAwakener.baseAliemusAmplify);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + RECHARGE_ALIEMUS_PERCENT + DESCRIPTIONS[2];
    }

    @Override
    public void onAfterExalt(AbstractAwakener awaker, int exhaustAliemus, boolean overExalt) {
        if (exhaustAliemus <= 0)
            return;

        int aliemus = exhaustAliemus * RECHARGE_ALIEMUS_PERCENT / 100;

        int aliemusAmplify = 100 + AbstractAwakener.baseAliemusAmplify;
        aliemus = MathUtils.ceil(aliemus * aliemusAmplify / 100F);

        flash();
        addToBot(new AliemusChangeAction(awaker, aliemus));

        logger.debug("exhaustAliemus:" + exhaustAliemus + ", gainedAliemus:" + aliemus);
    }
}
