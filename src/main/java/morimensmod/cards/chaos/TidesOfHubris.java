package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.enums.CustomTags;

public class TidesOfHubris extends AbstractEasyCard {
    public final static String ID = makeID(TidesOfHubris.class.getSimpleName());

    public TidesOfHubris() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.STRIKE);
        damage = baseDamage = 7;
        isMultiDamage = true; // 攻擊多個目標
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 1; // 獲得力量
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                addToTop(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AttackEffect.SLASH_HORIZONTAL));
            });
        if (magicNumber > 0)
            applyToSelf(new StrengthPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(5);
        upgradeMagicNumber(1);
    }
}
