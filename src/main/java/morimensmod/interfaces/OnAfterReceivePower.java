package morimensmod.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public interface OnAfterReceivePower {
    void onAfterReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source);
}
