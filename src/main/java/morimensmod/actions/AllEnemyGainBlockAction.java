package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class AllEnemyGainBlockAction extends AbstractGameAction {
    private ArrayList<GainBlockAction> actions = new ArrayList<>();

    public AllEnemyGainBlockAction(AbstractCreature source, int amount) {
        this.source = source;
        this.actionType = ActionType.BLOCK;
        AbstractDungeon.getMonsters().monsters.stream().filter((m) -> {
            return !m.isDeadOrEscaped();
        }).forEach((q) -> {
            this.actions.add(new GainBlockAction(q, source, amount));
        });
    }

    public void update() {
        this.isDone = true;
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            return;
        for (GainBlockAction action : this.actions) {
            if (!action.isDone) {
                action.update();
                this.isDone = false;
            }
        }
    }
}
