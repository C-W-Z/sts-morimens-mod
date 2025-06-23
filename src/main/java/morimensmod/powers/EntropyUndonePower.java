package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;
import static morimensmod.util.Wiz.powerAmount;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.KeyflareChangeAction;
import morimensmod.patches.CustomTags;

public class EntropyUndonePower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(EntropyUndonePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int NEGENTROPY_GAIN = 1;
    private static final int MAX_USE_PER_TURN = 3;
    private int negentropyAmountBeforeUseCard = 0;

    public EntropyUndonePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);

        isTwoAmount = true;
        amount2 = MAX_USE_PER_TURN; // remainUsesThisTurn

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        amount2 = MAX_USE_PER_TURN;
        updateDescription();
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (amount2 <= 0)
            return;
        if (!card.hasTag(CustomTags.LOOP))
            return;
        negentropyAmountBeforeUseCard = powerAmount(p(), NegentropyPower.POWER_ID);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (amount2 <= 0)
            return;
        if (!card.hasTag(CustomTags.LOOP))
            return;
        // 打出有回環效果的牌且負熵層數>=3則必定觸發回環
        if (negentropyAmountBeforeUseCard >= NegentropyPower.INVOKE_AMOUNT)
            return;
        flash();
        applyToSelf(new NegentropyPower(p(), NEGENTROPY_GAIN));
        addToBot(new KeyflareChangeAction(p(), amount));
        amount2--;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + 1 + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + MAX_USE_PER_TURN
                + DESCRIPTIONS[3] + amount2 + DESCRIPTIONS[4];
    }
}
