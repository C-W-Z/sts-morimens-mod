package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.makeInHand;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText.AbstractCardFlavorFields;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.symptoms.SymptomDelusion;
import morimensmod.util.ModSettings;

public class CryOut extends AbstractStatusCard {
    public final static String ID = makeID(CryOut.class.getSimpleName());

    public CryOut() {
        super(ID, 0, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        magicNumber = baseMagicNumber = 3; // 降低臨時力量
        secondMagic = baseSecondMagic = 1; // 幾張妄想
        cardsToPreview = new SymptomDelusion();
        exhaust = true;
        selfRetain = true;

        AbstractCardFlavorFields.boxColor.set(this, ModSettings.STATUS_CARD_FLAVOR_BOX_COLOR);
        AbstractCardFlavorFields.textColor.set(this, ModSettings.STATUS_CARD_FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AllEnemyApplyPowerAction(p, -magicNumber, (mo) -> new StrengthPower(mo, -magicNumber)));
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mo) -> new GainStrengthPower(mo, magicNumber)));
        makeInHand(cardsToPreview);
    }
}
