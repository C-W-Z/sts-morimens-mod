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

    // public EasyModalChoiceCard(String cardID, String name, String description, Runnable onUseOrChosen) {
    //     this(cardID, null, name, description, onUseOrChosen, CardColor.COLORLESS, null, new CardTags[] {});
    // }

    // public EasyModalChoiceCard(String cardID, String name, String description, Runnable onUseOrChosen,
    //         CardColor color, CardTags tag) {
    //     this(cardID, null, name, description, onUseOrChosen, color, null, new CardTags[] { tag });
    // }

    public EasyModalChoiceCard(String cardID, String cardImgID, String name, String description, Runnable onUseOrChosen,
            CardColor color, String cardOwner, CardTags tag) {
        this(cardID, cardImgID, name, description, onUseOrChosen, color, cardOwner, new CardTags[] { tag });
    }

    public EasyModalChoiceCard(String cardID, String cardImgID, String name, String description, Runnable onUseOrChosen,
            CardColor color, String cardOwner) {
        this(cardID, cardImgID, name, description, onUseOrChosen, color, cardOwner, new CardTags[] {});
    }

    public EasyModalChoiceCard(String cardID, String cardImgID, String name, String description, Runnable onUseOrChosen,
            CardColor color, String cardOwner, CardTags[] tags) {
        super(cardID, cardImgID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, color, cardOwner);
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
        return new EasyModalChoiceCard(cardID, signatureImgID, passedName, passedDesc, onUseOrChosen, color,
                cardOwner, this.tags.toArray(new CardTags[this.tags.size()]));
    }
}
