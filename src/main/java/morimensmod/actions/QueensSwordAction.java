package morimensmod.actions;

import static morimensmod.util.Wiz.p;

// import static morimensmod.MorimensMod.logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class QueensSwordAction extends AbstractGameAction {

    private AbstractMonster target;
    private int damage;
    private DamageInfo.DamageType damageType;
    private AttackEffect effect;

    public QueensSwordAction(AbstractMonster target, int damage, DamageInfo.DamageType damageType, AttackEffect effect) {
        this.target = target;
        this.damage = damage;
        this.damageType = damageType;
        this.attackEffect = effect;

        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        if (target == null || target.currentHealth <= 0 || target.isDeadOrEscaped()) {
            isDone = true;
            return;
        }

        // logger.info("target.isDeadOrEscaped=" + target.isDeadOrEscaped() + "HP=" +
        // target.currentHealth);

        addToTop(new ApplyPowerAction(p(), p(), new LoseStrengthPower(p(), 1), 1));
        addToTop(new ApplyPowerAction(p(), p(), new StrengthPower(p(), 1), 1));
        addToTop(new DamageAction(target, new DamageInfo(p(), damage, damageType), effect));

        isDone = true;
    }
}
