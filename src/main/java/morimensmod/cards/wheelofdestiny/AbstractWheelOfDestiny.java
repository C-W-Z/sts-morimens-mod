package morimensmod.cards.wheelofdestiny;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.interfaces.PassiveCard;
import morimensmod.patches.enums.CustomTags;

public abstract class AbstractWheelOfDestiny extends AbstractEasyCard implements SpawnModificationCard, PassiveCard {

    public AbstractWheelOfDestiny(final String cardID, final CardRarity rarity) {
        super(cardID, -2, CardType.POWER, rarity, CardTarget.NONE, WHEEL_OF_DESTINY_COLOR);
        tags.add(CustomTags.WHEEL_OF_DESTINY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void upp() {}

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        // Player can't already have the card.
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (c.cardID.equals(this.cardID))
                return false;
        return true;
    }

    @Override
    public boolean canSpawnShop(ArrayList<AbstractCard> currentShopCards) {
        // Player can't already have the card.
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (c.cardID.equals(this.cardID))
                return false;
        return true;
    }

    // don't applyPowers & calculateCardDamage
    @Override
    public void applyPowers() {}

    @Override
    public void calculateCardDamage(AbstractMonster mo) {}
}
