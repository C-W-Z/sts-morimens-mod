package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class NewWaitAction extends AbstractGameAction {
    public NewWaitAction(float setDur) {
        this.setValues(null, null, 0);
        this.duration = setDur * (Settings.FAST_MODE ? 0.5F : 1F);
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.tickDuration();
    }
}
