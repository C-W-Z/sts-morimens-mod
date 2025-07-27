package morimensmod.cards.symptoms;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.SYMPTOM_COLOR;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.enums.CustomTags;

public abstract class AbstractSymptomCard extends AbstractEasyCard {

    public AbstractSymptomCard(String cardID, int cost, CardTarget target) {
        super(cardID, cost, CardType.CURSE, CardRarity.CURSE, target, SYMPTOM_COLOR);
        tags.add(CustomTags.SYMPTOM);
    }

    @Override
    public void upp() {}

    @Override
    public void upgrade() {}

    @Override
    public void applyPowers() {
        super.applySuperPower();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateSuperCardDamage(mo);
    }
}
