package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.isDefendOrAsDefend;
import static morimensmod.util.Wiz.isStrikeOrAsStrike;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.CustomTags;

public class PrecisionSlash extends AbstractEasyCard {

    public final static String ID = makeID(PrecisionSlash.class.getSimpleName());

    public PrecisionSlash() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.STRIKE);
        damageType = DamageType.NORMAL; // 必須是normal才能享受力量加成
        damage = baseDamage = 10;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 2; // 50%格擋加成
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                dmgTop(m, AttackEffect.SLASH_HEAVY);
            });
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (isDefendOrAsDefend(c))
            setCostForTurn(costForTurn - 1);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(-1);
    }

    // 讓卡面顯示的傷害包含格擋加成
    @Override
    public void applyPowers() {
        isDamageModified = false;

        int damageAmplify = 100 + baseDamageAmplify + AbstractAwakener.baseDamageAmplify;
        if (isStrikeOrAsStrike(this))
            damageAmplify += baseStrikeDamageAmplify;
        applyedBaseDamageAmplifies(damageAmplify);
        if (damageAmplify != 100)
            isDamageModified = true;

        AbstractPlayer player = AbstractDungeon.player;
        float tmp = baseDamage;
        for (AbstractRelic r : player.relics) {
            tmp = r.atDamageModify(tmp, this);
            if (baseDamage != (int) tmp)
                isDamageModified = true;
        }
        for (AbstractPower p : player.powers)
            tmp = p.atDamageGive(tmp, damageTypeForTurn, this);
        tmp = player.stance.atDamageGive(tmp, damageTypeForTurn, this);

        // 享受格擋加成
        tmp += MathUtils.ceil((float) p().currentBlock / magicNumber);

        if (baseDamage != (int) tmp)
            isDamageModified = true;
        for (AbstractPower p : player.powers)
            tmp = p.atDamageFinalGive(tmp, damageTypeForTurn, this);
        if (tmp < 0.0F)
            tmp = 0.0F;
        if (baseDamage != MathUtils.floor(tmp))
            isDamageModified = true;
        damage = MathUtils.floor(tmp);
    }

    // 針對單一目標計算最終傷害（例如精英或 Boss）
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        isDamageModified = false;

        int damageAmplify = 100 + baseDamageAmplify + AbstractAwakener.baseDamageAmplify;
        if (isStrikeOrAsStrike(this))
            damageAmplify += baseStrikeDamageAmplify;
        applyedBaseDamageAmplifies(damageAmplify);
        if (damageAmplify != 100)
            isDamageModified = true;

        AbstractPlayer player = AbstractDungeon.player;
        float tmp = baseDamage;
        for (AbstractRelic r : player.relics) {
            tmp = r.atDamageModify(tmp, this);
            if (baseDamage != (int) tmp)
                isDamageModified = true;
        }
        for (AbstractPower p : player.powers)
            tmp = p.atDamageGive(tmp, damageTypeForTurn, this);
        tmp = player.stance.atDamageGive(tmp, damageTypeForTurn, this);

        // 享受格擋加成
        tmp += MathUtils.ceil((float) p().currentBlock / magicNumber);

        if (baseDamage != (int) tmp)
            isDamageModified = true;
        for (AbstractPower p : mo.powers)
            tmp = p.atDamageReceive(tmp, damageTypeForTurn, this);
        for (AbstractPower p : player.powers)
            tmp = p.atDamageFinalGive(tmp, damageTypeForTurn, this);
        for (AbstractPower p : mo.powers)
            tmp = p.atDamageFinalReceive(tmp, damageTypeForTurn, this);
        if (tmp < 0.0F)
            tmp = 0.0F;
        if (baseDamage != MathUtils.floor(tmp))
            isDamageModified = true;
        damage = MathUtils.floor(tmp);
    }
}
