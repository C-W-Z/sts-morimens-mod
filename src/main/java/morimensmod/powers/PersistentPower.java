package morimensmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

// WARNING: can only save ID and amount, other fields will not be saved
public abstract class PersistentPower extends AbstractEasyPower {

    public PersistentPower(String ID, String NAME, PowerType powerType, AbstractCreature owner,
            int amount) {
        super(ID, NAME, powerType, false, owner, amount);
    }

    public abstract PersistentPower newPower(AbstractCreature owner, int amount);
}
