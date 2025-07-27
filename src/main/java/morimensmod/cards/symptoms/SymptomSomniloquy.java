package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.patches.enums.CustomTags;

public class SymptomSomniloquy extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomSomniloquy.class.getSimpleName());

    public SymptomSomniloquy() {
        super(ID, 1, CardTarget.NONE);
        tags.add(CustomTags.UNREMOVABLE_IN_SHOP);
        magicNumber = baseMagicNumber = 1;
        draw = baseDraw = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(draw));
        addToBot(new MakeTempCardInDiscardAction(new SymptomSomniloquy(), magicNumber));
    }
}
