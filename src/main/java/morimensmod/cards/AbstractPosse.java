package morimensmod.cards;

import static morimensmod.patches.ColorPatch.CardColorPatch.POSSE_COLOR;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.PosseAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;
import morimensmod.patches.CustomTags;

public abstract class AbstractPosse extends AbstractEasyCard {

    protected AbstractAwakener awaker;
    protected PosseType type;

    public AbstractPosse(String cardID, AbstractAwakener awaker, PosseType type) {
        super(cardID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, POSSE_COLOR);
        tags.add(CustomTags.POSSE);
        set(awaker, type);
    }

    public abstract void activate();

    @Override
    public void onChoseThisOption() {
        // 用addToTop是因為，被選擇之後就應該要立即觸發
        addToTop(new PosseAction(this));
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onChoseThisOption();
    }

    public AbstractAwakener getAwakener() {
        return awaker;
    }

    public PosseType getType() {
        return type;
    }

    public void set(AbstractAwakener awaker, PosseType type) {
        this.awaker = awaker;
        this.type = type;
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
