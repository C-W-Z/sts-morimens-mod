package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.STATUS_COLOR;
import static morimensmod.util.Wiz.makeInHand;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.symptoms.SymptomDelusion;
import morimensmod.patches.CustomTags;

public class CryOut extends AbstractEasyCard {
    public final static String ID = makeID(CryOut.class.getSimpleName());

    public CryOut() {
        super(ID, 0, CardType.STATUS, CardRarity.COMMON, CardTarget.ALL_ENEMY, STATUS_COLOR);
        tags.add(CustomTags.STATUS);
        magicNumber = baseMagicNumber = 3; // 降低臨時力量
        secondMagic = baseSecondMagic = 1; // 幾張妄想
        cardsToPreview = new SymptomDelusion();
        exhaust = true;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AllEnemyApplyPowerAction(p, -magicNumber, (mo) -> new StrengthPower(mo, -magicNumber)));
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mo) -> new GainStrengthPower(mo, magicNumber)));
        makeInHand(cardsToPreview);
    }

    @Override
    public void upp() {
    }

    @Override
    public void upgrade() {
    }
}
