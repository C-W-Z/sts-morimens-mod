package morimensmod.actions;


// import static morimensmod.MorimensMod.logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class QueensSwordAction extends AbstractGameAction {

    int strength;

    public QueensSwordAction(AbstractCreature target, DamageInfo damageInfo, int strength, AttackEffect effect) {
        this(target, damageInfo, strength, effect, Settings.ACTION_DUR_XFAST);
    }

    public QueensSwordAction(AbstractCreature target, DamageInfo damageInfo, int strength, AttackEffect effect, float duration) {
        this.source = damageInfo.owner;
        this.target = target;
        this.amount = damageInfo.base;
        this.damageType = damageInfo.type;
        this.attackEffect = effect;
        this.actionType = ActionType.DAMAGE;
        this.duration = duration;
        this.strength = strength;
    }

    @Override
    public void update() {
        if (shouldCancelAction()) {
            isDone = true;
            return;
        }

        // logger.info("target.isDeadOrEscaped=" + target.isDeadOrEscaped() + "HP=" +
        // target.currentHealth);

        if (strength > 0) {
            addToTop(new ApplyPowerAction(source, source, new LoseStrengthPower(source, strength), strength));
            addToTop(new ApplyPowerAction(source, source, new StrengthPower(source, strength), strength));
        }
        addToTop(new DamageAction(target, new DamageInfo(source, amount, damageType), attackEffect));

        isDone = true;
    }
}
