package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class PrecisionSlash extends AbstractEasyCard {

    public final static String ID = makeID(PrecisionSlash.class.getSimpleName());

    public PrecisionSlash() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damageType = DamageType.NORMAL; // 必須是normal才能享受力量加成
        damage = baseDamage = 10;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 2; // 50%格擋加成
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                dmgTop(m, AttackEffect.NONE);
            });
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.hasTag(CustomTags.DEFEND))
            setCostForTurn(costForTurn - 1);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(-1);
    }

    // 讓卡面顯示的傷害包含格擋加成
    @Override
    public void applyPowers() {
        int originalStrength = 0;
        AbstractPower str = p().getPower(StrengthPower.POWER_ID);
        if (str == null) {
            str = new StrengthPower(p(), 0);
            p().powers.add(str);
        }
        else
            originalStrength = str.amount;
        // 暫時修改力量 加上格擋
        str.amount += MathUtils.ceil((float) p().currentBlock / magicNumber);

        super.applyPowers();

        // 還原力量
        if (originalStrength == 0)
            p().powers.remove(p().getPower(StrengthPower.POWER_ID));
        else
            str.amount = originalStrength;

        initializeDescription(); // 更新描述中的 !D!
    }

    // 針對單一目標計算最終傷害（例如精英或 Boss）
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int originalStrength = 0;
        AbstractPower str = p().getPower(StrengthPower.POWER_ID);
        if (str == null) {
            str = new StrengthPower(p(), 0);
            p().powers.add(str);
        }
        else
            originalStrength = str.amount;
        // 暫時修改力量 加上格擋
        str.amount += MathUtils.ceil((float) p().currentBlock / magicNumber);

        super.calculateCardDamage(mo);

        // 還原力量
        if (originalStrength == 0)
            p().powers.remove(p().getPower(StrengthPower.POWER_ID));
        else
            str.amount = originalStrength;

        initializeDescription(); // 更新描述中的 !D!
    }
}
