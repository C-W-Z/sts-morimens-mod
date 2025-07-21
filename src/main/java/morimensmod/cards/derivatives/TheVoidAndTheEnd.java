package morimensmod.cards.derivatives;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.AutoAdd;

@AutoAdd.Ignore
public class TheVoidAndTheEnd extends AbstractDerivative {
    public final static String ID = makeID(TheVoidAndTheEnd.class.getSimpleName());

    public TheVoidAndTheEnd() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.ALL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}
