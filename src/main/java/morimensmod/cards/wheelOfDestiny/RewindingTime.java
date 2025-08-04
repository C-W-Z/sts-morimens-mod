package morimensmod.cards.wheelOfDestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.characters.AbstractAwakener;
import morimensmod.powers.wheelOfDestiny.RewindingTimePower;

public class RewindingTime extends AbstractWheelOfDestiny implements StartupCard {
    public final static String ID = makeID(RewindingTime.class.getSimpleName());

    public RewindingTime() {
        super(ID, 3, CardRarity.RARE);
        magicNumber = baseMagicNumber = 14; // 銀鑰充能
        secondMagic = baseSecondMagic = 1; // 每回合最大觸發次數
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RewindingTimePower(p, secondMagic));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2); // cost 3 -> 2
    }

    @Override
    public boolean atBattleStartPreDraw() {
        if (upgraded && p() instanceof AbstractAwakener)
            actB(() -> ((AbstractAwakener) p()).keyflareRegen += magicNumber);
        return upgraded;
    }
}
