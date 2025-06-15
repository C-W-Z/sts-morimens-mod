package morimensmod.powers;

import static morimensmod.MorimensMod.logger;
import static morimensmod.MorimensMod.makeID;

// import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterExalt;
import morimensmod.interfaces.OnBeforeExalt;

public class ManikinOfOblivionPower extends AbstractEasyPower implements OnBeforeExalt, OnAfterExalt {
    public final static String POWER_ID = makeID(ManikinOfOblivionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int aliemusBeforeExalt;

    public ManikinOfOblivionPower(AbstractCreature owner, int percent) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, percent);

        AbstractAwakener.baseAliemusAmplify += percent;
        AbstractAwakener.baseHealAmplify += percent;
        AbstractAwakener.basePoisonAmplify += percent;

        isTwoAmount = true;
        amount2 = 20;
        updateDescription();

        logger.info("ManikinOfOblivionPower, AbstractAwakener.baseAliemusAmplify:" + AbstractAwakener.baseAliemusAmplify);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        // A new Power() must be called before stack, so the baseAliemusAmplify increment will be done in the constructor
        // AbstractAwakener.baseAliemusAmplify += stackAmount;
        // logger.info("stackPower, AbstractAwakener.baseAliemusAmplify:" + AbstractAwakener.baseAliemusAmplify);
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);

        AbstractAwakener.baseAliemusAmplify -= reduceAmount;
        AbstractAwakener.baseHealAmplify -= reduceAmount;
        AbstractAwakener.basePoisonAmplify -= reduceAmount;

        logger.info("reducePower, AbstractAwakener.baseAliemusAmplify:" + AbstractAwakener.baseAliemusAmplify);
    }

    @Override
    public void onRemove() {
        AbstractAwakener.baseAliemusAmplify -= amount;
        AbstractAwakener.baseHealAmplify -= amount;
        AbstractAwakener.basePoisonAmplify -= amount;

        logger.info("onRemove, AbstractAwakener.baseAliemusAmplify:" + AbstractAwakener.baseAliemusAmplify);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }

    @Override
    public void onBeforeExalt(AbstractAwakener awaker) {
        aliemusBeforeExalt = AbstractAwakener.aliemus;

        logger.info("aliemusBeforeExalt:" + aliemusBeforeExalt);
    }

    @Override
    public void onAfterExalt(AbstractAwakener awaker) {
        int diffAliemus = aliemusBeforeExalt - AbstractAwakener.aliemus;
        if (diffAliemus <= 0)
            return;

        int gainedAliemus = diffAliemus * amount2 / 100;

        // int aliemusAmplify = 100 + AbstractAwakener.baseAliemusAmplify;
        // gainedAliemus = MathUtils.ceil(gainedAliemus * aliemusAmplify / 100F);

        addToBot(new AliemusChangeAction(awaker, gainedAliemus));

        logger.info("diffAliemus:" + diffAliemus + ", gainedAliemus:" + gainedAliemus);
    }
}
