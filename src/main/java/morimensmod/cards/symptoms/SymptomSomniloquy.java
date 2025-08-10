package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.addToDiscard;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.patches.enums.CustomTags;

public class SymptomSomniloquy extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomSomniloquy.class.getSimpleName());

    public SymptomSomniloquy() {
        super(ID, 1, CardTarget.NONE);
        sortIndex = 8;
        tags.add(CustomTags.UNREMOVABLE_IN_SHOP);
        magicNumber = baseMagicNumber = 1;
        draw = baseDraw = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(draw));
        addToDiscard(new SymptomSomniloquy(), magicNumber);
    }
}
