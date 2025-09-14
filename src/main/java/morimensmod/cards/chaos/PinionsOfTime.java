package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
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
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CHAOS_COLOR, CardImgID.Tawil.ID);
        tags.add(CustomTags.COMMAND);

        magicNumber = baseMagicNumber = 1; // 力量
        secondMagic = baseSecondMagic = 0; // 靈感的數量
        selfRetain = true; // 保留

        MultiCardPreview.add(this, new TwinWings());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (magicNumber > 0)
            applyToSelf(new StrengthPower(p, magicNumber)); // 獲得力量
        if (secondMagic > 0)
            makeInHand(MultiCardPreview.multiCardPreview.get(this).get(1), secondMagic); // 將1張靈感放到手中
        shuffleIn(MultiCardPreview.multiCardPreview.get(this).get(0)); // 把「雙翼初張」洗入抽牌堆中
    }

    @Override
    public void upp() {
        upgradeSecondMagic(1);
        MultiCardPreview.multiCardPreview.get(this).forEach(c -> c.upgrade());
        MultiCardPreview.add(this, new Insight());
    }
}
