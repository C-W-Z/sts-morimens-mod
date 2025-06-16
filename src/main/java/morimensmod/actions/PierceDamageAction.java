package morimensmod.actions;



// import static morimensmod.MorimensMod.logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class PierceDamageAction extends AbstractGameAction {

    public PierceDamageAction(AbstractCreature target, AbstractCreature source, int damage, AttackEffect effect) {
        this(target, source, damage, effect, Settings.ACTION_DUR_FAST);
    }

    public PierceDamageAction(AbstractCreature target, AbstractCreature source, int damage, AttackEffect effect,
            float duration) {
        this.source = source;
        this.target = target;
        this.amount = damage;
        this.attackEffect = effect;
        this.actionType = ActionType.DAMAGE;
        this.duration = duration;
    }

    @Override
    public void update() {
        if (target == null || target.currentHealth <= 0 || target.isDeadOrEscaped()) {
            isDone = true;
            return;
        }

        addToTop(new DamageAction(target, new DamageInfo(source, amount, DamageType.HP_LOSS), attackEffect));
        if (target.currentBlock > 0)
            addToTop(new LoseBlockAction(target, source, amount));

        isDone = true;
    }
}
