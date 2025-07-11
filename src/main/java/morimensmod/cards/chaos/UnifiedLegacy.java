package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.DelayAliemusPower;
import morimensmod.powers.DelayEnhancePower;

public class UnifiedLegacy extends AbstractEasyCard {
    public final static String ID = makeID(UnifiedLegacy.class.getSimpleName());

    public UnifiedLegacy() {
        super(ID, 4, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        aliemus = baseAliemus = 100;
        magicNumber = baseMagicNumber = 0;
        exhaust = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (magicNumber > 0)
            applyToSelf(new DelayEnhancePower(p, magicNumber));
        applyToSelf(new DelayAliemusPower(p, aliemus));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
