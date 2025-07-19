package morimensmod.powers.rouse;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.isCommandCard;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnPowerModified;
import morimensmod.powers.AbstractEasyPower;

public class DanceOfTheGibbousMoonPower extends AbstractEasyPower implements OnPowerModified {

    public final static String POWER_ID = makeID(DanceOfTheGibbousMoonPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int PER_N_CARD = 5;
    public static final int HEAL_PER_AMOUNT = 1;
    public static final int ALIEMUS_PER_AMOUNT = 5;
    public static final int POISON_PER_AMOUNT = 5;
    private int heal;
    private int aliemus;
    private int poison;

    public DanceOfTheGibbousMoonPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        isTwoAmount = true;
        amount2 = PER_N_CARD;
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        // TODO: 打出奇妙料理施加中毒
        if (!isCommandCard(card))
            return;
        amount2--;
        if (amount2 > 0) {
            updateDescription();
            return;
        }
        flash();
        addToBot(new HealAction(owner, owner, heal));
        if (owner instanceof AbstractAwakener)
            addToBot(new AliemusChangeAction((AbstractPlayer) owner, aliemus));
        amount2 = PER_N_CARD;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], PER_N_CARD, heal, aliemus, poison, amount2);
    }

    @Override
    public void onPowerModified() {
        int healAmplify = 100 + AbstractAwakener.baseHealAmplify;
        heal = MathUtils.ceil(amount * HEAL_PER_AMOUNT * healAmplify / 100F);
        if (p() instanceof AbstractAwakener) {
            int aliemusAmplify = 100 + AbstractAwakener.baseAliemusAmplify;
            aliemus = MathUtils.ceil(((AbstractAwakener) p()).aliemusRegen * aliemusAmplify / 100F);
        }
        int poisonAmplify = 100 + AbstractAwakener.basePoisonAmplify;
        poison = MathUtils.ceil(amount * POISON_PER_AMOUNT * poisonAmplify / 100F);
        updateDescription();
    }
}
