package morimensmod.cards.buffs;

import static morimensmod.patches.ColorPatch.CardColorPatch.BUFF_COLOR;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public abstract class AbstractBuffCard extends AbstractEasyCard {

    public AbstractBuffCard(final String cardID, final int cost, final CardRarity rarity, final CardTarget target) {
        super(cardID, cost, CardType.SKILL, rarity, target, BUFF_COLOR);
        tags.add(CustomTags.BUFF);
    }
}
