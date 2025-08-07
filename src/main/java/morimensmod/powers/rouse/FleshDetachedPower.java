package morimensmod.powers.rouse;

import static morimensmod.MorimensMod.makeID;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnPowerModified;
import morimensmod.powers.AbstractEasyPower;

public class FleshDetachedPower extends AbstractEasyPower implements OnPowerModified {

    public final static String POWER_ID = makeID(FleshDetachedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int BLOCK_PER_HEAL = 5;
    private int block;

    public FleshDetachedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public int onHeal(int healAmount) {
        flash();
        addToTop(new GainBlockAction(owner, owner, block));
        return healAmount;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], block);
    }

    @Override
    public void onPowerModified() {
        int blockAmplify = 100 + AbstractAwakener.baseBlockAmplify;
        block = MathUtils.ceil(amount * BLOCK_PER_HEAL * blockAmplify / 100F);

        for (AbstractPower p : owner.powers)
            block = MathUtils.floor(p.modifyBlock(block));
        for (AbstractPower p : owner.powers)
            block = MathUtils.floor(p.modifyBlockLast(block));

        updateDescription();
    }
}
