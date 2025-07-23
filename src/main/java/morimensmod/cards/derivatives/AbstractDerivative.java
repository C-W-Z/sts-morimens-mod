package morimensmod.cards.derivatives;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.DERIVATIVE_COLOR;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.cards.AbstractEasyCard;

public abstract class AbstractDerivative extends AbstractEasyCard implements SpawnModificationCard {

    public AbstractDerivative(final String cardID, final String cardImgID, final int cost, final CardType type,
            final CardRarity rarity, final CardTarget target) {
        super(cardID, cardImgID, cost, type, rarity, target, DERIVATIVE_COLOR);
    }

    public AbstractDerivative(final String cardID, final int cost, final CardType type, final CardRarity rarity,
            final CardTarget target) {
        this(cardID, cardID, cost, type, rarity, target);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        return false;
    }

    @Override
    public boolean canSpawnShop(ArrayList<AbstractCard> currentShopCards) {
        return false;
    }
}
