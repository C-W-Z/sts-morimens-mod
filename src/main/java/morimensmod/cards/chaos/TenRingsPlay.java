package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class TenRingsPlay extends AbstractEasyCard {
    public final static String ID = makeID(TenRingsPlay.class.getSimpleName());

    public TenRingsPlay() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CustomTags.PHANTOM);
        damage = baseDamage = 5;
        attackCount = baseAttackCount = 2;
        returnToHand = true;
    }

    public void use(AbstractPlayer p, AbstractMonster _m) {
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                calculateCardDamage(m);
                if (m != null)
                    dmgTop(m, AttackEffect.NONE);
            });
        misc++;
        setCostForTurn(cost + misc);
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        atTurnStart();
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        atTurnStart();
    }

    @Override
    public void triggerOnManualDiscard() {
        super.triggerOnManualDiscard();
        atTurnStart();
    }

    @Override
    public void atTurnStart() {
        misc = 0;
        resetAttributes();
        applyPowers();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        setCostForTurn(costForTurn - 1);
    }

    @Override
    public void upp() {
        upgradeAttackCount(1);
    }
}
