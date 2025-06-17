package morimensmod.cards;

import static morimensmod.patches.ColorPatch.CardColorPatch.POSSE_COLOR;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.PosseAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;
import morimensmod.patches.CustomTags;

public abstract class AbstractPosse extends AbstractEasyCard {

    protected AbstractAwakener p;
    protected PosseType type;
    protected int effectCount = 1;

    public AbstractPosse(String cardID, AbstractAwakener p, PosseType type) {
        super(cardID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, POSSE_COLOR);
        tags.add(CustomTags.POSSE);
        set(p, type);
    }

    public abstract void activate();

    @Override
    public void onChoseThisOption() {
        for (int index = 0; index < effectCount; index++)
            addToBot(new PosseAction(p, type, this));
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onChoseThisOption();
    }

    public void set(AbstractAwakener p, PosseType type) {
        this.p = p;
        this.type = type;
    }

    public void set(AbstractAwakener p, PosseType type, int effectCount) {
        this.p = p;
        this.type = type;
        this.effectCount = effectCount;
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

    public String getUITitle() {
        return cardStrings.NAME;
    }

    public String getUIDescription() {
        return cardStrings.EXTENDED_DESCRIPTION[0];
    }
}
