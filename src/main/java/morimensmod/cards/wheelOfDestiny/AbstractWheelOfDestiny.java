package morimensmod.cards.wheelOfDestiny;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.enums.CustomTags;

public abstract class AbstractWheelOfDestiny extends AbstractEasyCard implements StartupCard, SpawnModificationCard {

    public AbstractWheelOfDestiny(final String cardID, final int cost, final CardRarity rarity) {
        super(cardID, cost, CardType.POWER, rarity, CardTarget.SELF, WHEEL_OF_DESTINY_COLOR);
        tags.add(CustomTags.WHEEL_OF_DESTINY);
        selfRetain = true;
        prepare = 1;
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        //Player can't already have the card.
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (c.cardID.equals(this.cardID))
                return false;
        return true;
    }
}
