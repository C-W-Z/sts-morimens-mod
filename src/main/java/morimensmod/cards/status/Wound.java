package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.discardPile;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Wound extends AbstractStatusCard {
    public final static String ID = makeID(Wound.class.getSimpleName());

    public Wound() {
        super(ID, 0, CardRarity.COMMON, CardTarget.SELF);
        damageType = DamageType.NORMAL;
        magicNumber = baseMagicNumber = 2; // 受傷
        draw = baseDraw = 1;
        exhaust = true;
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(p, magicNumber, damageTypeForTurn), AttackEffect.NONE));
        addToBot(new DrawCardAction(p, draw));
    }

    @Override
    public void triggerOnManualDiscard() {
        super.triggerOnManualDiscard();
        addToTop(new ExhaustSpecificCardAction(this, discardPile()));
    }
}
