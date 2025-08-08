package morimensmod.cardmodifiers;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.AbstractCreature;

import basemod.abstracts.AbstractCardModifier;

public class AddBlockAtTurnEndModifier extends AbstractCardModifier {

    public static final String ID = makeID(AddBlockAtTurnEndModifier.class.getSimpleName());

    int amount;
    int increasePerTurn;

    public AddBlockAtTurnEndModifier(int increasePerTurn) {
        this(0, increasePerTurn);
        priority = -1;
    }

    public AddBlockAtTurnEndModifier(int initialAmount, int increasePerTurn) {
        this.amount = initialAmount;
        this.increasePerTurn = increasePerTurn;
    }

    public AbstractCardModifier makeCopy() {
        return new AddBlockAtTurnEndModifier(amount, increasePerTurn);
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        amount = 0;
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        if (group.type == CardGroupType.HAND)
            amount += increasePerTurn;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        return block + amount;
    }
}
