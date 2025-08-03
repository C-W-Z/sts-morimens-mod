package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class PainOfUnfulfilledDesires_Status extends AbstractStatusCard {
    public final static String ID = makeID(PainOfUnfulfilledDesires_Status.class.getSimpleName());

    public static final int DEFAULT_DAMAGE = 6;

    public PainOfUnfulfilledDesires_Status() {
        this(DEFAULT_DAMAGE);
    }

    public PainOfUnfulfilledDesires_Status(int damageToSelf) {
        super(ID, 0, CardRarity.COMMON, CardTarget.SELF);
        damageType = DamageType.NORMAL;
        magicNumber = baseMagicNumber = damageToSelf; // 受傷
        secondMagic = baseSecondMagic = 2; // 臨時力量
        draw = baseDraw = 1;
        exhaust = true;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(p, magicNumber, damageTypeForTurn), AttackEffect.BLUNT_LIGHT));
        applyToSelf(new StrengthPower(p, secondMagic));
        applyToSelf(new LoseStrengthPower(p, secondMagic));
        addToBot(new DrawCardAction(p, draw));
    }
}
