package morimensmod.powers.rouse;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;
import static morimensmod.util.Wiz.getPowerAmount;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.KeyflareChangeAction;
import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.AbstractEasyPower;
import morimensmod.powers.NegentropyPower;

public class EntropyUndonePower extends AbstractEasyPower {

    public final static String POWER_ID = makeID(EntropyUndonePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int KEYFLARE_PER_COMMAND = 50;
    public static final int NEGENTROPY_GAIN = 1;
    public static final int MAX_USE_PER_TURN = 3;
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
        if (!card.hasTag(CustomTags.LOOP))
            return;
        negentropyAmountBeforeUseCard = getPowerAmount(p(), NegentropyPower.POWER_ID);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        boolean flashed = false;
        if (amount2 > 0 && card.hasTag(CustomTags.COMMAND)) {
            flash();
            flashed = true;
            addToBot(new KeyflareChangeAction(p(), amount * KEYFLARE_PER_COMMAND));
            amount2--;
            updateDescription();
        }
        if (card.hasTag(CustomTags.LOOP) && negentropyAmountBeforeUseCard < NegentropyPower.INVOKE_AMOUNT) {
            // 打出有回環效果的牌且負熵層數>=3則必定觸發回環
            if (!flashed)
                flash();
            applyToSelf(new NegentropyPower(p(), NEGENTROPY_GAIN));
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount * KEYFLARE_PER_COMMAND, MAX_USE_PER_TURN, amount2, NEGENTROPY_GAIN);
    }
}
