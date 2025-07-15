package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.getEnemies;
import java.util.Comparator;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.enums.CustomTags;

public class InsightStrike extends AbstractEasyCard {

    public final static String ID = makeID(InsightStrike.class.getSimpleName());

    public InsightStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.STRIKE);
        damage = baseDamage = 4;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 25;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster targetMonster = getEnemies().stream()
                .max(Comparator.comparingInt((_m) -> _m.currentHealth + _m.currentBlock))
                .orElse(null);
        if (targetMonster == null)
            return;
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(targetMonster);
                addToBot(new GainBlockAction(p, p, MathUtils.ceil(damage * magicNumber / 100F)));
                dmgTop(targetMonster, AttackEffect.SLASH_DIAGONAL);
            });
    }

    @Override
    public void upp() {
        upgradeMagicNumber(25);
    }
}
