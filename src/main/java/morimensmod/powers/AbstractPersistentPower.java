package morimensmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

// WARNING: can only save ID and amount, other fields will not be saved
public abstract class AbstractPersistentPower extends AbstractEasyPower {

    public AbstractPersistentPower(String ID, String NAME, PowerType powerType, AbstractCreature owner,
            int amount) {
        super(ID, NAME, powerType, false, owner, amount);
    }

    public abstract AbstractPersistentPower newPower(AbstractCreature owner, int amount);
}
