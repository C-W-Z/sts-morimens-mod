package morimensmod.cards;

import basemod.AutoAdd;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class EasyModalChoiceCard extends AbstractEasyCard {

    private Runnable onUseOrChosen;
    private String passedName;
    private String passedDesc;

    public EasyModalChoiceCard(String cardID, String name, String description, Runnable onUseOrChosen) {
        this(cardID, name, description, onUseOrChosen, CardColor.COLORLESS, null);
    }

    public EasyModalChoiceCard(String cardID, String name, String description, Runnable onUseOrChosen, CardColor color, CardTags tag) {
        super(cardID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, color);
        if (tag != null)
            tags.add(tag);
        this.name = this.originalName = passedName = name;
        this.rawDescription = passedDesc = description;
        this.onUseOrChosen = onUseOrChosen;
        initializeTitle();
        initializeDescription();
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

    @Override
    public AbstractCard makeCopy() {
        return new EasyModalChoiceCard(cardID, passedName, passedDesc, onUseOrChosen);
    }
}
