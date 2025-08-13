package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class Suffocation extends AbstractStatusCard {
    public final static String ID = makeID(Suffocation.class.getSimpleName());

    public static final int DEFAULT_POISON = 3;

    public Suffocation() {
        this(DEFAULT_POISON);
        sortIndex = 10;
    }

    public Suffocation(int poison) {
        super(ID, 1, CardRarity.COMMON, CardTarget.NONE);
        magicNumber = baseMagicNumber = poison; // 中毒
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        addToBot(new ApplyPowerAction(p(), null, new PoisonPower(p(), null, magicNumber)));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}
}
