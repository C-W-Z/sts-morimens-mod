package morimensmod.cards;

import static morimensmod.patches.ColorPatch.CardColorPatch.POSSE_COLOR;
import static morimensmod.util.Wiz.atb;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.PosseAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;
import morimensmod.posses.AbstractPosse;

public abstract class AbstractPosseCard extends AbstractEasyCard {

    protected Runnable onUseOrChosen;

    public AbstractPosseCard(String cardID, AbstractAwakener p, PosseType type, AbstractPosse posse) {
        this(cardID, () -> atb(new PosseAction(p, type, posse)));
    }

    public AbstractPosseCard(String cardID, Runnable onUseOrChosen) {
        super(cardID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, POSSE_COLOR);
        this.onUseOrChosen = onUseOrChosen;
    }

    @Override
    public void onChoseThisOption() {
        onUseOrChosen.run();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onUseOrChosen.run();
    }

    @Override
    public void upp() {
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }

    // @Override
    // public AbstractCard makeCopy() {
    // return new AbstractPosseCard(cardID, onUseOrChosen);
    // }
}
