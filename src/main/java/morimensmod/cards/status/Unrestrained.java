package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.STATUS_COLOR;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class Unrestrained extends AbstractEasyCard {
    public final static String ID = makeID(Unrestrained.class.getSimpleName());

    public Unrestrained() {
        super(ID, 0, CardType.STATUS, CardRarity.COMMON, CardTarget.ALL_ENEMY, STATUS_COLOR);
        tags.add(CustomTags.STATUS);
        tags.add(CustomTags.RETAIN_IN_DECK);
        magicNumber = baseMagicNumber = 2; // 獲得臨時力量
        secondMagic = baseSecondMagic = 2; // 敵人獲得臨時力量
        selfRetain = true;
        FleetingField.fleeting.set(this, true); // 銷毀
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, null, new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, null, new LoseStrengthPower(p, magicNumber)));
        addToBot(new AllEnemyApplyPowerAction(null, magicNumber, (mo) -> new StrengthPower(mo, magicNumber)));
        addToBot(new AllEnemyApplyPowerAction(null, magicNumber, (mo) -> new LoseStrengthPower(mo, magicNumber)));
    }

    @Override
    public void upp() {
    }

    @Override
    public void upgrade() {
    }
}
