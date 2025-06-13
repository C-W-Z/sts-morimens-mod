package morimensmod.cards.Ramona;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.Ramona;

public class Inspiration extends AbstractEasyCard {
    public final static String ID = makeID(Inspiration.class.getSimpleName());

    public Inspiration() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY, Ramona.Enums.RAMONA_COLOR);
        this.magicNumber = this.baseMagicNumber = 1; // 獲得能量
        this.secondMagic = this.baseSecondMagic = 1; // 抽牌數
        this.exhaust = true;
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
