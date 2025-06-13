package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.powers.FirstDoctrinePower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class FirstDoctrine extends AbstractEasyCard {
    public final static String ID = makeID(FirstDoctrine.class.getSimpleName());

    public FirstDoctrine() {
        super(ID, "Powers/Ramona", 3, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, CHAOS_COLOR);
        baseMagicNumber = magicNumber = 1; // 每次獲得的能量
        baseSecondMagic = secondMagic = 3; // 每回合最大觸發次數
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FirstDoctrinePower(p, secondMagic));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2); // cost 3 -> 2
    }
}
