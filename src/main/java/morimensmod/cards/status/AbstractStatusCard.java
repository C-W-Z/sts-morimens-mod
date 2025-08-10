package morimensmod.cards.status;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.STATUS_COLOR;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText.AbstractCardFlavorFields;
import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.config.ModSettings;
import morimensmod.patches.enums.CustomTags;

public abstract class AbstractStatusCard extends AbstractEasyCard {

    protected int sortIndex = 0;

    public AbstractStatusCard(final String cardID, final int cost, final CardRarity rarity, final CardTarget target) {
        super(cardID, cost, CardType.STATUS, rarity, target, STATUS_COLOR);
        tags.add(CustomTags.STATUS);
        AbstractCardFlavorFields.boxColor.set(this, ModSettings.STATUS_CARD_FLAVOR_BOX_COLOR);
        AbstractCardFlavorFields.textColor.set(this, ModSettings.STATUS_CARD_FLAVOR_TEXT_COLOR);
    }

    @Override
    public void upp() {}

    @Override
    public void upgrade() {}

    @Override
    public int compareTo(AbstractCard other) {
        if (other instanceof AbstractStatusCard && sortIndex != ((AbstractStatusCard) other).sortIndex)
            return sortIndex - ((AbstractStatusCard) other).sortIndex;
        return super.compareTo(other);
    }
}
