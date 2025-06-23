package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToEnemyTop;
import static morimensmod.util.Wiz.getEnemies;
import java.util.Comparator;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class MidnightTide extends AbstractEasyCard {

    public final static String ID = makeID(MidnightTide.class.getSimpleName());

    public MidnightTide() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damageType = DamageType.NORMAL; // 必須是normal才能享受力量加成
        damage = baseDamage = 3;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 50;
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
}
