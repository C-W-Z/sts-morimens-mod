package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.CustomTags;

public class KnellOfDeath extends AbstractEasyCard {
    public final static String ID = makeID(KnellOfDeath.class.getSimpleName());

    public KnellOfDeath() {
        super(ID, 3, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        magicNumber = baseMagicNumber = 4; // 中毒
        secondMagic = baseSecondMagic = 1; // 虛弱 易傷
        block = baseBlock = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mo) -> new PoisonPower(mo, p, magicNumber)));
        addToBot(new AllEnemyApplyPowerAction(p, secondMagic, (mo) -> new WeakPower(mo, secondMagic, false)));
        addToBot(new AllEnemyApplyPowerAction(p, secondMagic, (mo) -> new VulnerablePower(mo, secondMagic, false)));
        if (upgraded)
            addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
        upgradeBlock(4);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        // 計算中毒加成
        int poisonAmplify = 100 + AbstractAwakener.basePoisonAmplify;
        AbstractEasyCard tmp = (AbstractEasyCard) CardLibrary.getCopy(cardID, timesUpgraded, misc);
        baseMagicNumber = MathUtils.ceil(tmp.baseMagicNumber * poisonAmplify / 100F);

        if (poisonAmplify != 100) {
            isMagicNumberModified = true;
            magicNumber = baseMagicNumber;
        }
    }
}
