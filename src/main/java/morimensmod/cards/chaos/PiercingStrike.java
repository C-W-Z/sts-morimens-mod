package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToEnemy;
import static morimensmod.util.Wiz.p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import morimensmod.actions.PierceDamageAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;

public class PiercingStrike extends AbstractEasyCard {

    private static final Logger logger = LogManager.getLogger(PiercingStrike.class);

    public final static String ID = makeID(PiercingStrike.class.getSimpleName());

    public PiercingStrike() {
        super(ID, CardImgID.OgierAttack, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damageType = DamageType.NORMAL; // 必須是normal才能享受力量加成
        damage = baseDamage = 6;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 1; // 易傷
        secondMagic = baseSecondMagic = 3; // 3倍力量
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                addToTop(new PierceDamageAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_HEAVY));
            });
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    // 讓卡面顯示的傷害包含三倍力量加成
    @Override
    public void applyPowers() {
        int originalStrength = 0;
        AbstractPower str = p().getPower(StrengthPower.POWER_ID);
        if (str != null) {
            originalStrength = str.amount;
            // 暫時修改力量為 ×3
            str.amount *= secondMagic;
        }

        super.applyPowers();

        // 還原力量
        if (str != null)
            str.amount = originalStrength;

        initializeDescription(); // 更新描述中的 !D!

        logger.debug("PiercingStrike.applyPowers: damage=" + damage + ", str.amount=" + originalStrength);
    }

    // 針對單一目標計算最終傷害（例如精英或 Boss）
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int originalStrength = 0;
        AbstractPower str = p().getPower(StrengthPower.POWER_ID);
        if (str != null) {
            originalStrength = str.amount;
            str.amount *= secondMagic;
        }

        super.calculateCardDamage(mo);

        if (str != null)
            str.amount = originalStrength;

        initializeDescription();

        logger.debug("PiercingStrike.calculateCardDamage: damage=" + damage + ", str.amount=" + originalStrength);
    }
}
