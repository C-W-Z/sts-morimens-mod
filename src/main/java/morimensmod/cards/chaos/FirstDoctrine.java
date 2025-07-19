package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.powers.rouse.FirstDoctrinePower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class FirstDoctrine extends AbstractRouseCard {
    public final static String ID = makeID(FirstDoctrine.class.getSimpleName());

    public FirstDoctrine() {
        super(ID, 3, CardRarity.UNCOMMON, CHAOS_COLOR);
        magicNumber = baseMagicNumber = 1; // 每次獲得的能量
        secondMagic = baseSecondMagic = 3; // 每回合最大觸發次數 only for display
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new FirstDoctrinePower(p, magicNumber));
    }
}
