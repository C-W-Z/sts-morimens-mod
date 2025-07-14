package morimensmod.cards.ultra;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.ULTRA_COLOR;
import static morimensmod.util.Wiz.actB;

import com.megacrit.cardcrawl.actions.watcher.LessonLearnedAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class EndlessLearning extends AbstractEasyCard {
    public final static String ID = makeID(EndlessLearning.class.getSimpleName());

    public EndlessLearning() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY, ULTRA_COLOR);
        tags.add(CustomTags.COMMAND);
        damage = baseDamage = 10;
        attackCount = baseAttackCount = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                addToTop(new LessonLearnedAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
            });
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}
