package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.makeInHand;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import morimensmod.actions.AliemusChangeAction;
import morimensmod.cardmodifiers.ExhaustModifier;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class Showdown extends AbstractEasyCard {
    public final static String ID = makeID(Showdown.class.getSimpleName());

    private static final int MAX_ATTACK_COUNT = 6;

    public Showdown() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damage = baseDamage = 6;
        attackCount = baseAttackCount = MAX_ATTACK_COUNT;
        aliemus = baseAliemus = 15;
        magicNumber = baseMagicNumber = 1; // 1 張
        cardsToPreview = new UnexpectedGain();
        cardsToPreview.modifyCostForCombat(-cardsToPreview.cost);
        cardsToPreview.freeToPlayOnce = true;
        CardModifierManager.addModifier(cardsToPreview, new ExhaustModifier());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));

        attackCount = AbstractDungeon.cardRandomRng.random(1, attackCount);

        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                dmgTop(m, AttackEffect.NONE);
            });

        if (attackCount >= MAX_ATTACK_COUNT) {
            makeInHand(cardsToPreview, magicNumber);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}
