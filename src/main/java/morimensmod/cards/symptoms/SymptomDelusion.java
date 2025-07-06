package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.patches.CustomTags;

public class SymptomDelusion extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomDelusion.class.getSimpleName());

    public SymptomDelusion() {
        super(ID, 0, CardTarget.SELF);
        tags.add(CustomTags.UNREMOVABLE_IN_SHOP);
        magicNumber = baseMagicNumber = 15; // 失去生命
        secondMagic = baseSecondMagic = 2; // 獲得能量
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, magicNumber));
        addToBot(new GainEnergyAction(secondMagic));
    }
}
