package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Joker extends AbstractStatusCard {
    public final static String ID = makeID(Joker.class.getSimpleName());

    public static final int DEFAULT_DAMAGE = 8;

    public Joker() {
        this(DEFAULT_DAMAGE);
    }

    public Joker(int damageToSelf) {
        super(ID, 0, CardRarity.SPECIAL, CardTarget.SELF);
        sortIndex = 1000;
        damageType = DamageType.NORMAL;
        magicNumber = baseMagicNumber = damageToSelf; // 受傷
        draw = baseDraw = 2;
        exhaust = true;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(p, magicNumber, damageTypeForTurn), AttackEffect.NONE));
        addToBot(new DrawCardAction(p, draw));
    }
}
