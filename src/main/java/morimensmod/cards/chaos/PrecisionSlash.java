package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.isDefendOrAsDefend;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import morimensmod.cardmodifiers.DamageEnhanceByBlockModifier;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;

public class PrecisionSlash extends AbstractEasyCard {

    public final static String ID = makeID(PrecisionSlash.class.getSimpleName());

    public PrecisionSlash() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CHAOS_COLOR, CardImgID.Alva.ID);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.STRIKE);
        damageType = DamageType.NORMAL; // 必須是normal才能享受力量加成
        damage = baseDamage = 10;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 2; // 50%格擋加成
        CardModifierManager.addModifier(this, new DamageEnhanceByBlockModifier(50));
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
        // +50%格擋加成
        upgradeMagicNumber(-1);
        CardModifierManager.addModifier(this, new DamageEnhanceByBlockModifier(50));
    }
}
