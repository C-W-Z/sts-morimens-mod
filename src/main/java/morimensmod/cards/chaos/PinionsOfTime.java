package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.buffs.Insight;
import morimensmod.patches.enums.CustomTags;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.makeInHand;
import static morimensmod.util.Wiz.shuffleIn;

public class PinionsOfTime extends AbstractEasyCard {
    public final static String ID = makeID(PinionsOfTime.class.getSimpleName());

    public PinionsOfTime() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);

        magicNumber = baseMagicNumber = 1; // 力量
        secondMagic = baseSecondMagic = 1; // 靈感的數量
        cardsToPreview = new TwinWings();  // 可以預先查看「雙翼初張」
        selfRetain = true; // 保留
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));  // 獲得力量
        makeInHand(new Insight(), secondMagic); // 將1張靈感放到手中
        shuffleIn(cardsToPreview, 1); // 把「雙翼初張」洗入抽牌堆中
    }
    @Override
    public void upp() {
        upgradeMagicNumber(5); // 增加力量
    }
}
