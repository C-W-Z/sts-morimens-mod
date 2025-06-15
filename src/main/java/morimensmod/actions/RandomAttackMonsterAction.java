package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RandomAttackMonsterAction extends AbstractGameAction {

    private DamageInfo info;

    public RandomAttackMonsterAction(DamageInfo info, AttackEffect effect) {
        this.info = info;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    @Override
    public void update() {
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (m != null)
            addToTop(new DamageAction(m, info));
        isDone = true;
    }
}
