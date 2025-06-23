package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.KnightsZealPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class KnightsZeal extends AbstractEasyCard {
    public final static String ID = makeID(KnightsZeal.class.getSimpleName());

    public KnightsZeal() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.ROUSE);
        aliemus = baseAliemus = 5;
        magicNumber = baseMagicNumber = 1; // 力量
        secondMagic = baseSecondMagic = 1; // 每次獲得多少力量
        thirdMagic = baseThirdMagic = 3; // 每幾張指令卡獲得力量 only for display
        selfRetain = true; // 保留
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));
        applyToSelf(new StrengthPower(p, magicNumber));
        applyToSelf(new KnightsZealPower(p, secondMagic));
    }

    @Override
    public void upp() {
        upgradeAliemus(20);
        upgradeMagicNumber(1);
        upgradeBaseCost(1);
    }
}
