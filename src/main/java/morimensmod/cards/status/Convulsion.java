package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.discardPile;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Convulsion extends AbstractStatusCard {
    public final static String ID = makeID(Convulsion.class.getSimpleName());

    public Convulsion() {
        super(ID, -2, CardRarity.COMMON, CardTarget.NONE);
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnManualDiscard() {
        super.triggerOnManualDiscard();
        addToTop(new ExhaustSpecificCardAction(this, discardPile()));
    }
}
