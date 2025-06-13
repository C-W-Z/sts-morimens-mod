package morimensmod.cards.Ramona;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.powers.RewindingTimePower;

public class RewindingTime extends AbstractEasyCard {
    public final static String ID = makeID(RewindingTime.class.getSimpleName());

    public RewindingTime() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1; // 每回合最大觸發次數
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RewindingTimePower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2); // cost 3 -> 2
    }
}
