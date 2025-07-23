package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static morimensmod.util.Wiz.getPowerAmount;

import java.util.ArrayList;

public class AllEnemyScalePoisonAction extends AbstractGameAction {
    private ArrayList<ApplyPowerAction> actions = new ArrayList<>();

    public AllEnemyScalePoisonAction(AbstractCreature source, int scale) {
        this.source = source;
        this.amount = scale;
        this.actionType = ActionType.POWER;
        AbstractDungeon.getMonsters().monsters.stream().filter((m) -> {
            return !m.isDeadOrEscaped();
        }).forEach((q) -> {
            int poisonAmount = getPowerAmount(q, PoisonPower.POWER_ID) * (scale - 1);
            if (poisonAmount > 0)
                this.actions.add(new ApplyPowerAction(q, source, new PoisonPower(q, source, poisonAmount), poisonAmount));
        });
    }

    public void update() {
        this.isDone = true;
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            return;
        for (ApplyPowerAction action : this.actions) {
            if (!action.isDone) {
                action.update();
                this.isDone = false;
            }
        }
    }
}
