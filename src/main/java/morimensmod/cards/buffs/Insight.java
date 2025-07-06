package morimensmod.cards.buffs;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Insight extends AbstractBuffCard {
    public final static String ID = makeID(Insight.class.getSimpleName());

    public Insight() {
        super(ID, 0, CardRarity.UNCOMMON, CardTarget.NONE);
        magicNumber = baseMagicNumber = 1; // 獲得能量
        secondMagic = baseSecondMagic = 1; // 抽牌數
        exhaust = true;
        selfRetain = true;
        upgradedName = cardStrings.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new DrawCardAction(p, secondMagic));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }
}
