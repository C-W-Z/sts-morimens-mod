package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Stagger extends AbstractStatusCard {
    public final static String ID = makeID(Stagger.class.getSimpleName());

    public Stagger() {
        super(ID, 2, CardRarity.COMMON, CardTarget.NONE);
        sortIndex = 3;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}
