package morimensmod.cards.status;

import static morimensmod.patches.ColorPatch.CardColorPatch.STATUS_COLOR;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public abstract class AbstractStatusCard extends AbstractEasyCard {

    public AbstractStatusCard(final String cardID, final int cost, final CardRarity rarity, final CardTarget target) {
        super(cardID, cost, CardType.STATUS, rarity, target, STATUS_COLOR);
        tags.add(CustomTags.STATUS);
    }

    @Override
    public void upp() {}

    @Override
    public void upgrade() {}
}
