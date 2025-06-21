package morimensmod.cards.democards.complex;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.BUFF_COLOR;
import static morimensmod.util.Wiz.applyToSelfTop;
import static morimensmod.util.Wiz.atb;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.actions.EasyXCostAction;
import morimensmod.cards.AbstractEasyCard;

import basemod.AutoAdd;

@AutoAdd.Ignore
public class EasyXCostDemo extends AbstractEasyCard {
    public final static String ID = makeID(EasyXCostDemo.class.getSimpleName());
    // intellij stuff attack, enemy, rare, , , , , 0, 1

    public EasyXCostDemo() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY, BUFF_COLOR);
        baseDamage = 5;
        baseMagicNumber = magicNumber = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            applyToSelfTop(new StrengthPower(p, effect + params[0]));
            for (int i = 0; i < effect + params[0]; i++)
                dmgTop(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
            return true;
        }, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}