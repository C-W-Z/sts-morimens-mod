package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.STATUS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.actions.DestroyCardAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class Unrestrained extends AbstractEasyCard {
    public final static String ID = makeID(Unrestrained.class.getSimpleName());

    public Unrestrained() {
        super(ID, 0, CardType.STATUS, CardRarity.COMMON, CardTarget.ALL_ENEMY, STATUS_COLOR);
        tags.add(CustomTags.STATUS);
        tags.add(CustomTags.RETAIN_IN_DECK);
        tags.add(CustomTags.DESTROYABLE);
        magicNumber = baseMagicNumber = 2; // 獲得臨時力量
        secondMagic = baseSecondMagic = 2; // 敵人獲得臨時力量
        exhaust = true;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
        applyToSelf(new LoseStrengthPower(p, magicNumber));
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mo) -> new StrengthPower(mo, magicNumber)));
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mo) -> new LoseStrengthPower(mo, magicNumber)));
        addToBot(new DestroyCardAction(this));
    }

    @Override
    public void upp() {
    }

    @Override
    public void upgrade() {
    }
}
