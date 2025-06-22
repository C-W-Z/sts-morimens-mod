package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.shop.ShopScreen;

import javassist.CtBehavior;

public class CardPurgePatch {

    @SpirePatch2(clz = ShopScreen.class, method = "purchasePurge")
    public static class ShopScreenPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Insert(ShopScreen __instance) {
            CardGroup purgeGroup = CardGroup
                    .getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards());

            purgeGroup.group.removeIf(c -> c.hasTag(CustomTags.UNREMOVABLE_IN_SHOP));

            AbstractDungeon.gridSelectScreen.open(purgeGroup, 1, ShopScreen.NAMES[13], false, false, true, true);

            return SpireReturn.Return();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(GridCardSelectScreen.class, "open");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = CardGroup.class, method = "getPurgeableCards")
    public static class CardGroupPatch {
        @SpirePostfixPatch
        public static CardGroup Postfix(CardGroup __result, CardGroup __instance) {
            __result.group.removeIf(c -> c.hasTag(CustomTags.UNREMOVABLE));
            return __result;
        }
    }
}
