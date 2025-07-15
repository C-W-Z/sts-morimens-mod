package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.LoseThornsPower;

import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.getCleanCopy;
import static morimensmod.util.Wiz.isInBossCombat;

public class NoTrespassing extends AbstractEasyCard {
    public final static String ID = makeID(NoTrespassing.class.getSimpleName());

    public NoTrespassing() {
        super(ID, 3, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        block = baseBlock = 15;
        magicNumber = baseMagicNumber = 5; // 反擊
        secondMagic = baseSecondMagic = 3; // 3 倍
        prepare = 1;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new ThornsPower(p, magicNumber));
        if (isInBossCombat())
            applyToSelf(new LoseThornsPower(p, magicNumber * secondMagic));
        else
            applyToSelf(new LoseThornsPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(5);
        upgradeMagicNumber(5);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        // 計算反擊加成
        int counterAmplify = 100 + AbstractAwakener.baseCounterAmplify;
        AbstractEasyCard tmp = (AbstractEasyCard) getCleanCopy(this);
        magicNumber = baseMagicNumber = MathUtils.ceil(tmp.baseMagicNumber * counterAmplify / 100F);

        if (magicNumber != tmp.baseMagicNumber)
            isMagicNumberModified = true;
    }
}
