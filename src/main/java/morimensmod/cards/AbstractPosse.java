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
    protected boolean purgeOnUse = false;

    public AbstractPosse(String cardID, AbstractAwakener p, PosseType type) {
        super(cardID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, POSSE_COLOR);
        tags.add(CustomTags.POSSE);
        set(p, type);
    }

    /**
     * 裡面的效果必須要用addToTop排進action queue，不可以用addToBottom
     * (注意想要後發生的效果要先addToTop，才會後發生)
     */
    public abstract void activate();

    @Override
    public void onChoseThisOption() {
        // 用addToTop是因為，被選擇之後就應該要立即觸發
        for (int index = 0; index < effectCount - 1; index++)
            addToTop(new PosseAction(p, PosseType.TMP, this, true));
        addToTop(new PosseAction(p, type, this));
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onChoseThisOption();
    }

    public AbstractAwakener getAwakener() {
        return p;
    }

    public PosseType getType() {
        return type;
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

    public boolean getPurgeOnUse() {
        return purgeOnUse;
    }

    public void setPurgeOnUse(boolean purgeOnUse) {
        this.purgeOnUse = purgeOnUse;
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
