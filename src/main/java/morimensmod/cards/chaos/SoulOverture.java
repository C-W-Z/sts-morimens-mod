package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToEnemyTop;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.actions.PierceDamageAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.BleedPower;

public class SoulOverture extends AbstractEasyCard {

    public final static String ID = makeID(SoulOverture.class.getSimpleName());

    public SoulOverture() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damageType = DamageType.NORMAL;
        damage = baseDamage = 6;
        attackCount = baseAttackCount = 3;
        magicNumber = baseMagicNumber = 0;
        secondMagic = baseSecondMagic = 2;
        prepare = 1;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        int additionalAttackCount = 0;
        if (this.costForTurn == 0 || freeToPlay() || this.isInAutoplay)
            additionalAttackCount = secondMagic;

        for (int i = 0; i < attackCount + additionalAttackCount; i++)
            actB(() -> {
                AbstractMonster target = AbstractDungeon.getMonsters()
                        .getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                if (target == null)
                    return;
                calculateCardDamage(target);

                if (magicNumber > 0 && AbstractDungeon.cardRandomRng.randomBoolean(magicNumber)) {
                    // 奇妙負面效果
                    int type = AbstractDungeon.cardRandomRng.random(1, 5);
                    switch (type) {
                        case 1:
                            applyToEnemyTop(target, new BleedPower(target, MathUtils.ceil(damage * 1.5F)));
                            break;
                        case 2:
                            applyToEnemyTop(target, new PoisonPower(target, p, MathUtils.ceil(damage * 0.75F)));
                            break;
                        case 3:
                            applyToEnemyTop(target, new WeakPower(target, 1, false));
                            break;
                        case 4:
                            applyToEnemyTop(target, new VulnerablePower(target, 1, false));
                            break;
                        case 5:
                            applyToEnemyTop(target, new GainStrengthPower(target, 3));
                            applyToEnemyTop(target, new StrengthPower(target, -3));
                            break;
                        default:
                            break;
                    }
                }

                addToTop(new PierceDamageAction(
                    target,
                    new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn),
                    AttackEffect.NONE
                ));
            });
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(50);
    }
}
