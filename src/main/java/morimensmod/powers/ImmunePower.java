package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.interfaces.OnBeforeDamaged;
import morimensmod.monsters.AbstractAwakenableBoss;

public class ImmunePower extends AbstractEasyPower implements OnBeforeDamaged {

    public final static String POWER_ID = makeID(ImmunePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ImmunePower(AbstractCreature owner) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, -1);
        priority = 1;
    }

    @Override
    public int onBeforeDamaged(DamageInfo info, int damageAmount) {
        // cannot immune to HP_LOSS (e.g. Pierce Damage)
        if (info.type == DamageType.HP_LOSS)
            return damageAmount;
        return 0;
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    // Called in MorimensMod file
    public static boolean onPreMonsterTurn(AbstractMonster monster) {
        if (monster instanceof AbstractAwakenableBoss)
            return false; // skip a turn if half killed by Poison or something at turn starts
        return true;
    }
}
