package morimensmod.cards.caro;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CARO_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToEnemy;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.BleedPower;

public class DirgeOfBlood extends AbstractEasyCard {
    public final static String ID = makeID(DirgeOfBlood.class.getSimpleName());

    public DirgeOfBlood() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CARO_COLOR, CardImgID.Uvhash.ID);
        tags.add(CustomTags.COMMAND);
        tags.add(CustomTags.PHANTOM);
        damage = baseDamage = 5;
        returnToHand = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new BleedPower(m, damage));
        misc++;
        actB(() -> setCostForTurn(cost + misc));
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
        upgradeDamage(3);
    }
}
