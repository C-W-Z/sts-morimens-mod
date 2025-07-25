package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class Suffocation extends AbstractStatusCard {
    public final static String ID = makeID(Suffocation.class.getSimpleName());

    public Suffocation() {
        super(ID, 1, CardRarity.COMMON, CardTarget.NONE);
        magicNumber = baseMagicNumber = 3; // 中毒
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        addToBot(new ApplyPowerAction(p(), null, new PoisonPower(p(), null, magicNumber)));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}
}
