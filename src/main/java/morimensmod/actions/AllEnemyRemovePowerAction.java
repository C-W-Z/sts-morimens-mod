package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class AllEnemyRemovePowerAction extends AbstractGameAction {
    private ArrayList<RemoveSpecificPowerAction> actions = new ArrayList<>();

    public AllEnemyRemovePowerAction(AbstractCreature source, String powerID) {
        this.source = source;
        this.actionType = ActionType.POWER;
        AbstractDungeon.getMonsters().monsters.stream().filter((m) -> {
            return !m.isDeadOrEscaped();
        }).forEach((q) -> {
            this.actions.add(new RemoveSpecificPowerAction(q, source, powerID));
        });
    }

    public void update() {
        this.isDone = true;
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            return;
        for (RemoveSpecificPowerAction action : this.actions) {
            if (!action.isDone) {
                action.update();
                this.isDone = false;
            }
        }
    }
}
