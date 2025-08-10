package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.patches.enums.CustomTags;

public class SymptomClaustrophobia extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomClaustrophobia.class.getSimpleName());

    public SymptomClaustrophobia() {
        super(ID, 1, CardTarget.SELF);
        sortIndex = 6;
        tags.add(CustomTags.UNREMOVABLE_IN_SHOP);
        magicNumber = baseMagicNumber = 10; // 失去生命
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, null, magicNumber));
        addToBot(new GainBlockAction(p, null, 2 * magicNumber));
    }
}
