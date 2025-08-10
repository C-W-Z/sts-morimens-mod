package morimensmod.cards.posses;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.POSSE_COLOR;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.PosseAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;
import morimensmod.patches.enums.CustomTags;

public abstract class AbstractPosse extends AbstractEasyCard {

    protected int posseIndex = 100;

    protected AbstractPlayer awaker;
    protected PosseType type;

    public AbstractPosse(String cardID) {
        this(cardID, AbstractDungeon.player, PosseType.UNLIMITED);
    }

    public AbstractPosse(String cardID, AbstractPlayer awaker, PosseType type) {
        super(cardID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, POSSE_COLOR);
        tags.add(CustomTags.POSSE);
        set(awaker, type);
    }

    public abstract void activate();

    @Override
    public void onChoseThisOption() {
        // 用addToTop是因為，被選擇之後就應該要立即觸發
        addToTop(new PosseAction(this));
        if (type == PosseType.EXTRA)
            AbstractAwakener.posseUsedThisTurn.add(cardID);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onChoseThisOption();
    }

    public AbstractPlayer getAwakener() {
        return awaker;
    }

    public PosseType getType() {
        return type;
    }

    public void set(AbstractPlayer awaker, PosseType type) {
        this.awaker = awaker;
        this.type = type;
    }

    @Override
    public void upp() {}

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

    public boolean isAwakenerOnly() {
        return false;
    }

    @Override
    public int compareTo(AbstractCard other) {
        if (other instanceof AbstractPosse && posseIndex != ((AbstractPosse)other).posseIndex)
            return posseIndex - ((AbstractPosse)other).posseIndex;
        return super.compareTo(other);
    }
}
