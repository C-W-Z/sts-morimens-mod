package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.RandomAttackMonsterAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class BladeOfDefiance extends AbstractEasyCard {
    public final static String ID = makeID(BladeOfDefiance.class.getSimpleName());

    public BladeOfDefiance() {
        super(ID, 3, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.STRIKE);
        damage = baseDamage = 6;
        attackCount = baseAttackCount = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                addToTop(new RandomAttackMonsterAction(new DamageInfo(p, damage, damageTypeForTurn),
                        AttackEffect.SLASH_DIAGONAL));
            });
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.hasTag(CardTags.STRIKE))
            setCostForTurn(costForTurn - 1);
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}
