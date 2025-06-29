package morimensmod.patches;

import static morimensmod.patches.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch2(clz = AbstractDungeon.class, method = "getRewardCards")
public class CardRewardPatch {

    private static boolean shouldNotRepeat(AbstractCard card) {
        return card.color == WHEEL_OF_DESTINY_COLOR || card.hasTag(CustomTags.ROUSE);
    }

    private static boolean contains(ArrayList<AbstractCard> list, String cardID) {
        return list.stream().anyMatch(_c -> _c.cardID.equals(cardID));
    }

    @SpirePrefixPatch
    public static SpireReturn<ArrayList<AbstractCard>> Replace(float ___cardUpgradedChance) {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        int numCards = 3;
        for (AbstractRelic r : AbstractDungeon.player.relics)
            numCards = r.changeNumberOfCardsInReward(numCards);
        if (ModHelper.isModEnabled("Binary"))
            numCards--;
        for (int i = 0; i < numCards; i++) {
            AbstractCard.CardRarity rarity = AbstractDungeon.rollRarity();
            switch (rarity) {
                case COMMON:
                    AbstractDungeon.cardBlizzRandomizer -= AbstractDungeon.cardBlizzGrowth;
                    if (AbstractDungeon.cardBlizzRandomizer <= AbstractDungeon.cardBlizzMaxOffset)
                        AbstractDungeon.cardBlizzRandomizer = AbstractDungeon.cardBlizzMaxOffset;
                case UNCOMMON: break;
                case RARE: AbstractDungeon.cardBlizzRandomizer = AbstractDungeon.cardBlizzStartOffset; break;
                default: break;
            }
            boolean containsDupe = true;
            AbstractCard card = null;
            while (containsDupe) {
                containsDupe = false;
                card = AbstractDungeon.getCard(rarity);
                if (contains(retVal, card.cardID) ||
                    (shouldNotRepeat(card) && contains(AbstractDungeon.player.masterDeck.group, card.cardID)))
                    containsDupe = true;
            }
            if (card != null)
                retVal.add(card);
        }
        ArrayList<AbstractCard> retVal2 = new ArrayList<>();
        for (AbstractCard c : retVal)
            retVal2.add(c.makeCopy());
        for (AbstractCard c : retVal2) {
            if (c.rarity != AbstractCard.CardRarity.RARE &&
                AbstractDungeon.cardRng.randomBoolean(___cardUpgradedChance) &&
                c.canUpgrade()) {
                c.upgrade();
                continue;
            }
            for (AbstractRelic r : AbstractDungeon.player.relics)
                r.onPreviewObtainCard(c);
        }

        return SpireReturn.Return(retVal2);
    }
}
