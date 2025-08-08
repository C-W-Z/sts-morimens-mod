package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.cards.EasyModalChoiceCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.NegentropyPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.*;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PredeterminedStrike extends AbstractEasyCard {

    private static final Logger logger = LogManager.getLogger(PredeterminedStrike.class);

    public final static String ID = makeID(PredeterminedStrike.class.getSimpleName());

    static final int DMG_SCALE = 2;

    public PredeterminedStrike() {
        super(ID, CardImgID.RamonaTimewormAttack, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CustomTags.LOOP);
        damageType = DamageType.NORMAL; // 必須是normal才能享受力量加成
        damage = baseDamage = 10;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 3; // 3倍力量
        secondMagic = baseSecondMagic = 3; // 3能量
        thirdMagic = baseThirdMagic = 3; // 3倍銀鑰能量
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (getPowerAmount(p, NegentropyPower.POWER_ID) >= NegentropyPower.INVOKE_AMOUNT) {
            addToBot(new ReducePowerAction(p, p, NegentropyPower.POWER_ID, NegentropyPower.INVOKE_AMOUNT));

            ArrayList<AbstractCard> choiceCardList = new ArrayList<>();

            EasyModalChoiceCard rebirthTribunal = new EasyModalChoiceCard(
                    ID,
                    CardImgID.RamonaTimewormSkill,
                    cardStrings.EXTENDED_DESCRIPTION[0],
                    cardStrings.EXTENDED_DESCRIPTION[1],
                    () -> useRebirthTribunal(p),
                    CHAOS_COLOR,
                    CustomTags.COMMAND);

            rebirthTribunal.secondMagic = rebirthTribunal.baseSecondMagic = secondMagic;

            EasyModalChoiceCard odeToTomorrow = new EasyModalChoiceCard(
                    ID,
                    CardImgID.RamonaTimewormSkill,
                    cardStrings.EXTENDED_DESCRIPTION[2],
                    cardStrings.EXTENDED_DESCRIPTION[3],
                    () -> useOdeToTomorrow(p),
                    CHAOS_COLOR,
                    CustomTags.COMMAND);

            odeToTomorrow.thirdMagic = odeToTomorrow.baseThirdMagic = thirdMagic;

            choiceCardList.add(rebirthTribunal);
            choiceCardList.add(odeToTomorrow);
            atb(new EasyModalChoiceAction(choiceCardList));
        }

        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AttackEffect.SLASH_VERTICAL));
            });
    }

    private void useRebirthTribunal(AbstractPlayer p) {
        addToBot(new GainEnergyAction(secondMagic));
    }

    private void useOdeToTomorrow(AbstractPlayer p) {
        misc = DMG_SCALE;
        AbstractAwakener.addLastUsedEnergy(AbstractAwakener.getLastUsedEnergy() * (thirdMagic - 1));
        exhaustOnUseOnce = true;
    }

    @Override
    public void upp() {
        upgradeDamage(5);
    }

    // 讓卡面顯示的傷害包含三倍力量加成
    @Override
    public void applyPowers() {
        magicNumber = baseMagicNumber + AbstractAwakener.getPossedThisBattle();
        isMagicNumberModified = (magicNumber != baseMagicNumber);

        int originalStrength = 0;
        AbstractPower str = p().getPower(StrengthPower.POWER_ID);
        if (str != null) {
            originalStrength = str.amount;
            // 暫時修改力量為 ×3
            str.amount *= magicNumber;
        }

        super.applyPowers();

        // 還原力量
        if (str != null)
            str.amount = originalStrength;

        // 雙倍傷害
        if (misc > 0)
            damage *= misc;

        initializeDescription(); // 更新描述中的 !D!

        logger.trace("PredeterminedStrike.applyPowers: damage=" + damage + ", str.amount=" + originalStrength);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        magicNumber = baseMagicNumber + AbstractAwakener.getPossedThisBattle();
        isMagicNumberModified = (magicNumber != baseMagicNumber);

        int originalStrength = 0;
        AbstractPower str = p().getPower(StrengthPower.POWER_ID);
        if (str != null) {
            originalStrength = str.amount;
            str.amount *= magicNumber;
        }

        super.calculateCardDamage(mo);

        if (str != null)
            str.amount = originalStrength;

        // 雙倍傷害
        if (misc > 0)
            damage *= misc;

        initializeDescription();

        logger.trace("PredeterminedStrike.calculateCardDamage: damage=" + damage + ", str.amount=" + originalStrength);
    }
}
