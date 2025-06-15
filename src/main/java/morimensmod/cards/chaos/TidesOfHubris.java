package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class TidesOfHubris extends AbstractEasyCard {
    public final static String ID = makeID(TidesOfHubris.class.getSimpleName());

    public TidesOfHubris() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.STRIKE);
        damage = baseDamage = 5;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 1; // 獲得力量
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < attackCount; i++)
            addToBot(new DamageAllEnemiesAction(p, damage, damageTypeForTurn, AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(1);
    }
}
