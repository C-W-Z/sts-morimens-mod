package morimensmod.cards.chaos;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.enums.CustomTags;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;

public class ToadStew extends AbstractEasyCard {
    public final static String ID = makeID(ToadStew.class.getSimpleName());

    public ToadStew() {
        super(ID, 3, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.HEALING);
        magicNumber = baseMagicNumber = 7; // 中毒
        secondMagic = baseSecondMagic = 1; // 虛弱
        heal = baseHeal = 2;
        prepare = 1;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mo) -> new PoisonPower(mo, p, magicNumber)));
        addToBot(new AllEnemyApplyPowerAction(p, secondMagic, (mo) -> new WeakPower(mo, secondMagic, false)));
        addToBot(new HealAction(p, p, heal));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
        upgradeHeal(1);
    }
}
