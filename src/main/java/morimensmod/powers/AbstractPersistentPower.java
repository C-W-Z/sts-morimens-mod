package morimensmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import basemod.Pair;
import morimensmod.characters.AbstractAwakener;

// WARNING: can only save ID and amount, other fields will not be saved
public abstract class AbstractPersistentPower extends AbstractEasyPower {

    public AbstractPersistentPower(String ID, String NAME, PowerType powerType, AbstractCreature owner,
            int amount) {
        super(ID, NAME, powerType, false, owner, amount);
    }

    public abstract AbstractPersistentPower newPower(AbstractCreature owner, int amount);

    @Override
    public void onInitialApplication() {
        AbstractAwakener.persistentPowers.add(new Pair<String, Integer>(ID, amount));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        for (int i = 0; i < AbstractAwakener.persistentPowers.size(); i++) {
            if (AbstractAwakener.persistentPowers.get(i).getKey() == ID)
                AbstractAwakener.persistentPowers.set(i, new Pair<>(ID, amount)); // 取代成新 pair
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        for (int i = 0; i < AbstractAwakener.persistentPowers.size(); i++) {
            if (AbstractAwakener.persistentPowers.get(i).getKey() == ID)
                AbstractAwakener.persistentPowers.set(i, new Pair<>(ID, amount)); // 取代成新 pair
        }
    }

    @Override
    public void onRemove() {
        AbstractAwakener.persistentPowers.removeIf(pair -> pair.getKey().equals(ID));
    }
}
