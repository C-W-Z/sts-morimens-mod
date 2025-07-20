package morimensmod.cards.buffs;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.isInCombat;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.actions.KeyflareChangeAction;
import morimensmod.patches.enums.CustomTags;

public class MarvelousCuisine extends AbstractBuffCard implements StartupCard {
    public final static String ID = makeID(MarvelousCuisine.class.getSimpleName());

    public MarvelousCuisine() {
        super(ID, Insight.ID, 0, CardRarity.UNCOMMON, CardTarget.NONE);
        tags.add(CustomTags.REUSE);
        magicNumber = baseMagicNumber = 1; // 能量
        secondMagic = baseSecondMagic = 0;
        thirdMagic = baseThirdMagic = 3;
        ExhaustiveVariable.setBaseValue(this, thirdMagic);
        selfRetain = true;
        returnToHand = true;
        if (isInCombat())
            atBattleStartPreDraw();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(magicNumber));
        // 奇妙正面效果
        if (secondMagic <= 0 || !AbstractDungeon.cardRandomRng.randomBoolean(secondMagic))
            return;
        int type = AbstractDungeon.cardRandomRng.random(1, 5);
        switch (type) {
            case 1:
                addToBot(new AliemusChangeAction(p, 10));
                break;
            case 2:
                addToBot(new AllEnemyApplyPowerAction(p, 1, (mo) -> new VulnerablePower(mo, 1, false)));
                break;
            case 3:
                addToBot(new AllEnemyApplyPowerAction(p, 1, (mo) -> new WeakPower(mo, 1, false)));
                break;
            case 4:
                addToBot(new DrawCardAction(1));
                break;
            case 5:
                addToBot(new KeyflareChangeAction(p, 200));
            default:
                break;
        }
    }

    @Override
    public void upp() {
        upgradeSecondMagic(50);
    }

    @Override
    public boolean atBattleStartPreDraw() {
        this.rawDescription = this.cardStrings.EXTENDED_DESCRIPTION[timesUpgraded];
        this.initializeDescription();
        return false;
    }
}
