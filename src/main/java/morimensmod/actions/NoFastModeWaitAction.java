package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class NoFastModeWaitAction extends AbstractGameAction {
    public NoFastModeWaitAction(float setDur) {
        this.setValues(null, null, 0);
        this.duration = setDur;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.tickDuration();
    }
}
