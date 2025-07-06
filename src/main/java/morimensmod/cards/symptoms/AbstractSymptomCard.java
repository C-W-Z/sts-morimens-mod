package morimensmod.cards.symptoms;

import static morimensmod.patches.ColorPatch.CardColorPatch.SYMPTOM_COLOR;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public abstract class AbstractSymptomCard extends AbstractEasyCard {

    public AbstractSymptomCard(String cardID, int cost, CardTarget target) {
        super(cardID, cost, CardType.CURSE, CardRarity.CURSE, target, SYMPTOM_COLOR);
        tags.add(CustomTags.SYMPTOM);
    }

    @Override
    public void upp() {}

    @Override
    public void upgrade() {}
}
