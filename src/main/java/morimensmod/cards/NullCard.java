package morimensmod.cards;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.AutoAdd;

@AutoAdd.Ignore
public class NullCard extends AbstractEasyCard {
    public static final String ID = makeID(NullCard.class.getSimpleName());

    public NullCard(CardType type, CardTarget target) {
        super(ID, -2, type, CardRarity.SPECIAL, target, CardColor.COLORLESS);
    }

    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
    }

    @Override
    public void upp() {
    }
}
