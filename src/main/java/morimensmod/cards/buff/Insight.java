package morimensmod.cards.buff;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.BUFF_COLOR;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class Insight extends AbstractEasyCard {
    public final static String ID = makeID(Insight.class.getSimpleName());

    public Insight() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE, BUFF_COLOR);
        tags.add(CustomTags.BUFF);
        magicNumber = baseMagicNumber = 1; // 獲得能量
        secondMagic = baseSecondMagic = 1; // 抽牌數
        exhaust = true;
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
