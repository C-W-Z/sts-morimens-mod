package morimensmod.actions;

import static morimensmod.util.Wiz.p;

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

    public QueensSwordAction(AbstractCreature target, int damage, DamageInfo.DamageType damageType, AttackEffect effect) {
        this(target, p(), damage, damageType, effect, Settings.ACTION_DUR_XFAST);
    }

    public QueensSwordAction(AbstractCreature target, AbstractCreature source, int damage,
            DamageInfo.DamageType damageType, AttackEffect effect, float duration) {
        this.source = source;
        this.target = target;
        this.amount = damage;
        this.damageType = damageType;
        this.attackEffect = effect;
        this.actionType = ActionType.DAMAGE;
        this.duration = duration;
    }

    @Override
    public void update() {
        if (shouldCancelAction()) {
            isDone = true;
            return;
        }

        // logger.info("target.isDeadOrEscaped=" + target.isDeadOrEscaped() + "HP=" +
        // target.currentHealth);

        addToTop(new ApplyPowerAction(source, source, new LoseStrengthPower(source, 1), 1));
        addToTop(new ApplyPowerAction(source, source, new StrengthPower(source, 1), 1));
        addToTop(new DamageAction(target, new DamageInfo(source, amount, damageType), attackEffect));

        isDone = true;
    }
}
