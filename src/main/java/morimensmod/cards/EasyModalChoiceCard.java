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

    public EasyModalChoiceCard(String cardID, String cardImgID, String name, String description,
            Runnable onUseOrChosen) {
        this(cardID, cardImgID, name, description, onUseOrChosen, CardColor.COLORLESS, new CardTags[] {});
    }

    public EasyModalChoiceCard(String cardID, String cardImgID, String name, String description, Runnable onUseOrChosen,
            CardColor color, CardTags tag) {
        this(cardID, cardImgID, name, description, onUseOrChosen, color, new CardTags[] { tag });
    }

    public EasyModalChoiceCard(String cardID, String cardImgID, String name, String description, Runnable onUseOrChosen,
            CardColor color, CardTags[] tags) {
        super(cardID, cardImgID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, color);
        if (tags != null)
            for (CardTags t : tags)
                this.tags.add(t);
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
        return new EasyModalChoiceCard(cardID, cardImgID, passedName, passedDesc, onUseOrChosen, color,
                this.tags.toArray(new CardTags[this.tags.size()]));
    }
}
