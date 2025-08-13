package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.getPowerAmount;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.enums.CustomTags;

public class KnellOfDeath extends AbstractEasyCard {
    public final static String ID = makeID(KnellOfDeath.class.getSimpleName());

    public KnellOfDeath() {
        super(ID, 3, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR, CardImgID.Nymphaea.ID);
        tags.add(CustomTags.COMMAND);
        magicNumber = baseMagicNumber = 8; // 中毒
        secondMagic = baseSecondMagic = 2; // 2倍自身中毒加成
        thirdMagic = baseThirdMagic = 1; // 虛弱 易傷
        block = baseBlock = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mo) -> new PoisonPower(mo, p, magicNumber)));
        addToBot(new AllEnemyApplyPowerAction(p, thirdMagic, (mo) -> new WeakPower(mo, thirdMagic, false)));
        addToBot(new AllEnemyApplyPowerAction(p, thirdMagic, (mo) -> new VulnerablePower(mo, thirdMagic, false)));
        if (upgraded)
            addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(4);
        upgradeSecondMagic(1);
        upgradeBlock(5);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        magicNumber = baseMagicNumber;
        magicNumber += secondMagic * getPowerAmount(p(), PoisonPower.POWER_ID);

        // 計算中毒加成
        int poisonAmplify = 100 + AbstractAwakener.basePoisonAmplify;
        magicNumber = MathUtils.ceil(magicNumber * poisonAmplify / 100F);

        if (magicNumber != baseMagicNumber)
            isMagicNumberModified = true;
    }
}
