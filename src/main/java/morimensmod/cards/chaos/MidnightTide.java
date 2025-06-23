package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToEnemyTop;
import static morimensmod.util.Wiz.getEnemies;
import static morimensmod.util.Wiz.p;

import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class MidnightTide extends AbstractEasyCard {

    private static final Logger logger = LogManager.getLogger(MidnightTide.class);

    public final static String ID = makeID(MidnightTide.class.getSimpleName());

    public MidnightTide() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damageType = DamageType.NORMAL; // 必須是normal才能享受力量加成
        damage = baseDamage = 2;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 50;
        secondMagic = baseSecondMagic = 2; // 2倍力量
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster targetMonster = getEnemies().stream().max(Comparator.comparingInt((_m) -> _m.currentHealth)).orElse(null);
        if (targetMonster == null)
            return;
        for (int i = 0; i < attackCount; i++)
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    calculateCardDamage(targetMonster);
                    applyToEnemyTop(targetMonster, new PoisonPower(targetMonster, p, MathUtils.ceil(damage * magicNumber / 100F)));
                    dmgTop(targetMonster, AttackEffect.NONE);
                    isDone = true;
                }
            });
    }

    @Override
    public void upp() {
        upgradeMagicNumber(50);
    }

    // 讓卡面顯示的傷害包含2倍力量加成
    @Override
    public void applyPowers() {
        int originalStrength = 0;
        AbstractPower str = p().getPower(StrengthPower.POWER_ID);
        if (str != null) {
            originalStrength = str.amount;
            // 暫時修改力量為 ×2
            str.amount *= secondMagic;
        }

        super.applyPowers();

        // 還原力量
        if (str != null)
            str.amount = originalStrength;

        initializeDescription(); // 更新描述中的 !D!

        logger.debug("MidnightTide.applyPowers: damage=" + damage + ", str.amount=" + originalStrength);
    }

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

        logger.debug("MidnightTide.calculateCardDamage: damage=" + damage + ", str.amount=" + originalStrength);
    }
}
