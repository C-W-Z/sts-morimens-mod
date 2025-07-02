package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelfTop;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.LoseThornsPower;

public class WhipOfMadness extends AbstractEasyCard {

    public final static String ID = makeID(WhipOfMadness.class.getSimpleName());

    public WhipOfMadness() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damageType = DamageType.NORMAL;
        damage = baseDamage = 6;
        attackCount = baseAttackCount = 3;
        magicNumber = baseMagicNumber = 25;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                AbstractMonster target = AbstractDungeon.getMonsters()
                        .getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                if (target == null)
                    return;
                calculateCardDamage(target);
                int tmp_thorns = MathUtils.ceil(damage * magicNumber / 100F);
                applyToSelfTop(new LoseThornsPower(p, tmp_thorns));
                applyToSelfTop(new ThornsPower(p, tmp_thorns));
                addToTop(
                    new DamageAction(
                        target,
                        new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn),
                        AttackEffect.SLASH_DIAGONAL
                ));
            });
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(25);
    }
}
