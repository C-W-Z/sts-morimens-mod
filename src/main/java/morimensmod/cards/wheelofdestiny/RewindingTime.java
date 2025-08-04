package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.PassiveCard;
import morimensmod.powers.wheelofdestiny.RewindingTimePower;

public class RewindingTime extends AbstractWheelOfDestiny implements PassiveCard {
    public final static String ID = makeID(RewindingTime.class.getSimpleName());

    public RewindingTime() {
        super(ID, -2, CardRarity.RARE);
        magicNumber = baseMagicNumber = 0; // 銀鑰充能
        secondMagic = baseSecondMagic = 1; // 每回合最大觸發次數
    }

    @Override
    public void upp() {
        upgradeMagicNumber(14);
    }

    @Override
    public boolean onInitDeck() {
        applyToSelf(new RewindingTimePower(p(), secondMagic));
        if (upgraded && p() instanceof AbstractAwakener)
            actB(() -> ((AbstractAwakener) p()).keyflareRegen += magicNumber);
        return true;
    }
}
