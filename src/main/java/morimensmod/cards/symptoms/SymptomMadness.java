package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.patches.enums.CustomTags;

public class SymptomMadness extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomMadness.class.getSimpleName());

    public SymptomMadness() {
        super(ID, 0, CardTarget.SELF);
        sortIndex = 3;
        tags.add(CustomTags.UNREMOVABLE_IN_SHOP);
        magicNumber = baseMagicNumber = 15; // 失去生命
        aliemus = baseAliemus = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, null, magicNumber));
        addToBot(new AliemusChangeAction(p, aliemus));
    }
}
