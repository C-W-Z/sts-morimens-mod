package morimensmod.cards.chaos.unique;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.getPowerAmount;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.NegentropyPower;

public class Defend_RamonaTimeworm extends AbstractEasyCard {
    public final static String ID = makeID(Defend_RamonaTimeworm.class.getSimpleName());

    public Defend_RamonaTimeworm() {
        super(ID, CardImgID.RamonaTimewormSkill, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CustomTags.DEFEND);
        tags.add(CardTags.STARTER_DEFEND);
        tags.add(CustomTags.LOOP);
        block = baseBlock = 5;
        aliemus = baseAliemus = 5;
        magicNumber = baseMagicNumber = 3;
        draw = baseDraw = 1;
        secondMagic = baseSecondMagic = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster _m) {
        addToBot(new AliemusChangeAction(p, aliemus));
        blck();

        if (getPowerAmount(p, NegentropyPower.POWER_ID) >= NegentropyPower.INVOKE_AMOUNT) {
            addToBot(new ReducePowerAction(p, p, NegentropyPower.POWER_ID, NegentropyPower.INVOKE_AMOUNT));
            addToBot(new AllEnemyApplyPowerAction(p, -magicNumber, m -> new StrengthPower(m, -magicNumber)));
            addToBot(new AllEnemyApplyPowerAction(p, magicNumber, m -> new GainStrengthPower(m, magicNumber)));
            addToBot(new DrawCardAction(p, draw));
            addToBot(new AliemusChangeAction(p, secondMagic));
        }
    }

    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeAliemus(5);
        upgradeMagicNumber(2);
        upgradeSecondMagic(5);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        int aliemusAmplify = 100 + baseAliemusAmplify + AbstractAwakener.baseAliemusAmplify;
        if (aliemusAmplify != 100) {
            isSecondMagicModified = true;
            secondMagic = MathUtils.ceil(baseSecondMagic * aliemusAmplify / 100F);
        }
    }
}
