package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.powers.KnightsZealPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class KnightsZeal extends AbstractRouseCard {
    public final static String ID = makeID(KnightsZeal.class.getSimpleName());

    public KnightsZeal() {
        super(ID, 2, CardRarity.RARE, CHAOS_COLOR);
        magicNumber = baseMagicNumber = 1; // 力量
        secondMagic = baseSecondMagic = 1; // 每次獲得多少力量
        thirdMagic = baseThirdMagic = 4; // 每幾張指令卡獲得力量 only for display
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new StrengthPower(p, magicNumber));
        applyToSelf(new KnightsZealPower(p, secondMagic));
    }
}
