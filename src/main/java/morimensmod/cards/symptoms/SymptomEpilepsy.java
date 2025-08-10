package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.patches.enums.CustomTags;

public class SymptomEpilepsy extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomEpilepsy.class.getSimpleName());

    public SymptomEpilepsy() {
        super(ID, 0, CardTarget.SELF);
        sortIndex = 2;
        tags.add(CustomTags.UNREMOVABLE_IN_SHOP);
        magicNumber = baseMagicNumber = 15; // 失去生命
        draw = baseDraw = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, null, magicNumber));
        addToBot(new DrawCardAction(draw));
    }
}
