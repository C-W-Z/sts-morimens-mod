package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.patches.enums.CustomTags;

public class SymptomHysteria extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomHysteria.class.getSimpleName());

    public SymptomHysteria() {
        super(ID, 0, CardTarget.NONE);
        tags.add(CustomTags.UNREMOVABLE_IN_SHOP);
        magicNumber = baseMagicNumber = 1;
        secondMagic = baseSecondMagic = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(secondMagic));
        addToBot(new MakeTempCardInDiscardAction(new SymptomHysteria(), magicNumber));
    }
}
